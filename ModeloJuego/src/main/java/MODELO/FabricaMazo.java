package MODELO;

import Cartas.Carta;
import Cartas.Color;
import Cartas.TipoCarta;
import Cartas.CartaNumero;
import Cartas.CartaAccion;
import Cartas.CartaComodin;
import java.util.Stack;

public class FabricaMazo {

    public Stack<Carta> crearMazoOficial() {
        Stack<Carta> mazo = new Stack<>();
        Color[] colores = {Color.ROJO, Color.AZUL, Color.VERDE, Color.AMARILLO};

        for (Color c : colores) {
            String cStr = capitalizar(c.name());
            
            for (int i = 0; i <= 9; i++) {
                String fotoId = i + "_" + cStr; 
                mazo.push(new CartaNumero(String.valueOf(i), c, TipoCarta.NUMERO, fotoId));
                if (i != 0) {
                    mazo.push(new CartaNumero(String.valueOf(i), c, TipoCarta.NUMERO, fotoId));
                }
            }

            mazo.push(new CartaAccion("Salto", c, TipoCarta.SALTO, "Carta_Salto"));
            mazo.push(new CartaAccion("Reversa", c, TipoCarta.REVERSA, "Carta_Reversa"));
            mazo.push(new CartaAccion("MasDos", c, TipoCarta.TOMA_DOS, "Carta_MasDos"));
        }

        for (int i = 0; i < 4; i++) {
            mazo.push(new CartaComodin("Comodin", Color.ESPECIAL, TipoCarta.COMODIN, "Carta_Comodin"));
            mazo.push(new CartaComodin("MasCuatro", Color.ESPECIAL, TipoCarta.TOMA_CUATRO, "Carta_MasCuatro"));
        }

        return mazo;
    }

    private String capitalizar(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}