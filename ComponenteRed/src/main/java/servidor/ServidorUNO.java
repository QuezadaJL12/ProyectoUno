/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author Chino
 */
public class ServidorUNO extends Thread {
    private int puerto = 5000;
    private ServerSocket serverSocket;
    private List<ManejadorCliente> clientes = new ArrayList<>();

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor UNO iniciado en el puerto " + puerto);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nuevo jugador conectado!");
                
                ManejadorCliente cliente = new ManejadorCliente(socket, this);
                clientes.add(cliente);
                cliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para mandarle el EstadoJuegoDTO a todos los conectados
    public void broadcast(Object objeto) {
        for (ManejadorCliente c : clientes) {
            c.enviarObjeto(objeto);
        }
    }
}
