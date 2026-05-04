package Cartas;

public class CartaComodin extends Carta {

    public CartaComodin(String id, Color color, TipoCarta tipo, String fotoId) {
        super(id, color, tipo, fotoId);
    }

    @Override
    public boolean esJugable(Carta cima, Color colorActivo) {
        return true;
    }
}