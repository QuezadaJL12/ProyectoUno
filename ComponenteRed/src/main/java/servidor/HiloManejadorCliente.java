package servidor;

import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import dtos.CartaDTO; 
import dtos.EstadoPartidaDTO;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Chino
 */
public class HiloManejadorCliente implements Runnable {

    private Socket socket;
    private IPuertoAplicacion gestor;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private String idJugador; // ID dinámico asignado a esta conexión

    // Constructor actualizado para recibir el ID del jugador
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

            System.out.println("[SERVER] Atendiendo a: " + idJugador);

            while (true) {
                try {
                    Object objPeticion = entrada.readObject();
                    if (objPeticion == null) {
                        break;
                    }

                    String peticion = (String) objPeticion;
                    System.out.println("Petición de " + idJugador + ": " + peticion);

                    if (peticion.equals("ROBAR_CARTA")) {
                        // Usamos el idJugador dinámico
                        EstadoPartidaDTO nuevoEstado = gestor.robarCarta("partida-1", idJugador);
                        salida.writeObject(nuevoEstado);
                        salida.flush();

                    } else if (peticion.equals("JUGAR_CARTA")) {
                        CartaDTO cartaAJugar = (CartaDTO) entrada.readObject();
                        
                        // Buscamos el índice real de la carta en la mano del jugador para el gestor
                        // Si tu gestor requiere el índice exacto, podrías obtenerlo aquí
                        int indice = 0; 
                        String idCarta = cartaAJugar.getFotoId();

                        // Usamos el idJugador dinámico
                        EstadoPartidaDTO nuevoEstado = gestor.jugarCarta("partida-1", idJugador, indice, idCarta);

                        salida.writeObject(nuevoEstado);
                        salida.flush();
                    }
                } catch (Exception e) {
                    System.err.println("Error en jugada de " + idJugador + ": " + e.getMessage());
                    
                    // IMPORTANTE: Si el gestor devuelve null por ser fuera de turno, 
                    // mandamos el estado actual para que el cliente no se quede bloqueado
                    EstadoPartidaDTO estadoActual = gestor.obtenerEstadoPartida("partida-1", idJugador);
                    salida.writeObject(estadoActual);
                    salida.flush();
                }
            }
        } catch (java.io.EOFException e) {
            System.out.println("El cliente " + idJugador + " cerró la conexión.");
        } catch (Exception e) {
            System.err.println("Error crítico en la conexión de " + idJugador + ": " + e.getMessage());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception ex) {
                System.err.println("Error al cerrar socket: " + ex.getMessage());
            }
        }
    }
}