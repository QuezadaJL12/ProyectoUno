package Cartas;

public class CartaNumero extends Carta {
    private final int valor;

    public CartaNumero(String id, Color color, TipoCarta tipo, String fotoId) {
        super(id, color, tipo, fotoId);
        this.valor = Integer.parseInt(id);
    }

    public int getValor() { return valor; }

    @Override
    public boolean esJugable(Carta cima, Color colorActivo) {
        if (cima instanceof CartaNumero) {
            return this.getColor() == colorActivo || this.valor == ((CartaNumero) cima).getValor();
        }
        return this.getColor() == colorActivo;
    }
}