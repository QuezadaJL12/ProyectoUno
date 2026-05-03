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
    private String avatar;

    public JugadorResumenDTO(String id, String nombre, int cantidadCartas,String avatar) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadCartas = cantidadCartas;
        this.avatar = avatar;
        
        
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    
}
