package servidorPrueba;

import dtos.EstadoJuegoDTO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author Chino
 */
public class ServidorPrueba {

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(5000)) {
            System.out.println("Servidor UNO iniciado en el puerto 5000...");
            System.out.println("Esperando al Chino...");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("¡Cliente conectado desde " + cliente.getInetAddress() + "!");

             
                ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

           
                EstadoJuegoDTO prueba = new EstadoJuegoDTO();
                
             
                prueba.cartaCimaId = "5_Rojo"; 
                prueba.colorActual = "Rojo";
                
                // Lista de cartas para la mano del jugador
                prueba.misCartasIds = Arrays.asList(
                    "1_Azul", 
                    "2_Verde", 
                    "3_Amarillo", 
                    "Carta_Reversa", 
                    "Carta_MasCuatro"
                );
                
                prueba.esMiTurno = true;
                prueba.turnoDeNombre = "Chino";

          
                salida.writeObject(prueba);
                salida.flush();
                
                System.out.println("Datos de prueba enviados con éxito.");
                
               
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}