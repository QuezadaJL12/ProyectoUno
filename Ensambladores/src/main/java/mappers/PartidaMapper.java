/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappers;

import MODELO.Partida;
import MODELO.Jugador;
import Cartas.Carta;

import dtos.EstadoJuegoDTO;
import dtos.InfoOponenteDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chino
 */
public class PartidaMapper {

    public static EstadoJuegoDTO toDTO(Partida partida, String idJugadorReceptor) {
        EstadoJuegoDTO dto = new EstadoJuegoDTO();
        dto.idPartida = "PARTIDA-01"; 
        dto.cartaCimaId = partida.getCimaDescarte().getId();
        dto.colorActual = partida.getColorActual().name();
        dto.turnoDeNombre = partida.getJugadorActual().getNombre();
        dto.esMiTurno = partida.getJugadorActual().getId().equals(idJugadorReceptor);
        
        dto.misCartasIds = new ArrayList<>();
        dto.oponentes = new ArrayList<>();

        for (Jugador j : partida.getJugadores()) {
            if (j.getId().equals(idJugadorReceptor)) {
                for (Carta c : j.getMano()) {
                    dto.misCartasIds.add(c.getId());
                }
            } else {
                InfoOponenteDTO op = new InfoOponenteDTO();
                op.nombre = j.getNombre();
                op.cantidadCartas = j.getMano().size();
                op.suTurno = partida.getJugadorActual().getId().equals(j.getId());
                dto.oponentes.add(op);
            }
        }
        return dto;
    }
}
