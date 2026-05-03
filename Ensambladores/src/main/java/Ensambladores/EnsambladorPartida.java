package Ensambladores;

import Cartas.Carta;
import MODELO.Jugador;
import MODELO.Partida;
import dtos.CartaDTO;
import dtos.EstadoPartidaDTO;
import dtos.JugadorResumenDTO;
import java.util.ArrayList;
import java.util.List;

public class EnsambladorPartida {

    public static EstadoPartidaDTO mapear(Partida partida, String idJugadorSolicitante) {
        EstadoPartidaDTO dto = new EstadoPartidaDTO();

        Carta cima = partida.getCimaDescarte();
        dto.setCartaEnCima(new CartaDTO(cima.getColor().name(), cima.getTipo().name(), cima.getFotoId()));
        dto.setColorActual(partida.getColorActual().name());
        
        dto.setIdJugadorTurno(partida.getJugadorActual().getId());
        
        dto.setHayGanador(partida.hayGanador());
        if (partida.hayGanador()) {
            dto.setIdGanador(partida.getGanador().getId());
        }

        List<JugadorResumenDTO> rivales = new ArrayList<>();
        List<CartaDTO> miMano = new ArrayList<>();

        for (Jugador j : partida.getJugadores()) {
            rivales.add(new JugadorResumenDTO(j.getId(), j.getNombre(), j.getMano().size()));

            if (j.getId().equals(idJugadorSolicitante)) {
                for (Carta c : j.getMano()) {
                    miMano.add(new CartaDTO(c.getColor().name(), c.getTipo().name(), c.getFotoId()));
                }
            }
        }
        
        dto.setRivales(rivales);
        dto.setMiMano(miMano);

        return dto;
    }
}