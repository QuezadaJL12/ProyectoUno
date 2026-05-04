package dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EstadoPartidaDTO implements Serializable {
    private List<CartaDTO> miMano;
    private CartaDTO cartaEnCima;
    private String idJugadorTurno;
    private List<String> historialPartida;
    private boolean partidaTerminada;
    private String ganador;
    private Map<String, Integer> puntajesFinales;
    private int acumulado;
    
    private Map<String, Integer> conteoCartasOponentes;
    private Map<String, String> avataresOponentes;
    private List<JugadorResumenDTO> rivales; 
    private String colorActual; 

    public String getColorActual() { return colorActual; }
    public void setColorActual(String colorActual) { this.colorActual = colorActual; }

    public int getAcumulado() { return acumulado; }
    public void setAcumulado(int acumulado) { this.acumulado = acumulado; }

    public List<CartaDTO> getMiMano() { return miMano; }
    public void setMiMano(List<CartaDTO> miMano) { this.miMano = miMano; }

    public CartaDTO getCartaEnCima() { return cartaEnCima; }
    public void setCartaEnCima(CartaDTO cartaEnCima) { this.cartaEnCima = cartaEnCima; }

    public String getIdJugadorTurno() { return idJugadorTurno; }
    public void setIdJugadorTurno(String idJugadorTurno) { this.idJugadorTurno = idJugadorTurno; }

    public List<String> getHistorialPartida() { return historialPartida; }
    public void setHistorialPartida(List<String> historialPartida) { this.historialPartida = historialPartida; }

    public boolean isPartidaTerminada() { return partidaTerminada; }
    public void setPartidaTerminada(boolean partidaTerminada) { this.partidaTerminada = partidaTerminada; }

    public String getGanador() { return ganador; }
    public void setGanador(String ganador) { this.ganador = ganador; }

    public Map<String, Integer> getPuntajesFinales() { return puntajesFinales; }
    public void setPuntajesFinales(Map<String, Integer> puntajesFinales) { this.puntajesFinales = puntajesFinales; }

    public Map<String, Integer> getConteoCartasOponentes() { return conteoCartasOponentes; }
    public void setConteoCartasOponentes(Map<String, Integer> conteo) { this.conteoCartasOponentes = conteo; }

    public Map<String, String> getAvataresOponentes() { return avataresOponentes; }
    public void setAvataresOponentes(Map<String, String> avatares) { this.avataresOponentes = avatares; }

    public List<JugadorResumenDTO> getRivales() { return rivales; }
    public void setRivales(List<JugadorResumenDTO> rivales) { this.rivales = rivales; }
}