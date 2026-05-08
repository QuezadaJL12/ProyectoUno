package servidor;

import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import dtos.EstadoLobbyDTO;
import dtos.EstadoPartidaDTO;
import java.io.*;
import java.net.Socket;
import java.util.Map;

public class HiloManejadorCliente implements Runnable {
    private Socket socket;
    private IPuertoAplicacion gestor;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private String idJugador;

    public HiloManejadorCliente(Socket socket, IPuertoAplicacion gestor) {
        this.socket = socket;
        this.gestor = gestor;
    }

    private void enviarATodos(Object respuesta) {
        for (HiloManejadorCliente c : ServidorUNO.clientes) {
            try {
                c.salida.writeObject(respuesta);
                c.salida.flush();
                c.salida.reset();
            } catch (IOException e) {
                ServidorUNO.clientes.remove(c);
            }
        }
    }

    private void notificarCambioPartida(String idSala) {
        for (HiloManejadorCliente c : ServidorUNO.clientes) {
            try {
                EstadoPartidaDTO estadoPersonalizado = gestor.obtenerEstadoPartida(idSala, c.idJugador);
                if (estadoPersonalizado != null) {
                    c.salida.writeObject(estadoPersonalizado);
                    c.salida.flush();
                    c.salida.reset();
                }
            } catch (IOException e) {
                ServidorUNO.clientes.remove(c);
            }
        }
    }

    @Override
    public void run() {
        try {
            salida = new ObjectOutputStream(socket.getOutputStream());
            salida.flush();
            entrada = new ObjectInputStream(socket.getInputStream());

            while (!socket.isClosed()) {
                Object obj = entrada.readObject();
                if (!(obj instanceof Map)) continue;

                Map<String, Object> peticion = (Map<String, Object>) obj;
                String accion = (String) peticion.get("accion");
                String idSala = (String) peticion.get("idSala");
                if (idSala == null) idSala = (String) peticion.get("idPartida");
                
                this.idJugador = (String) peticion.get("nombre");
                if (this.idJugador == null) this.idJugador = (String) peticion.get("idJugador");

                switch (accion) {
                    case "UNIRSE_LOBBY":
                        EstadoLobbyDTO lobby = gestor.unirseLobby(idSala, (String)peticion.get("nombre"), (String)peticion.get("avatar"));
                        enviarATodos(lobby);
                        break;

                    case "INICIAR_PARTIDA":
                        gestor.iniciarPartidaDesdeLobby(idSala);
                        EstadoLobbyDTO inicio = new EstadoLobbyDTO();
                        inicio.setJuegoIniciado(true);
                        enviarATodos(inicio);
                        break;

                    case "JUGAR_CARTA":
                        gestor.jugarCarta(idSala, idJugador, (String)peticion.get("idCarta"), (String)peticion.get("nuevoColor"));
                        notificarCambioPartida(idSala); 
                        break;

                    case "ROBAR_CARTA":
                        gestor.robarCarta(idSala, idJugador);
                        notificarCambioPartida(idSala); 
                        break;

                    case "CANTAR_UNO":
                        gestor.cantarUno(idSala, idJugador);
                        notificarCambioPartida(idSala); 
                        break;

                    case "OBTENER_ESTADO":
                        salida.writeObject(gestor.obtenerEstadoPartida(idSala, idJugador));
                        salida.flush();
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Finalizando conexión con " + idJugador);
        } finally {
            ServidorUNO.clientes.remove(this);
            try { socket.close(); } catch (IOException e) {}
        }
    }
}