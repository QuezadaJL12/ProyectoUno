package servidorPrueba;

import MODELO.Partida;
import MODELO.Jugador;
import Cartas.Carta;
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

    public static void main(String[] args) {
        // 1. Inicializamos la lógica real de tu proyecto ModeloJuego
        Partida partida = new Partida("P1");
        
        // Agregamos al Chino y a un Bot para poder iniciar
        Jugador jugadorChino = new Jugador("1", "Chino");
        partida.agregarJugador(jugadorChino);
        partida.agregarJugador(new Jugador("2", "Bot"));
        
        // Reparte 7 cartas y pone la primera en el descarte
        partida.iniciarJuego();

        try (ServerSocket servidor = new ServerSocket(5000)) {
            System.out.println("Servidor UNO iniciado en el puerto 5000...");
            System.out.println("Esperando al Chino...");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("¡Cliente conectado desde " + cliente.getInetAddress() + "!");

                ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

                // 2. Creamos el DTO basado en el estado REAL de la Partida
                EstadoJuegoDTO estado = new EstadoJuegoDTO();
                
                // Obtenemos la carta de la cima (importante que Carta tenga getFotoId)
                Carta cima = partida.getCimaDescarte();
                estado.cartaCimaId = cima.getFotoId(); 
                estado.colorActual = partida.getColorActual().toString();
                
                // Convertimos la mano de objetos Carta a una lista de Strings (IDs de fotos)
                List<String> manoIds = new ArrayList<>();
                for (Carta c : jugadorChino.getMano()) {
                    manoIds.add(c.getFotoId());
                }
                estado.misCartasIds = manoIds;
                
                // Datos de turno
                estado.esMiTurno = (partida.getJugadorActual() == jugadorChino);
                estado.turnoDeNombre = partida.getJugadorActual().getNombre();

                // 3. Enviamos el estado real al cliente
                salida.writeObject(estado);
                salida.flush();
                
                System.out.println("Datos reales de la partida enviados con éxito.");
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}