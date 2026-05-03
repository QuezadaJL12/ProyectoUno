package Gestor;

import Cartas.Carta;
import Cartas.Color;
import MODELO.Jugador;
import MODELO.Partida;
import dtos.CartaDTO;
import dtos.EstadoPartidaDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorJuego {
    private Map<String, Partida> partidas = new HashMap<>();
    private List<String> logEventos = new ArrayList<>();

    public void registrarPartida(String idPartida, List<String> idsJugadores) {
        Partida nuevaPartida = new Partida(idPartida);
        for (String idJugador : idsJugadores) {
            nuevaPartida.agregarJugador(new Jugador(idJugador, idJugador));
        }
        nuevaPartida.iniciarJuego();
        partidas.put(idPartida, nuevaPartida);
        logEventos.add("Partida " + idPartida + " iniciada.");
    }

    public EstadoPartidaDTO jugarCarta(String idPartida, String idJugador, int indice, String idCarta, String nuevoColorStr) {
        Partida p = partidas.get(idPartida);
        if (p == null) return null;

        Color nuevoColor = (nuevoColorStr != null) ? Color.valueOf(nuevoColorStr) : null;
        
        p.realizarJugada(idJugador, idCarta, nuevoColor);
        
        logEventos.add("Jugador " + idJugador + " jugó una carta.");
        if (logEventos.size() > 5) logEventos.remove(0);

        return obtenerEstadoPartida(idPartida, idJugador);
    }
    
    public EstadoPartidaDTO robarCarta(String idPartida, String idJugador) {
        Partida p = partidas.get(idPartida);
        if (p == null) return null;
        
        p.robarCarta(idJugador);
        
        logEventos.add("Jugador " + idJugador + " robó una carta.");
        if (logEventos.size() > 5) logEventos.remove(0);
        
        return obtenerEstadoPartida(idPartida, idJugador);
    }

    public EstadoPartidaDTO obtenerEstadoPartida(String idPartida, String idJugador) {
        Partida p = partidas.get(idPartida);
        if (p == null) return null;
        
        EstadoPartidaDTO dto = new EstadoPartidaDTO();

        for (Jugador j : p.getJugadores()) {
            if (j.getId().equals(idJugador)) {
                dto.setMiMano(convertirAManoDTO(j.getMano()));
                break;
            }
        }

        Carta cima = p.getCimaDescarte();
        dto.setCartaEnCima(new CartaDTO(
            cima.getColor().toString(),
            cima.getTipo().toString(),
            cima.getFotoId()
        ));

        dto.setColorActual(p.getColorActual().toString());
        dto.setIdJugadorTurno(p.getJugadorActual().getId());
        dto.setHistorialPartida(new ArrayList<>(logEventos));
        dto.setHayGanador(p.hayGanador());
        
        if (p.hayGanador()) {
            dto.setIdGanador(p.getGanador().getId());
        }

        return dto;
    }

    private List<CartaDTO> convertirAManoDTO(List<Carta> mano) {
        List<CartaDTO> lista = new ArrayList<>();
        for (Carta c : mano) {
            lista.add(new CartaDTO(
                c.getColor().toString(),
                c.getTipo().toString(),
                c.getFotoId()
            ));
        }
        return lista;
    }
}