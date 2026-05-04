/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cartas;



/**
 *
 * @author Chino
 */
public abstract class Carta {
    private final String id;
    private final Color color; 
    private final TipoCarta tipo;
    private final String fotoId; 

    public Carta(String id, Color color, TipoCarta tipo, String fotoId) {
        this.id = id;
        this.color = color;
        this.tipo = tipo;
        this.fotoId = fotoId;
    }

    public Color getColor() { return color; }
    public TipoCarta getTipo() { return tipo; }
    public String getId() { return id; }
    public String getFotoId() { return fotoId; } // Ahora es un getter normal

    public abstract boolean esJugable(Carta cimaPila, Color colorActivo);
}
