/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author Chino
 */
public class JugadorResumenDTO implements Serializable {
    private String id;
    private String nombre;
    private int cantidadCartas;

    public JugadorResumenDTO(String id, String nombre, int cantidadCartas) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadCartas = cantidadCartas;
        
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadCartas() {
        return cantidadCartas;
    }

    public void setCantidadCartas(int cantidadCartas) {
        this.cantidadCartas = cantidadCartas;
    }
}
