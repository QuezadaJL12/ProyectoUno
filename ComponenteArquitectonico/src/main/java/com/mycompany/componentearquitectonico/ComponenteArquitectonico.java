package com.mycompany.componentearquitectonico;

import Gestor.GestorJuego;
import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import servidor.ServidorUNO;
import java.util.Arrays;
import java.util.List;

public class ComponenteArquitectonico {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA UNO MULTIJUGADOR ---");

        GestorJuego gestor = new GestorJuego();
        
        System.out.println("Preparando partida de prueba...");
        
        // Creamos la lista de IDs de los jugadores esperados
        List<String> jugadores = Arrays.asList("jugador-1", "jugador-2");
        
        // El gestor se encarga de instanciar la Partida y agregar los jugadores
        gestor.registrarPartida("partida-1", jugadores);
        
        System.out.println("[OK] Partida 'partida-1' registrada y lista.");

        ServidorUNO servidor = new ServidorUNO((IPuertoAplicacion) gestor);
        System.out.println("[OK] Servidor de red configurado.");

        System.out.println("Arrancando Sockets...");
        servidor.iniciar();
    }
}