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
public class CartaDTO implements Serializable {
    private String color;
    private String valorOTipo;
    private String fotoId; 

    public CartaDTO(String color, String valorOTipo, String fotoId) {
        this.color = color;
        this.valorOTipo = valorOTipo;
        this.fotoId = fotoId;
        
        
    
}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValorOTipo() {
        return valorOTipo;
    }

    public void setValorOTipo(String valorOTipo) {
        this.valorOTipo = valorOTipo;
    }

    public String getFotoId() {
        return fotoId;
    }

    public void setFotoId(String fotoId) {
        this.fotoId = fotoId;
    }
}
