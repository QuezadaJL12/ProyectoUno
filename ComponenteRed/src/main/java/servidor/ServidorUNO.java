package servidor;

import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServidorUNO {
    private final int PUERTO = 5000;
    private IPuertoAplicacion gestorPartidas;
    public static List<HiloManejadorCliente> clientes = new CopyOnWriteArrayList<>();

    public ServidorUNO(IPuertoAplicacion gestorPartidas) {
        this.gestorPartidas = gestorPartidas;
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor UNO iniciado en el puerto: " + PUERTO);
            System.out.println("Esperando jugadores...");

            while (true) {
                Socket socketCliente = serverSocket.accept();
                System.out.println("¡Nueva conexión desde " + socketCliente.getInetAddress() + "!");

                HiloManejadorCliente manejador = new HiloManejadorCliente(socketCliente, gestorPartidas);
                clientes.add(manejador);
                new Thread(manejador).start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}