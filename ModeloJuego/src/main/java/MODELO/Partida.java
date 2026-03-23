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
        if (jugadores.size() < 2) throw new RuntimeException("Faltan jugadores");
        Collections.shuffle(mazo);
        for (Jugador j : jugadores) {
            for (int i = 0; i < 7; i++) j.recibirCarta(mazo.pop());
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
        if (!jugadorActual.getId().equals(idJugador)) throw new RuntimeException("No es tu turno");

        Carta carta = jugadorActual.getMano().get(indiceCarta);
        if (!carta.esJugable(descarte.peek(), colorActual)) throw new RuntimeException("Jugada inválida");

        jugadorActual.getMano().remove(indiceCarta);
        descarte.push(carta);
        
        if (carta.getColor() == Color.ESPECIAL) {
            this.colorActual = nuevoColor;
        } else {
            this.colorActual = carta.getColor();
        }

        procesarEfecto(carta);
    }

    private void procesarEfecto(Carta carta) {
        switch (carta.getTipo()) {
            case SALTO -> { avanzarTurno(); avanzarTurno(); }
            case REVERSA -> {
                this.sentidoHorario = !sentidoHorario;
                if (jugadores.size() == 2) avanzarTurno();
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
            default -> avanzarTurno();
        }
    }

    private void robarCartaPara(Jugador j) {
        if (mazo.isEmpty()) rellenarMazo();
        if (!mazo.isEmpty()) j.recibirCarta(mazo.pop());
    }

    private void rellenarMazo() {
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

    public void agregarJugador(Jugador j) { jugadores.add(j); }
    public Jugador getJugadorActual() { return jugadores.get(indiceTurnoActual); }

public Carta getCimaDescarte() {
    return descarte.peek();
}

public Color getColorActual() {
    return colorActual;
}

public List<Jugador> getJugadores() {
    return jugadores;
}
}
