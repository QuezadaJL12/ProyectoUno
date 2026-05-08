package Cliente;

import dtos.CartaDTO;
import dtos.EstadoLobbyDTO;
import dtos.EstadoPartidaDTO;
import dtos.RespuestaLobbyDTO;
import interfaces.ObservadorRed;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteHilo extends Thread {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    
    private String idJugador;
    private String idPartida;
    private String host;
    private int puerto;
    
    private List<ObservadorRed> observadores = new ArrayList<>();
    private boolean conectado = false;

    public ClienteHilo(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void agregarObservador(ObservadorRed obs) {
        this.observadores.add(obs);
    }


    public void unirseLobby(String idSala, String nombreJugador, String rutaAvatar) {
        this.idPartida = idSala;
        this.idJugador = nombreJugador;
        try {
            if (out == null) conectar();
            Map<String, Object> peticion = new HashMap<>();
            peticion.put("accion", "UNIRSE_LOBBY");
            peticion.put("idSala", idSala);
            peticion.put("nombre", nombreJugador);
            peticion.put("avatar", rutaAvatar);
            out.writeObject(peticion);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciarPartida(String idSala) {
        try {
            Map<String, Object> peticion = new HashMap<>();
            peticion.put("accion", "INICIAR_PARTIDA");
            peticion.put("idSala", idSala);
            out.writeObject(peticion);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jugarCarta(CartaDTO carta, String nuevoColor) {
        try {
            Map<String, Object> peticion = new HashMap<>();
            peticion.put("accion", "JUGAR_CARTA");
            peticion.put("idPartida", this.idPartida);
            peticion.put("idJugador", this.idJugador);
            peticion.put("idCarta", carta.getFotoId());
            peticion.put("nuevoColor", nuevoColor);
            out.writeObject(peticion);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void robarCarta() {
        enviarPeticionSimple("ROBAR_CARTA");
    }

    public void cantarUno() {
        enviarPeticionSimple("CANTAR_UNO");
    }

    public void pedirEstadoPartida() {
        enviarPeticionSimple("OBTENER_ESTADO");
    }

    private void enviarPeticionSimple(String accion) {
        try {
            Map<String, Object> peticion = new HashMap<>();
            peticion.put("accion", accion);
            peticion.put("idPartida", this.idPartida);
            peticion.put("idJugador", this.idJugador);
            out.writeObject(peticion);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void conectar() throws IOException {
        if (!conectado) {
            try {
                this.socket = new Socket(host, puerto);
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(socket.getInputStream());
                this.conectado = true;
            } catch (IOException e) {
                
                for (ObservadorRed obs : observadores) {
                    RespuestaLobbyDTO res = new RespuestaLobbyDTO("No se pudo conectar con el servidor");
                    obs.onError(res);
                }
                
                conectado = false;
                
                throw e; 
                
            }
        }
    }
    
    @Override
    public void run() {
        try {
            conectar();
            while (conectado) {
                Object respuesta = in.readObject();
                
                if (respuesta instanceof EstadoLobbyDTO) {
                    for (ObservadorRed obs : observadores) {
                        obs.enActualizacionLobby((EstadoLobbyDTO) respuesta);
                    }
                } else if (respuesta instanceof EstadoPartidaDTO) {
                    for (ObservadorRed obs : observadores) {
                        obs.enActualizacionPartida((EstadoPartidaDTO) respuesta);
                    }
                }
            }
        } catch (Exception e) {
            for (ObservadorRed obs : observadores) {
                RespuestaLobbyDTO res = new RespuestaLobbyDTO("El nombre de usuario o la foto de perfil ya está siendo utilizado");
                obs.onError(res);
            }
            conectado = false;
        }
    }
}