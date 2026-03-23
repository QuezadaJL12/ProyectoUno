/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cartas;

import Cartas.Carta;


/**
 *
 * @author Chino
 */
public class CartaNumero extends Carta {
    private final int valor;

    public CartaNumero(String id, Color color, int valor) {
        super(id, color, TipoCarta.NUMERO);
        this.valor = valor;
    }

    public int getValor() { return valor; }

    @Override
    public boolean esJugable(Carta cima, Color colorActivo) {
        // Regla: Mismo color o mismo número
        if (cima instanceof CartaNumero) {
            return this.getColor() == colorActivo || this.valor == ((CartaNumero) cima).getValor();
        }
        return this.getColor() == colorActivo;
    }
}