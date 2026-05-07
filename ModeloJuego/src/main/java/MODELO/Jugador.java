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
    private String avatar; 
    private List<Carta> mano;
    private boolean dijoUno;

    public Jugador(String id, String nombre, String avatar) {
        this.id = id;
        this.nombre = nombre;
        this.avatar = avatar;
        this.mano = new ArrayList<>();
        this.dijoUno = false;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getAvatar() { return avatar; }
    public List<Carta> getMano() { return mano; }
    public void recibirCarta(Carta carta) { mano.add(carta); }
    public boolean isDijoUno() { return dijoUno; }
    public void setDijoUno(boolean dijoUno) { this.dijoUno = dijoUno; }
}

