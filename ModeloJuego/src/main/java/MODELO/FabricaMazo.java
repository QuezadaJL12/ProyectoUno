/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import Cartas.CartaNumero;
import Cartas.Carta;
import Cartas.CartaAccion;
import Cartas.CartaComodin;
import Cartas.Color;
import Cartas.TipoCarta;

import java.util.Stack;
import java.util.UUID;

/**
 *
 * @author Chino
 */
public class FabricaMazo {
    public Stack<Carta> crearMazoOficial() {
        Stack<Carta> mazo = new Stack<>();
        Color[] colores = {Color.ROJO, Color.AZUL, Color.VERDE, Color.AMARILLO};

        for (Color c : colores) {
            // 1. Un "0" por color
            mazo.push(new CartaNumero(UUID.randomUUID().toString(), c, 0));

            //  Dos de cada número (1-9) por color
            for (int i = 1; i <= 9; i++) {
                mazo.push(new CartaNumero(UUID.randomUUID().toString(), c, i));
                mazo.push(new CartaNumero(UUID.randomUUID().toString(), c, i));
            }

            //  Cartas de Acción (2 de cada una por color)
            for (int i = 0; i < 2; i++) {
                mazo.push(new CartaAccion(UUID.randomUUID().toString(), c, TipoCarta.SALTO));
                mazo.push(new CartaAccion(UUID.randomUUID().toString(), c, TipoCarta.REVERSA));
                mazo.push(new CartaAccion(UUID.randomUUID().toString(), c, TipoCarta.TOMA_DOS));
            }
        }

        //  Comodines 
        for (int i = 0; i < 4; i++) {
            mazo.push(new CartaComodin(UUID.randomUUID().toString(), Color.ESPECIAL, TipoCarta.CAMBIO_COLOR));
            mazo.push(new CartaComodin(UUID.randomUUID().toString(), Color.ESPECIAL, TipoCarta.TOMA_CUATRO));
        }

        return mazo; // Total: 108 cartas
    }
}
