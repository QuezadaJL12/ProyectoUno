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

            while (true) {
                try {
                    Object objPeticion = entrada.readObject();
                    if (objPeticion == null) break;

                    String peticion = (String) objPeticion;

                    if (peticion.equals("OBTENER_ESTADO")) {
                        salida.writeObject(gestor.obtenerEstadoPartida("partida-1", idJugador));
                        salida.flush();
                    } else if (peticion.equals("ROBAR_CARTA")) {
                        salida.writeObject(gestor.robarCarta("partida-1", idJugador));
                        salida.flush();
                    } else if (peticion.equals("JUGAR_CARTA")) {
                        CartaDTO cartaAJugar = (CartaDTO) entrada.readObject();
                        String colorElegidoStr = (String) entrada.readObject();
                        
                        EstadoPartidaDTO nuevoEstado = gestor.jugarCarta("partida-1", idJugador, 0, colorElegidoStr);
                        if (nuevoEstado == null) {
                            nuevoEstado = gestor.obtenerEstadoPartida("partida-1", idJugador);
                        }
                        salida.writeObject(nuevoEstado);
                        salida.flush();
                    }
                } catch (Exception e) {
                    salida.writeObject(gestor.obtenerEstadoPartida("partida-1", idJugador));
                    salida.flush();
                }
            }
        } catch (Exception e) {
            System.err.println("Conexión finalizada: " + idJugador);
        } finally {
            try { socket.close(); } catch (Exception ex) {}
        }
    }
}