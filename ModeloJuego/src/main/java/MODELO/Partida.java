package MODELO;

import Cartas.Carta;
import Cartas.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import servicios.ValidadorReglas;

public class Partida {
    private String id;
    private List<Jugador> jugadores;
    private Stack<Carta> mazo;
    private Stack<Carta> descarte;
    private Color colorActual;
    private boolean sentidoHorario;
    private int indiceTurnoActual;
    private Jugador ganador = null;
    private ValidadorReglas validador;

    public Partida(String id) {
        this.id = id;
        this.jugadores = new ArrayList<>();
        this.mazo = new FabricaMazo().crearMazoOficial();
        this.descarte = new Stack<>();
        this.sentidoHorario = true;
        this.indiceTurnoActual = 0;
        this.validador = new ValidadorReglas();
    }

    public void iniciarJuego() {
        if (jugadores.size() < 2) return; 
        
        Collections.shuffle(mazo);
        
        for (Jugador j : jugadores) {
            j.getMano().clear(); 
            for (int i = 0; i < 7; i++) {
                if (!mazo.isEmpty()) j.recibirCarta(mazo.pop());
            }
        }

        if (mazo.isEmpty()) rellenarMazo();
        Carta inicio = mazo.pop();
        
        while (inicio.getColor() == Color.ESPECIAL) {
            mazo.insertElementAt(inicio, 0);
            inicio = mazo.pop();
        }
        
        descarte.push(inicio);
        this.colorActual = inicio.getColor();
    }

    public void realizarJugada(String idJugador, String idCarta, Color nuevoColor) {
        if (hayGanador()) return; 

        Jugador jugadorActual = jugadores.get(indiceTurnoActual);
        
        if (!jugadorActual.getId().equals(idJugador)) {
            return; 
        }

        Carta carta = buscarCartaEnMano(jugadorActual, idCarta);
        if (carta == null) return;

        Carta cima = descarte.peek();

        if (!validador.esMovimientoValido(carta, cima, colorActual)) {
            return;
        }

        jugadorActual.getMano().remove(carta);
        descarte.push(carta);
        
        if (jugadorActual.getMano().isEmpty()) {
            this.ganador = jugadorActual;
            return; 
        }
        
        if (carta.getColor() == Color.ESPECIAL) {
            this.colorActual = (nuevoColor == null) ? Color.ROJO : nuevoColor;
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
            default -> avanzarTurno(); 
        }
    }

    public void robarCarta(String idJugador) {
        if (hayGanador()) return; 
        Jugador jugadorActual = jugadores.get(indiceTurnoActual);
        if (!jugadorActual.getId().equals(idJugador)) return;
        
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

    private Carta buscarCartaEnMano(Jugador jugador, String idCarta) {
        for (Carta c : jugador.getMano()) {
            if (c.getFotoId().equals(idCarta)) return c;
        }
        return null;
    }

    public void agregarJugador(Jugador j) { jugadores.add(j); }
    public Jugador getJugadorActual() { return jugadores.get(indiceTurnoActual); }
    public Carta getCimaDescarte() { return descarte.peek(); }
    public Color getColorActual() { return colorActual; }
    public List<Jugador> getJugadores() { return jugadores; }
    public String getId() { return id; }
    public boolean hayGanador() { return ganador != null; }
    public Jugador getGanador() { return ganador; }
}