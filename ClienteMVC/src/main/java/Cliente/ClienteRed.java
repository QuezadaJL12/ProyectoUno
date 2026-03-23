package Cliente;

import Controlador.ControladorPrincipal;
import dtos.EstadoJuegoDTO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteRed {

    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private String ip;
    private int puerto;
    private ControladorPrincipal controlador;

    public ClienteRed(String ip, int puerto, ControladorPrincipal controlador) {
        this.ip = ip;
        this.puerto = puerto;
        this.controlador = controlador;
    }

    public void conectar() {
        try {
            this.socket = new Socket(ip, puerto);
            this.salida = new ObjectOutputStream(socket.getOutputStream());
            this.entrada = new ObjectInputStream(socket.getInputStream());
            iniciarEscucha();
        } catch (IOException e) {
            System.err.println("Error conexión: " + e.getMessage());
        }
    }

    public void enviarAccion(Object accion) {
        try {
            salida.writeObject(accion);
            salida.flush();
        } catch (IOException e) {
            System.err.println("Error envío: " + e.getMessage());
        }
    }

    private void iniciarEscucha() {
        Thread hilo = new Thread(() -> {
            try {
                while (true) {
                    Object recibido = entrada.readObject();
                    if (recibido instanceof EstadoJuegoDTO && controlador != null) {
                        controlador.recibirActualizacion((EstadoJuegoDTO) recibido);
                    }
                }
            } catch (Exception e) {
                System.out.println("Desconectado.");
            }
        });
        hilo.setDaemon(true);
        hilo.start();
    }
}