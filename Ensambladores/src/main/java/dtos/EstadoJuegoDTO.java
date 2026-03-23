/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Chino
 */
public class EstadoJuegoDTO implements Serializable {
    public String idPartida;
    public String cartaCimaId;
    public String colorActual;
    public List<String> misCartasIds;
    public int cantidadCartasMazo;
    public String turnoDeNombre;
    public boolean esMiTurno;
    public boolean sentidoHorario;
    public List<InfoOponenteDTO> oponentes;
}
