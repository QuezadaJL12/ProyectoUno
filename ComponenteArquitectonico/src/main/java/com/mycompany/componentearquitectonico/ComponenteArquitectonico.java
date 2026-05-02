package com.mycompany.componentearquitectonico;

import Gestor.GestorJuego;
import MODELO.Partida;
import MODELO.Jugador;
import servidor.ServidorUNO;

public class ComponenteArquitectonico {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA UNO MULTIJUGADOR ---");

        GestorJuego gestor = new GestorJuego();
        
        System.out.println("Preparando partida de prueba...");
        Partida partidaPrueba = new Partida("partida-1");
        
       
        partidaPrueba.agregarJugador(new Jugador("jugador-1", "Chino")); 
        partidaPrueba.agregarJugador(new Jugador("jugador-2", "Rival"));
        
        partidaPrueba.iniciarJuego(); 
        
        gestor.registrarPartida(partidaPrueba); 
        System.out.println("[OK] Partida 'partida-1' registrada y lista.");
      

        ServidorUNO servidor = new ServidorUNO(gestor);
        System.out.println("[OK] Servidor de red configurado.");

        System.out.println("Arrancando Sockets...");
        servidor.iniciar();
    }
}