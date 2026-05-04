package Gestor;

import Cartas.Carta;
import Cartas.Color;
import MODELO.Jugador;
import MODELO.Partida;
import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import dtos.EstadoLobbyDTO;
import dtos.EstadoPartidaDTO;
import Ensambladores.EnsambladorPartida;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GestorJuego implements IPuertoAplicacion {

    private Map<String, Partida> partidasActivas = new ConcurrentHashMap<>();
    private Map<String, Map<String, String>> lobbysTemporales = new ConcurrentHashMap<>();

    @Override
    public EstadoLobbyDTO unirseLobby(String idSala, String nombreJugador, String avatar) { 
        lobbysTemporales.putIfAbsent(idSala, new ConcurrentHashMap<>());
        lobbysTemporales.get(idSala).put(nombreJugador, avatar);
        
        EstadoLobbyDTO estado = new EstadoLobbyDTO();
        estado.setJugadores(new HashMap<>(lobbysTemporales.get(idSala)));
        estado.setJuegoIniciado(false);
        return estado;
    }

    @Override
    public void iniciarPartidaDesdeLobby(String idSala) { 
        Map<String, String> jugadoresEnSala = lobbysTemporales.get(idSala);
        if (jugadoresEnSala != null) {
            registrarPartida(idSala, jugadoresEnSala);
        }
    }

    @Override
    public void registrarPartida(String idPartida, Map<String, String> jugadoresLobby) {
        Partida nuevaPartida = new Partida();
        jugadoresLobby.forEach((nombre, avatar) -> {
            nuevaPartida.agregarJugador(new Jugador(nombre, nombre, avatar)); 
        });
        nuevaPartida.iniciarPartida();
        partidasActivas.put(idPartida, nuevaPartida);
    }

    @Override
    public EstadoPartidaDTO jugarCarta(String idPartida, String idJugador, String idCarta, String nuevoColorStr) {
        Partida partida = partidasActivas.get(idPartida);
        if (partida != null) {
            Jugador j = partida.getJugadores().stream().filter(jug -> jug.getId().equals(idJugador)).findFirst().orElse(null);
            if (j != null) {
                Carta carta = j.getMano().stream().filter(c -> c.getFotoId().equals(idCarta)).findFirst().orElse(null);
                if (carta != null) {
                    try {
                        Color color = Color.valueOf(nuevoColorStr.toUpperCase());
                        partida.realizarJugada(idJugador, carta, color);
                    } catch (Exception e) {}
                }
            }
        }
        return obtenerEstadoPartida(idPartida, idJugador);
    }

    @Override
    public EstadoPartidaDTO robarCarta(String idPartida, String idJugador) {
        Partida p = partidasActivas.get(idPartida);
        if (p != null) p.robarCarta(idJugador);
        return obtenerEstadoPartida(idPartida, idJugador);
    }

    @Override
    public EstadoPartidaDTO cantarUno(String idPartida, String idJugador) {
        Partida p = partidasActivas.get(idPartida);
        if (p != null) p.verificarUno(idJugador);
        return obtenerEstadoPartida(idPartida, idJugador);
    }

    @Override
    public EstadoPartidaDTO obtenerEstadoPartida(String idPartida, String idJugador) {
        Partida p = partidasActivas.get(idPartida);
        return (p == null) ? null : EnsambladorPartida.mapear(p, idJugador);
    }

    @Override public EstadoLobbyDTO obtenerEstadoLobby(String idSala) { return null; }
}