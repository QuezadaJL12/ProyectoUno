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
}