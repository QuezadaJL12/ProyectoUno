package com.mycompany.componentearquitectonico;

import Gestor.GestorJuego;
import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import servidor.ServidorUNO;

/**
 * 
 * Se encarga de orquestar el Gestor de Juego y el Servidor de Red.
 */
public class ComponenteArquitectonico {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA UNO MULTIJUGADOR ---");

        GestorJuego gestor = new GestorJuego();
        System.out.println("[OK] Gestor de juego inicializado. Esperando jugadores...");

       
        ServidorUNO servidor = new ServidorUNO((IPuertoAplicacion) gestor);
        System.out.println("[OK] Servidor de red configurado.");

        System.out.println("Arrancando Sockets...");
        servidor.iniciar();
    }
}