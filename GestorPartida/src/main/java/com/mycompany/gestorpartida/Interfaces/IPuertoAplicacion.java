/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.gestorpartida.Interfaces;

import dtos.EstadoPartidaDTO;



/**
 *
 * @author Chino
 */
public interface IPuertoAplicacion {
 // Para cuando el jugador tira una carta
    EstadoPartidaDTO jugarCarta(String idPartida, String idJugador, int indiceCarta, String nuevoColor);
    
    // Para cuando el jugador pasa turno robando del mazo
    EstadoPartidaDTO robarCarta(String idPartida, String idJugador);
    
    //  Para iniciar la partida
    EstadoPartidaDTO iniciarPartida(String idPartida, String idJugadorSolicitante);
    
    EstadoPartidaDTO obtenerEstadoPartida(String idPartida, String idJugador);
    
    void abandonarPartida(String idPartida, String idJugador);
}
