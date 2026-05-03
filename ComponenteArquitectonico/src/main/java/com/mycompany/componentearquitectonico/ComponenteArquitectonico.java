package com.mycompany.componentearquitectonico;

import Gestor.GestorJuego;
import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import servidor.ServidorUNO;
import java.util.HashMap;
import java.util.Map;

public class ComponenteArquitectonico {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA UNO MULTIJUGADOR ---");

        GestorJuego gestor = new GestorJuego();
        
        System.out.println("Preparando partida de prueba...");
        
        Map<String, String> jugadoresPrueba = new HashMap<>();
        jugadoresPrueba.put("jugador-1", "😎");
        jugadoresPrueba.put("jugador-2", "🤖");
        
        gestor.registrarPartida("partida-1", jugadoresPrueba);
        
        System.out.println("[OK] Partida 'partida-1' registrada y lista con avatares.");

        ServidorUNO servidor = new ServidorUNO((IPuertoAplicacion) gestor);
        System.out.println("[OK] Servidor de red configurado.");

        System.out.println("Arrancando Sockets...");
        servidor.iniciar();
    }
}