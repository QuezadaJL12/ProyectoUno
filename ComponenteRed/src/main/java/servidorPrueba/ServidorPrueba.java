package servidorPrueba;

import MODELO.Partida;
import MODELO.Jugador;
import Cartas.Carta;
import dtos.AccionDTO;
import dtos.EstadoJuegoDTO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chino
 */
public class ServidorPrueba {

    // Listas para manejar a los dos jugadores reales
    private static List<ObjectOutputStream> salidas = new ArrayList<>();
    private static List<Jugador> jugadoresReal = new ArrayList<>();
    private static Partida partida = new Partida("P1");

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(5000)) {
            System.out.println("Servidor UNO iniciado en el puerto 5000...");
            System.out.println("Esperando a 2 jugadores  para iniciar...");

         
            while (jugadoresReal.size() < 2) {
                Socket socket = servidor.accept();
                System.out.println("¡Jugador conectado desde " + socket.getInetAddress() + "!");

                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

               
                String id = String.valueOf(jugadoresReal.size() + 1);
                String nombre = "Jugador " + id;
                Jugador nuevoJugador = new Jugador(id, nombre);
                
                partida.agregarJugador(nuevoJugador);
                jugadoresReal.add(nuevoJugador);
                salidas.add(salida);

               
                iniciarEscuchaCliente(entrada, nuevoJugador);
            }

      
            partida.iniciarJuego();
            System.out.println("¡Juego iniciado! Repartiendo cartas...");
            actualizarTodos();

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

private static void iniciarEscuchaCliente(ObjectInputStream entrada, Jugador j) {
        new Thread(() -> {
            try {
                while (true) {
                    Object recibido = entrada.readObject();
                    if (recibido instanceof AccionDTO) {
                        AccionDTO accion = (AccionDTO) recibido;
                        
                        
                        if (partida.getJugadorActual().getId().equals(j.getId())) {
                            
                            // CASO 1: JUGAR CARTA
                            if (accion.tipo == AccionDTO.TipoAccion.JUGAR_CARTA) {
                                try {
                                    partida.realizarJugada(j.getId(), accion.indiceCarta, null);
                                    System.out.println(j.getNombre() + " realizó una jugada.");
                                } catch (Exception e) {
                                    System.out.println("Movimiento inválido de " + j.getNombre() + ": " + e.getMessage());
                                }
                            } 
                            
                         
                            else if (accion.tipo == AccionDTO.TipoAccion.ROBAR_CARTA) {
                                try {
                                    partida.robarCarta(j.getId());
                                    System.out.println(j.getNombre() + " robó una carta.");
                                } catch (Exception e) {
                                    System.out.println("Error al robar: " + e.getMessage());
                                }
                            }
                            
                           
                            actualizarTodos();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(j.getNombre() + " se ha desconectado.");
            }
        }).start();
    }

    private static void actualizarTodos() {
        for (int i = 0; i < salidas.size(); i++) {
            try {
                enviarEstado(salidas.get(i), partida, jugadoresReal.get(i));
            } catch (IOException e) {
                System.err.println("Error al actualizar a un jugador.");
            }
            
        }
    }

    private static void enviarEstado(ObjectOutputStream salida, Partida p, Jugador j) throws IOException {
        EstadoJuegoDTO estado = new EstadoJuegoDTO();
        Carta cima = p.getCimaDescarte();
        estado.cartaCimaId = cima.getFotoId();
        estado.colorActual = p.getColorActual().toString();

        List<String> manoIds = new ArrayList<>();
        for (Carta c : j.getMano()) {
            manoIds.add(c.getFotoId());
        }
        estado.misCartasIds = manoIds;

        estado.esMiTurno = (p.getJugadorActual().getId().equals(j.getId()));
        estado.turnoDeNombre = p.getJugadorActual().getNombre();

        salida.reset();
        salida.writeObject(estado);
        salida.flush();
    }
}