package MODELO;

import Cartas.Carta;
import Cartas.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
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
    private Jugador ganador = null;

    public Partida(String id) {
        this.id = id;
        this.jugadores = new ArrayList<>();
        this.mazo = new FabricaMazo().crearMazoOficial();
        this.descarte = new Stack<>();
        this.sentidoHorario = true;
        this.indiceTurnoActual = 0;
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

    /**
     * CORRECCIÓN: Ahora buscamos la carta por su ID único (fotoId) para evitar
     * errores de índice desfasado por latencia de red.
     */
    public void realizarJugada(String idJugador, String idCarta, Color nuevoColor) {
        if (hayGanador()) return; 

        Jugador jugadorActual = jugadores.get(indiceTurnoActual);
        
        if (!jugadorActual.getId().equals(idJugador)) {
            System.out.println("[SERVER] No es turno de " + idJugador);
            return; 
        }

        // Buscamos la carta físicamente en la mano por su fotoId
        Carta carta = null;
        int indiceReal = -1;
        for (int i = 0; i < jugadorActual.getMano().size(); i++) {
            if (jugadorActual.getMano().get(i).getFotoId().equals(idCarta)) {
                carta = jugadorActual.getMano().get(i);
                indiceReal = i;
                break;
            }
        }

        if (carta == null) {
            System.out.println("[SERVER] El jugador no tiene la carta: " + idCarta);
            return;
        }

        Carta cima = descarte.peek();

        if (!carta.esJugable(cima, colorActual)) {
            System.out.println("[SERVER] Jugada inválida: " + carta.getFotoId() + " sobre " + cima.getFotoId());
            return;
        }

        // Ejecutar jugada
        jugadorActual.getMano().remove(indiceReal);
        descarte.push(carta);
        
        if (jugadorActual.getMano().isEmpty()) {
            this.ganador = jugadorActual;
            return; 
        }
        
        // Actualizar color de la mesa
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
        
        if (!jugadorActual.getId().equals(idJugador)) {
            System.out.println("[SERVER] Intento de robo ilegal por: " + idJugador);
            return;
        }
        
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
    public String getId() { return id; }
    public boolean hayGanador() { return ganador != null; }
    public Jugador getGanador() { return ganador; }
}