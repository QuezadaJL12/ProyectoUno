package red;

import Controlador.ControladorLobby;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteHilo implements Runnable {

    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private ControladorLobby controladorLobby;

    public ClienteHilo(String ip, int puerto, ControladorLobby controladorLobby) {
        this.controladorLobby = controladorLobby;
        try {
            socket = new Socket(ip, puerto);
            salida = new ObjectOutputStream(socket.getOutputStream());
            salida.flush(); 
            entrada = new ObjectInputStream(socket.getInputStream());
            System.out.println("Conectado al servidor en " + ip + ":" + puerto);
        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }

    public void unirseLobby(String idSala, String nombre, String avatar) {
        try {
            if (salida != null) {
                salida.writeObject("UNIRSE_LOBBY");
                salida.writeObject(idSala);
                salida.writeObject(nombre);
                salida.writeObject(avatar);
                salida.flush();
            }
        } catch (Exception e) {
            System.err.println("Error enviando datos: " + e.getMessage());
        }
    }

    public void iniciarPartida(String idSala) {
        try {
            if (salida != null) {
                salida.writeObject("INICIAR_PARTIDA");
                salida.writeObject(idSala);
                salida.flush();
            }
        } catch (Exception e) {
            System.err.println("Error al iniciar: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (socket != null && !socket.isClosed()) {
                if (entrada != null) {
                    Object respuesta = entrada.readObject();
                    if (respuesta != null) {
                        controladorLobby.agregarTextoLobby("Se recibio: " + respuesta.getClass().getSimpleName() + "\n");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Desconectado del servidor. Motivo: " + e.getMessage());
            e.printStackTrace(); // Esto nos dira exactamente donde fallo
        }
    }
}