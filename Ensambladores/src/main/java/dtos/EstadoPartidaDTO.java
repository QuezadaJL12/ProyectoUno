package dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EstadoPartidaDTO implements Serializable {
    private List<CartaDTO> miMano;
    private CartaDTO cartaEnCima;
    private String colorActual;
    private String idJugadorTurno;
    private boolean sentidoHorario;
    private List<String> historialPartida;
    private boolean hayGanador;
    private String idGanador;
    private List<JugadorResumenDTO> rivales;

    public EstadoPartidaDTO() {
        this.miMano = new ArrayList<>();
        this.historialPartida = new ArrayList<>();
        this.rivales = new ArrayList<>();
    }

    public List<CartaDTO> getMiMano() { return miMano; }
    public void setMiMano(List<CartaDTO> miMano) { this.miMano = miMano; }
    
    public CartaDTO getCartaEnCima() { return cartaEnCima; }
    public void setCartaEnCima(CartaDTO cartaEnCima) { this.cartaEnCima = cartaEnCima; }
    
    public String getColorActual() { return colorActual; }
    public void setColorActual(String colorActual) { this.colorActual = colorActual; }
    
    public String getIdJugadorTurno() { return idJugadorTurno; }
    public void setIdJugadorTurno(String idJugadorTurno) { this.idJugadorTurno = idJugadorTurno; }
    
    public boolean isSentidoHorario() { return sentidoHorario; }
    public void setSentidoHorario(boolean sentidoHorario) { this.sentidoHorario = sentidoHorario; }
    
    public List<String> getHistorialPartida() { return historialPartida; }
    public void setHistorialPartida(List<String> historialPartida) { this.historialPartida = historialPartida; }
    
    public boolean isHayGanador() { return hayGanador; }
    public void setHayGanador(boolean hayGanador) { this.hayGanador = hayGanador; }
    
    public String getIdGanador() { return idGanador; }
    public void setIdGanador(String idGanador) { this.idGanador = idGanador; }
    
    public List<JugadorResumenDTO> getRivales() { return rivales; }
    public void setRivales(List<JugadorResumenDTO> rivales) { this.rivales = rivales; }
}