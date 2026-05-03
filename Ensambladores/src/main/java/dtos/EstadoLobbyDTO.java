/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chino
 */
public class EstadoLobbyDTO implements Serializable {
    private String idSala;
    private Map<String, String> jugadores; 
    private boolean puedeIniciar; 
    private boolean juegoIniciado; 

    public EstadoLobbyDTO() {
        this.jugadores = new LinkedHashMap<>();
    }

    public String getIdSala() { return idSala; }
    public void setIdSala(String idSala) { this.idSala = idSala; }
    public Map<String, String> getJugadores() { return jugadores; }
    public void setJugadores(Map<String, String> jugadores) { this.jugadores = jugadores; }
    public boolean isPuedeIniciar() { return puedeIniciar; }
    public void setPuedeIniciar(boolean puedeIniciar) { this.puedeIniciar = puedeIniciar; }
    public boolean isJuegoIniciado() { return juegoIniciado; }
    public void setJuegoIniciado(boolean juegoIniciado) { this.juegoIniciado = juegoIniciado; }
}