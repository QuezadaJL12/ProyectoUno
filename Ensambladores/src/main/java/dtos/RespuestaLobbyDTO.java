/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author santi
 */
public class RespuestaLobbyDTO {
    
    String respuesta;

    public RespuestaLobbyDTO() {
    }

    public RespuestaLobbyDTO(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "RespuestaLobbyDTO{" + "respuesta=" + respuesta + '}';
    }
    
    
}
