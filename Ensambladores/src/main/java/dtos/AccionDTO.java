/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author Chino
 */
public class AccionDTO implements Serializable {
    public enum TipoAccion { JUGAR_CARTA, ROBAR_CARTA }
    
    public TipoAccion tipo;
    public int indiceCarta; 
    public String idJugador;
    
    public AccionDTO(TipoAccion tipo, int indiceCarta, String idJugador) {
        this.tipo = tipo;
        this.indiceCarta = indiceCarta;
        this.idJugador = idJugador;
    }
}
