package interfaces;

import dtos.EstadoLobbyDTO;
import dtos.EstadoPartidaDTO;

public interface ObservadorRed {
    void enActualizacionLobby(EstadoLobbyDTO estado);
    void enActualizacionPartida(EstadoPartidaDTO estado);
}