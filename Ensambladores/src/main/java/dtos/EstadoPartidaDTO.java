package dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Chino
 */
public class EstadoPartidaDTO implements Serializable {
    private CartaDTO cartaEnCima;
    private String colorActual;
    private String idJugadorEnTurno;
    private boolean sentidoHorario;
    private boolean hayGanador;
    private String idGanador;
    private List<JugadorResumenDTO> rivales;
    private List<CartaDTO> miMano;

    public EstadoPartidaDTO() {
    }

    public EstadoPartidaDTO(CartaDTO cartaEnCima, String colorActual, String idJugadorEnTurno, boolean sentidoHorario, boolean hayGanador, String idGanador, List<JugadorResumenDTO> rivales, List<CartaDTO> miMano) {
        this.cartaEnCima = cartaEnCima;
        this.colorActual = colorActual;
        this.idJugadorEnTurno = idJugadorEnTurno;
        this.sentidoHorario = sentidoHorario;
        this.hayGanador = hayGanador;
        this.idGanador = idGanador;
        this.rivales = rivales;
        this.miMano = miMano;
    }

    public CartaDTO getCartaEnCima() {
        return cartaEnCima;
    }

    public void setCartaEnCima(CartaDTO cartaEnCima) {
        this.cartaEnCima = cartaEnCima;
    }

    public String getColorActual() {
        return colorActual;
    }

    public void setColorActual(String colorActual) {
        this.colorActual = colorActual;
    }

    public String getIdJugadorEnTurno() {
        return idJugadorEnTurno;
    }

    public void setIdJugadorEnTurno(String idJugadorEnTurno) {
        this.idJugadorEnTurno = idJugadorEnTurno;
    }

    public boolean isSentidoHorario() {
        return sentidoHorario;
    }

    public void setSentidoHorario(boolean sentidoHorario) {
        this.sentidoHorario = sentidoHorario;
    }

    public boolean isHayGanador() {
        return hayGanador;
    }

    public void setHayGanador(boolean hayGanador) {
        this.hayGanador = hayGanador;
    }

    public String getIdGanador() {
        return idGanador;
    }

    public void setIdGanador(String idGanador) {
        this.idGanador = idGanador;
    }

    
    public List<JugadorResumenDTO> getRivales() {
        return rivales;
    }

    public void setRivales(List<JugadorResumenDTO> rivales) {
        this.rivales = rivales;
    }
    public List<CartaDTO> getMiMano() {
        return miMano;
    }

    public void setMiMano(List<CartaDTO> miMano) {
        this.miMano = miMano;
    }
}