package com.mycompany.gestorpartida.Interfaces;

import dtos.EstadoLobbyDTO;
import dtos.EstadoPartidaDTO;
import java.util.Map;

public interface IPuertoAplicacion {

    void registrarPartida(String idPartida, Map<String, String> jugadoresLobby);
    
    EstadoPartidaDTO jugarCarta(String idPartida, String idJugador, String idCarta, String nuevoColorStr);
    
    EstadoPartidaDTO robarCarta(String idPartida, String idJugador);
    EstadoPartidaDTO cantarUno(String idPartida, String idJugador);
    EstadoPartidaDTO obtenerEstadoPartida(String idPartida, String idJugador);
    
    EstadoLobbyDTO unirseLobby(String idSala, String nombreJugador, String avatar);
    EstadoLobbyDTO obtenerEstadoLobby(String idSala);
    void iniciarPartidaDesdeLobby(String idSala);
}