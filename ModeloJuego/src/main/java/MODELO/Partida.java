/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import Cartas.Carta;
import Cartas.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Chino
 */
public class Partida {
    private String id;
    private List<Jugador> jugadores;
    private Stack<Carta> mazo;
    private Stack<Carta> descarte;
    private Color colorActual;
    private boolean sentidoHorario;
    private int indiceTurnoActual;

    public Partida(String id) {
        this.id = id;
        this.jugadores = new ArrayList<>();
        this.mazo = new FabricaMazo().crearMazoOficial();
        this.descarte = new Stack<>();
        this.sentidoHorario = true;
        this.indiceTurnoActual = 0;
    }

    public void iniciarJuego() {
        if (jugadores.size() < 2) throw new RuntimeException("Faltan jugadores para iniciar");
        
        Collections.shuffle(mazo);
        
        
        for (Jugador j : jugadores) {
            j.getMano().clear(); 
            for (int i = 0; i < 7; i++) {
                if (!mazo.isEmpty()) j.recibirCarta(mazo.pop());
            }
        }

        
        Carta inicio = mazo.pop();
        while (inicio.getColor() == Color.ESPECIAL) {
            mazo.insertElementAt(inicio, 0);
            inicio = mazo.pop();
        }
        
        descarte.push(inicio);
        this.colorActual = inicio.getColor();
    }

    public void realizarJugada(String idJugador, int indiceCarta, Color nuevoColor) {
        Jugador jugadorActual = jugadores.get(indiceTurnoActual);
        
       
        if (!jugadorActual.getId().equals(idJugador)) {
            throw new RuntimeException("¡No es tu turno! Es turno de: " + jugadorActual.getNombre());
        }

        Carta carta = jugadorActual.getMano().get(indiceCarta);
        Carta cima = descarte.peek();

        
        if (!carta.esJugable(cima, colorActual)) {
            throw new RuntimeException("La carta " + carta.getFotoId() + " no puede jugarse sobre " + cima.getFotoId());
        }

     
        jugadorActual.getMano().remove(indiceCarta);
        descarte.push(carta);
        
      
        if (carta.getColor() == Color.ESPECIAL) {
            if (nuevoColor == null) this.colorActual = Color.ROJO; // Default por si no mandaron nada
            else this.colorActual = nuevoColor;
        } else {
            this.colorActual = carta.getColor();
        }

        procesarEfecto(carta);
    }

    private void procesarEfecto(Carta carta) {
      
        switch (carta.getTipo()) {
            case SALTO -> {
                avanzarTurno(); 
                avanzarTurno();
            }
            case REVERSA -> {
                this.sentidoHorario = !sentidoHorario;
                if (jugadores.size() == 2) {
                    avanzarTurno();
                }
                avanzarTurno();
            }
            case TOMA_DOS -> {
                avanzarTurno();
                for(int i=0; i<2; i++) robarCartaPara(getJugadorActual());
                avanzarTurno(); 
            }
            case TOMA_CUATRO -> {
                avanzarTurno(); 
                for(int i=0; i<4; i++) robarCartaPara(getJugadorActual());
                avanzarTurno(); 
            }
            default -> {
                avanzarTurno(); 
            }
        }
    }

    public void robarCarta(String idJugador) {
        Jugador jugadorActual = jugadores.get(indiceTurnoActual);
        if (!jugadorActual.getId().equals(idJugador)) throw new RuntimeException("No es tu turno para robar");
        
        robarCartaPara(jugadorActual);
        avanzarTurno(); 
    }

    private void robarCartaPara(Jugador j) {
        if (mazo.isEmpty()) rellenarMazo();
        if (!mazo.isEmpty()) j.recibirCarta(mazo.pop());
    }

    private void rellenarMazo() {
        if (descarte.size() <= 1) return; 
        Carta cima = descarte.pop();
        mazo.addAll(descarte);
        descarte.clear();
        Collections.shuffle(mazo);
        descarte.push(cima);
    }

    private void avanzarTurno() {
        int paso = sentidoHorario ? 1 : -1;
        indiceTurnoActual = (indiceTurnoActual + paso + jugadores.size()) % jugadores.size();
    }

    // Getters y Setters
    public void agregarJugador(Jugador j) { jugadores.add(j); }
    public Jugador getJugadorActual() { return jugadores.get(indiceTurnoActual); }
    public Carta getCimaDescarte() { return descarte.peek(); }
    public Color getColorActual() { return colorActual; }
    public List<Jugador> getJugadores() { return jugadores; }
}
