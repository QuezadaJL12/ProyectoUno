package Gestor;

import Cartas.Carta;
import Cartas.Color;
import MODELO.Jugador;
import MODELO.Partida;
import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import dtos.CartaDTO;
import dtos.EstadoPartidaDTO;
import dtos.EstadoLobbyDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import Ensambladores.EnsambladorPartida;

public class GestorJuego implements IPuertoAplicacion {
    
    private Map<String, Partida> partidas = new HashMap<>();
    private Map<String, Map<String, String>> salasLobby = new HashMap<>();
    private List<String> logEventos = new ArrayList<>();

    
    @Override
    public EstadoLobbyDTO unirseLobby(String idSala, String nombreJugador, String avatarDeseado) {
        salasLobby.putIfAbsent(idSala, new LinkedHashMap<>());
        Map<String, String> jugadoresEnSala = salasLobby.get(idSala);

        // 1. Arreglo maestro con todos los avatares disponibles
        String[] todosLosAvatares = {
            "/avatares/avatar1.png", "/avatares/avatar2.png", 
            "/avatares/avatar3.png", "/avatares/avatar4.png", 
            "/avatares/avatar5.png", "/avatares/avatar6.png"
        };

        String avatarFinal = avatarDeseado;

        if (jugadoresEnSala.containsValue(avatarDeseado)) {
            for (String avatarLibre : todosLosAvatares) {
                if (!jugadoresEnSala.containsValue(avatarLibre)) {
                    avatarFinal = avatarLibre;
                    break;
                }
            }
        }

        jugadoresEnSala.put(nombreJugador, avatarFinal);
        
        return obtenerEstadoLobby(idSala);
    }

    @Override
    public EstadoLobbyDTO obtenerEstadoLobby(String idSala) {
        Map<String, String> jugadoresEnSala = salasLobby.getOrDefault(idSala, new LinkedHashMap<>());
        
        EstadoLobbyDTO dto = new EstadoLobbyDTO();
        dto.setIdSala(idSala);
        dto.setJugadores(jugadoresEnSala);
        dto.setPuedeIniciar(jugadoresEnSala.size() >= 2);
        dto.setJuegoIniciado(partidas.containsKey(idSala)); 
        
        return dto;
    }

    @Override
    public void iniciarPartidaDesdeLobby(String idSala) {
        Map<String, String> jugadoresLobby = salasLobby.get(idSala);
        if (jugadoresLobby != null && jugadoresLobby.size() >= 2) {
            registrarPartida(idSala, jugadoresLobby); 
        }
    }



    @Override
    public void registrarPartida(String idPartida, Map<String, String> jugadoresLobby) {
        Partida nuevaPartida = new Partida(idPartida);
        
        // Creamos cada objeto Jugador con su ID, nombre y el avatar recuperado del lobby
        for (Map.Entry<String, String> entry : jugadoresLobby.entrySet()) {
            nuevaPartida.agregarJugador(new Jugador(entry.getKey(), entry.getKey(), entry.getValue()));
        }
        
        nuevaPartida.iniciarJuego();
        partidas.put(idPartida, nuevaPartida);
        logEventos.add("Partida " + idPartida + " iniciada.");
    }

    @Override
    public EstadoPartidaDTO jugarCarta(String idPartida, String idJugador, int indice, String idCarta, String nuevoColorStr) {
        Partida p = partidas.get(idPartida);
        if (p == null) return null;

        Color nuevoColor = (nuevoColorStr != null) ? Color.valueOf(nuevoColorStr) : null;
        
        p.realizarJugada(idJugador, idCarta, nuevoColor);
        
        logEventos.add("Jugador " + idJugador + " jugó una carta.");
        if (logEventos.size() > 5) logEventos.remove(0);

        return obtenerEstadoPartida(idPartida, idJugador);
    }
    
    @Override
    public EstadoPartidaDTO robarCarta(String idPartida, String idJugador) {
        Partida p = partidas.get(idPartida);
        if (p == null) return null;
        
        p.robarCarta(idJugador);
        
        logEventos.add("Jugador " + idJugador + " robó una carta.");
        if (logEventos.size() > 5) logEventos.remove(0);
        
        return obtenerEstadoPartida(idPartida, idJugador);
    }

    @Override
    public EstadoPartidaDTO cantarUno(String idPartida, String idJugador) {
        Partida p = partidas.get(idPartida);
        if (p == null) return null;

        p.gritarUno(idJugador);
        
        logEventos.add("Jugador " + idJugador + " gritó ¡UNO!");
        if (logEventos.size() > 5) logEventos.remove(0);

        return obtenerEstadoPartida(idPartida, idJugador);
    }

    @Override
    public EstadoPartidaDTO obtenerEstadoPartida(String idPartida, String idJugador) {
        Partida p = partidas.get(idPartida);
        if (p == null) return null;
        
        EstadoPartidaDTO dto = EnsambladorPartida.mapear(p, idJugador);
        dto.setHistorialPartida(new ArrayList<>(logEventos));
        return dto;
    }
}