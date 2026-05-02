package Cliente;

import dtos.CartaDTO;
import dtos.EstadoPartidaDTO;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteRed {
    private final String HOST = "localhost";
    private final int PUERTO = 5000;
    
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public void conectar() {
        try {
            socket = new Socket(HOST, PUERTO);
            salida = new ObjectOutputStream(socket.getOutputStream());
            salida.flush(); 
            entrada = new ObjectInputStream(socket.getInputStream());
            System.out.println("[CLIENTE] Conectado al servidor UNO con éxito.");
        } catch (Exception e) {
            System.err.println("[CLIENTE] Error al conectar: " + e.getMessage());
        }
    }

    /**
     * Nuevo método para sincronización automática.
     * Envía la petición "OBTENER_ESTADO" al servidor.
     */
    public EstadoPartidaDTO pedirEstadoActual() {
        try {
            if (socket == null || socket.isClosed()) return null;
            
            salida.writeObject("OBTENER_ESTADO");
            salida.flush();
            
            return (EstadoPartidaDTO) entrada.readObject();
        } catch (Exception e) {
            System.err.println("Error al obtener estado actual: " + e.getMessage());
            return null;
        }
    }

    public EstadoPartidaDTO pedirRobarCarta() {
        try {
            salida.writeObject("ROBAR_CARTA");
            salida.flush();
            return (EstadoPartidaDTO) entrada.readObject();
        } catch (Exception e) {
            System.err.println("Error al robar carta: " + e.getMessage());
            return null;
        }
    }

    public EstadoPartidaDTO pedirJugarCarta(CartaDTO carta) {
        try {
            salida.writeObject("JUGAR_CARTA");
            salida.writeObject(carta); // Enviamos el objeto carta al servidor
            salida.flush();
            
            return (EstadoPartidaDTO) entrada.readObject();
        } catch (Exception e) {
            System.err.println("Error al jugar carta: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Cierra la conexión de forma limpia.
     */
    public void desconectar() {
        try {
            if (socket != null) socket.close();
        } catch (Exception e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}