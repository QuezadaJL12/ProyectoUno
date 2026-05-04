package Cartas;

public class CartaAccion extends Carta {

    public CartaAccion(String id, Color color, TipoCarta tipo, String fotoId) {
        super(id, color, tipo, fotoId);
    }

    @Override
    public boolean esJugable(Carta cima, Color colorActivo) {
        return this.getColor() == colorActivo || this.getTipo() == cima.getTipo();
    }
}