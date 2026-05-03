/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.gestorpartida.Interfaces;

import dtos.EstadoLobbyDTO;
import dtos.EstadoPartidaDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chino
 */
public interface IPuertoAplicacion {

    void registrarPartida(String idPartida, Map<String, String> jugadoresLobby);
    
    EstadoPartidaDTO jugarCarta(String idPartida, String idJugador, int indice, String idCarta, String nuevoColorStr);
    EstadoPartidaDTO robarCarta(String idPartida, String idJugador);
    EstadoPartidaDTO cantarUno(String idPartida, String idJugador);
    EstadoPartidaDTO obtenerEstadoPartida(String idPartida, String idJugador);
    
    // Asegúrate de que este también tenga el parámetro avatar
    EstadoLobbyDTO unirseLobby(String idSala, String nombreJugador, String avatar);
    EstadoLobbyDTO obtenerEstadoLobby(String idSala);
    void iniciarPartidaDesdeLobby(String idSala);
}
