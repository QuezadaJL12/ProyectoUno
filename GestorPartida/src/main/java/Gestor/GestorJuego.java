package Gestor;

import Cartas.Color;
import Ensambladores.EnsambladorPartida;
import MODELO.Partida;
import com.mycompany.gestorpartida.Interfaces.IPuertoAplicacion;
import dtos.EstadoPartidaDTO;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chino
 */
public class GestorJuego implements IPuertoAplicacion {

    private Map<String, Partida> partidasActivas;

    public GestorJuego() {
        this.partidasActivas = new HashMap<>();
    }

    public void registrarPartida(Partida p) {
        partidasActivas.put(p.getId(), p);
    }

    @Override
    public EstadoPartidaDTO jugarCarta(String idPartida, String idJugador, int indiceCarta, String nuevoColorStr) {
      
        Partida partida = partidasActivas.get(idPartida);
        if (partida == null) return null;

        Color color = null;
        try {
            if (nuevoColorStr != null && !nuevoColorStr.isEmpty()) {
                color = Color.valueOf(nuevoColorStr.toUpperCase()); 
            }
        } catch (Exception e) {
            color = Color.ROJO; 
        }

   
        partida.realizarJugada(idJugador, String.valueOf(indiceCarta), color); 
        
        return EnsambladorPartida.mapear(partida, idJugador);
    }

    @Override
    public EstadoPartidaDTO robarCarta(String idPartida, String idJugador) {
        Partida partida = partidasActivas.get(idPartida);
        if (partida == null) return null;

        partida.robarCarta(idJugador);
        return EnsambladorPartida.mapear(partida, idJugador);
    }

    @Override
    public EstadoPartidaDTO iniciarPartida(String idPartida, String idJugadorSolicitante) {
        Partida partida = partidasActivas.get(idPartida);
        if (partida == null) return null;
        
        partida.iniciarJuego();
        return EnsambladorPartida.mapear(partida, idJugadorSolicitante);
    }

    @Override
    public EstadoPartidaDTO obtenerEstadoPartida(String idPartida, String idJugador) {
        Partida partida = partidasActivas.get(idPartida);
        if (partida == null) return null;

        return EnsambladorPartida.mapear(partida, idJugador);
    }

    @Override
    public void abandonarPartida(String idPartida, String idJugador) {
        Partida partida = partidasActivas.get(idPartida);
        if (partida != null) {
            System.out.println("[GESTOR] El jugador " + idJugador + " ha abandonado la partida " + idPartida);
       
        }
    }
}