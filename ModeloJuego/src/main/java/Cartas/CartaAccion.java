/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cartas;

/**
 *
 * @author Chino
 */
public class CartaAccion extends Carta {

    public CartaAccion(String id, Color color, TipoCarta tipo) {
        super(id, color, tipo);
    }

    @Override
    public boolean esJugable(Carta cima, Color colorActivo) {
  
        return this.getColor() == colorActivo || this.getTipo() == cima.getTipo();
    }

    
   
    @Override
    public String getFotoId() {
        switch (this.getTipo()) {
            case SALTO: 
                return "Carta_Salto";
            case REVERSA: 
                return "Carta_Reversa";
            case TOMA_DOS: 
                return "Carta_MasDos";
            case BLOQUEO: 
                return "Carta_Bloqueo";
            default: 
                return "Error_Carta";
        }
    }
}