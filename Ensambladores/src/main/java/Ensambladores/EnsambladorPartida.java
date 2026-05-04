package Ensambladores;

import Cartas.Carta;
import MODELO.Jugador;
import MODELO.Partida;
import dtos.CartaDTO;
import dtos.EstadoPartidaDTO;
import dtos.JugadorResumenDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase encargada de transformar los objetos del dominio (Modelo)
 * en objetos de transferencia de datos (DTO) para ser enviados por la red.
 */
public class EnsambladorPartida {

    public static EstadoPartidaDTO mapear(Partida partida, String idJugadorSolicitante) {
        EstadoPartidaDTO dto = new EstadoPartidaDTO();

        Carta cima = partida.getCimaDescarte();
        if (cima != null) {
            dto.setCartaEnCima(new CartaDTO(cima.getId(), cima.getColor().name(), cima.getTipo().name(), cima.getFotoId()));
        }
        
        dto.setColorActual(partida.getColorActual().name());
        dto.setIdJugadorTurno(partida.getJugadorActual().getId());
        dto.setAcumulado(partida.getAcumuladoCartas()); 

        dto.setPartidaTerminada(partida.hayGanador());
        if (partida.hayGanador()) {
            dto.setGanador(partida.getGanador().getNombre());
            dto.setPuntajesFinales(partida.calcularPuntosFinales());
        }

        List<JugadorResumenDTO> rivalesResumen = new ArrayList<>();
        List<CartaDTO> miMano = new ArrayList<>();
        Map<String, Integer> conteoOponentes = new HashMap<>();
        Map<String, String> avataresOponentes = new HashMap<>();

        for (Jugador j : partida.getJugadores()) {
            if (j.getId().equals(idJugadorSolicitante)) {
                for (Carta c : j.getMano()) {
                    miMano.add(new CartaDTO(c.getId(), c.getColor().name(), c.getTipo().name(), c.getFotoId()));
                }
            } else {
                conteoOponentes.put(j.getNombre(), j.getMano().size());
                avataresOponentes.put(j.getNombre(), j.getAvatar());
                
                rivalesResumen.add(new JugadorResumenDTO(j.getId(), j.getNombre(), j.getMano().size(), j.getAvatar()));
            }
        }
        
        dto.setMiMano(miMano);
        dto.setConteoCartasOponentes(conteoOponentes);
        dto.setAvataresOponentes(avataresOponentes);
        dto.setRivales(rivalesResumen);

        return dto;
    }
}