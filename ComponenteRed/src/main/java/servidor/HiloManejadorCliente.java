package servidor;

import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import dtos.CartaDTO; 
import dtos.EstadoPartidaDTO;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloManejadorCliente implements Runnable {
    private Socket socket;
    private IPuertoAplicacion gestor;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private String idJugador;

    public HiloManejadorCliente(Socket socket, IPuertoAplicacion gestor, String idJugador) {
        this.socket = socket;
        this.gestor = gestor;
        this.idJugador = idJugador;
    }

    @Override
    public void run() {
        try {
            salida = new ObjectOutputStream(socket.getOutputStream());
            salida.flush();
            entrada = new ObjectInputStream(socket.getInputStream());

            while (!socket.isClosed()) {
                try {
                    Object objPeticion = entrada.readObject();
                    if (objPeticion == null) break;

                    String peticion = (String) objPeticion;

                    switch (peticion) {
                        case "OBTENER_ESTADO":
                            enviarRespuesta(gestor.obtenerEstadoPartida("partida-1", idJugador));
                            break;
                        case "ROBAR_CARTA":
                            enviarRespuesta(gestor.robarCarta("partida-1", idJugador));
                            break;
                        case "JUGAR_CARTA":
                            CartaDTO cartaAJugar = (CartaDTO) entrada.readObject();
                            String colorElegidoStr = (String) entrada.readObject();
                            EstadoPartidaDTO nuevoEstado = gestor.jugarCarta("partida-1", idJugador, 0, cartaAJugar.getFotoId(), colorElegidoStr);
                            if (nuevoEstado == null) {
                                nuevoEstado = gestor.obtenerEstadoPartida("partida-1", idJugador);
                            }
                            enviarRespuesta(nuevoEstado);
                            break;
                        case "CANTAR_UNO":
                            enviarRespuesta(gestor.cantarUno("partida-1", idJugador));
                            break;
                        case "UNIRSE_LOBBY":
                            String idSalaLobby = (String) entrada.readObject();
                            String nombre = (String) entrada.readObject();
                            String avatar = (String) entrada.readObject(); 
                            enviarRespuesta(gestor.unirseLobby(idSalaLobby, nombre, avatar));
                            break;
                        case "ESTADO_LOBBY":
                            String idSalaEstado = (String) entrada.readObject();
                            enviarRespuesta(gestor.obtenerEstadoLobby(idSalaEstado));
                            break;
                        case "INICIAR_PARTIDA":
                            String idSalaInicio = (String) entrada.readObject();
                            gestor.iniciarPartidaDesdeLobby(idSalaInicio);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    try {
                        enviarRespuesta(gestor.obtenerEstadoPartida("partida-1", idJugador));
                    } catch (Exception ex) {
                        break; 
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            cerrarConexion();
        }
    }

    private void enviarRespuesta(Object respuesta) throws Exception {
        if (respuesta != null) {
            salida.reset(); 
            salida.writeObject(respuesta);
            salida.flush();
        }
    }

    private void cerrarConexion() {
        try {
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (Exception e) {
        }
    }
}