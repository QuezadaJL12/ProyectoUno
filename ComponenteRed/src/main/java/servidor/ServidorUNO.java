package servidor;

import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Chino
 */
public class ServidorUNO {
    private final int PUERTO = 5000;
    private IPuertoAplicacion gestorPartidas;
    
    // Contador para asignar IDs únicos a los jugadores que se conectan
    private int contadorJugadores = 1;

    public ServidorUNO(IPuertoAplicacion gestorPartidas) {
        this.gestorPartidas = gestorPartidas;
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor UNO iniciado en el puerto: " + PUERTO);
            System.out.println("Esperando jugadores...");

            while (true) {
                Socket socketCliente = serverSocket.accept();
                
                // Generamos un ID dinámico basado en el orden de conexión
                String idAsignado = "jugador-" + contadorJugadores;
                
                System.out.println("¡Nuevo jugador conectado! Asignado como: " + idAsignado + 
                                   " desde " + socketCliente.getInetAddress());

                // Pasamos el ID asignado al constructor del manejador
                HiloManejadorCliente manejador = new HiloManejadorCliente(socketCliente, gestorPartidas, idAsignado);
                new Thread(manejador).start();
                
                // Incrementamos para el siguiente jugador que entre
                contadorJugadores++;
            }

        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}