/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import Cartas.Carta;
import Cartas.Color;

/**
 *
 * @author Chino
 */
public class ValidadorReglas {
    
    public boolean esMovimientoValido(Carta cartaJugada, Carta cartaEnCima, Color colorActual) {
        if (cartaJugada.getColor() == Color.ESPECIAL) {
            return true;
        }
        
        if (cartaJugada.getColor() == colorActual) {
            return true;
        }
        
        if (cartaJugada.getTipo().equals(cartaEnCima.getTipo())) {
            return true;
        }
        
        return false;
    }
}
