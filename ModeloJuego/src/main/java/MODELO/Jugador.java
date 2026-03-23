/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import Cartas.Carta;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Chino
 */
public class Jugador {
    private String id;
    private String nombre;
    private List<Carta> mano;
    private boolean gritoUno;

    public Jugador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.gritoUno = false;
    }

    public void recibirCarta(Carta carta) { this.mano.add(carta); }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Carta> getMano() { return mano; }
    public void setGritoUno(boolean b) { this.gritoUno = b; }
    public boolean haGritadoUno() { return gritoUno; }
}

