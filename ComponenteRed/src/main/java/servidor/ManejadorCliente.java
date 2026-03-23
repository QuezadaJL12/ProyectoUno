/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Chino
 */
public class ManejadorCliente extends Thread {
    private Socket socket;
    private ServidorUNO servidor;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    public ManejadorCliente(Socket socket, ServidorUNO servidor) {
        this.socket = socket;
        this.servidor = servidor;
        try {
        
            this.salida = new ObjectOutputStream(socket.getOutputStream());
            this.entrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
              
                Object recibido = entrada.readObject();
                System.out.println("El servidor recibio una accion del cliente");
                
             
                servidor.broadcast(recibido);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cliente desconectado.");
        } finally {
            desconectar();
        }
    }

    public void enviarObjeto(Object objeto) {
        try {
            salida.writeObject(objeto);
            salida.flush(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void desconectar() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
