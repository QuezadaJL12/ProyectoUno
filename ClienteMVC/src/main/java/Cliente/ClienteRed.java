package Cliente;

import dtos.CartaDTO;
import dtos.EstadoPartidaDTO;
import Cartas.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteRed {

    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public void conectar() {
        try {
            socket = new Socket("localhost", 5000);
            salida = new ObjectOutputStream(socket.getOutputStream());
            salida.flush();
            entrada = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
        }
    }

    public EstadoPartidaDTO pedirEstadoActual() {
        try {
            salida.writeObject("OBTENER_ESTADO");
            salida.flush();
            return (EstadoPartidaDTO) entrada.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public EstadoPartidaDTO pedirRobarCarta() {
        try {
            salida.writeObject("ROBAR_CARTA");
            salida.flush();
            return (EstadoPartidaDTO) entrada.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public EstadoPartidaDTO pedirJugarCarta(CartaDTO carta, Color color) {
        try {
            salida.writeObject("JUGAR_CARTA");
            salida.writeObject(carta);
            salida.writeObject(color != null ? color.toString() : null);
            salida.flush();
            return (EstadoPartidaDTO) entrada.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public dtos.EstadoPartidaDTO pedirCantarUno() {
        try {
            salida.writeObject("CANTAR_UNO");

            salida.flush();
            return (dtos.EstadoPartidaDTO) entrada.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
