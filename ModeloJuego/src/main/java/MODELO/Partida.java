package MODELO;

import Cartas.Carta;
import Cartas.Color;
import Cartas.TipoCarta; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Partida {
    private String id;
    private List<Jugador> jugadores;
    private Stack<Carta> mazo;
    private Stack<Carta> descarte;
    private Color colorActual;
    private boolean sentidoHorario;
    private int indiceTurnoActual;
    private Jugador ganador = null;
    private int acumuladoCartas = 0;

    public Partida() {
        this.id = java.util.UUID.randomUUID().toString();
        this.jugadores = new ArrayList<>();
        this.mazo = new FabricaMazo().crearMazoOficial();
        this.descarte = new Stack<>();
        this.sentidoHorario = true;
        this.indiceTurnoActual = 0;
    }

    public void iniciarPartida() {
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

    public void realizarJugada(String idJugador, Carta carta, Color nuevoColor) {
        if (hayGanador()) return; 

        Jugador jugadorActual = jugadores.get(indiceTurnoActual);
        if (!jugadorActual.getId().equals(idJugador)) return;

        if (acumuladoCartas > 0) {
            if (carta.getTipo() != TipoCarta.TOMA_DOS && carta.getTipo() != TipoCarta.TOMA_CUATRO) {
                return; 
            }
        }

        Carta cima = descarte.peek();
        
        if (!carta.esJugable(cima, colorActual)) return;

        jugadorActual.getMano().remove(carta);
        descarte.push(carta);
        
        if (jugadorActual.getMano().isEmpty()) {
            this.ganador = jugadorActual;
            return; 
        }

        if (jugadorActual.getMano().size() == 1 && !jugadorActual.isDijoUno()) {
            robarCartaPara(jugadorActual);
            robarCartaPara(jugadorActual);
        }

        jugadorActual.setDijoUno(false);

        if (carta.getTipo() == TipoCarta.TOMA_DOS) {
            acumuladoCartas += 2;
        } else if (carta.getTipo() == TipoCarta.TOMA_CUATRO) {
            acumuladoCartas += 4;
        }

        if (carta.getColor() == Color.ESPECIAL) {
            this.colorActual = (nuevoColor == null) ? Color.ROJO : nuevoColor;
        } else {
            this.colorActual = carta.getColor();
        }

        procesarEfecto(carta);
    }

    public void verificarUno(String idJugador) {
        for (Jugador j : jugadores) {
            if (j.getId().equals(idJugador)) {
                j.setDijoUno(true);
                break;
            }
        }
    }

    public void robarCarta(String idJugador) {
        if (hayGanador()) return; 
        Jugador jugadorActual = jugadores.get(indiceTurnoActual);
        if (!jugadorActual.getId().equals(idJugador)) return;
        
        if (acumuladoCartas > 0) {
            for (int i = 0; i < acumuladoCartas; i++) robarCartaPara(jugadorActual);
            acumuladoCartas = 0;
            avanzarTurno();
        } else {
            
            robarCartaPara(jugadorActual);
        }
    }

    private void procesarEfecto(Carta carta) {
        switch (carta.getTipo()) {
            case SALTO -> {
                avanzarTurno();
                avanzarTurno();
            }
            case REVERSA -> {
                this.sentidoHorario = !sentidoHorario;
                if (jugadores.size() == 2) avanzarTurno();
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

    public void agregarJugador(Jugador j) { jugadores.add(j); }
    public Jugador getJugadorActual() { return jugadores.get(indiceTurnoActual); }
    public Carta getCimaDescarte() { return descarte.peek(); }
    public Color getColorActual() { return colorActual; }
    public List<Jugador> getJugadores() { return jugadores; }
    public boolean hayGanador() { return ganador != null; }
    public Jugador getGanador() { return ganador; }
    public int getAcumuladoCartas() { return acumuladoCartas; }

    public java.util.Map<String, Integer> calcularPuntosFinales() {
        java.util.Map<String, Integer> puntos = new java.util.HashMap<>();
        for (Jugador j : jugadores) {
            int suma = 0;
            for (Carta c : j.getMano()) {
                TipoCarta t = c.getTipo();
                if (t == TipoCarta.TOMA_CUATRO || t == TipoCarta.CAMBIO_COLOR) suma += 50;
                else if (t == TipoCarta.SALTO || t == TipoCarta.REVERSA || t == TipoCarta.TOMA_DOS) suma += 20;
                else suma += 5;
            }
            puntos.put(j.getNombre(), suma);
        }
        return puntos;
    }
}