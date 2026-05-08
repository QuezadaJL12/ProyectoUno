package interfaces;

import dtos.EstadoLobbyDTO;
import dtos.EstadoPartidaDTO;
import dtos.RespuestaLobbyDTO;

public interface ObservadorRed {
    void enActualizacionLobby(EstadoLobbyDTO estado);
    void enActualizacionPartida(EstadoPartidaDTO estado);
    void onError(RespuestaLobbyDTO res);
}