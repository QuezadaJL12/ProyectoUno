package Controlador;

import Cliente.ClienteRed;
import dtos.AccionDTO;
import dtos.EstadoJuegoDTO;
import vista.FrmTablero;
import javax.swing.SwingUtilities;


public class ControladorPrincipal {

    private ClienteRed cliente;
    private FrmTablero vista;

    public ControladorPrincipal(FrmTablero vista) {
        this.vista = vista;
        // Se conecta al servidor local
        this.cliente = new ClienteRed("127.0.0.1", 5000, this);
        this.cliente.conectar();
    }

    public void recibirActualizacion(EstadoJuegoDTO estado) {
        SwingUtilities.invokeLater(() -> {
            vista.actualizarMesa(estado.cartaCimaId, estado.colorActual);
            vista.actualizarMano(estado.misCartasIds);
            vista.setTurno(estado.turnoDeNombre, estado.esMiTurno);
        });
    }

 
    public void jugarCarta(int indice) {
     
        AccionDTO jugada = new AccionDTO(AccionDTO.TipoAccion.JUGAR_CARTA, indice, "1");
        cliente.enviarAccion(jugada);
    }

    public void robarCarta() {
     
        AccionDTO robo = new AccionDTO(AccionDTO.TipoAccion.ROBAR_CARTA, -1, "1");
        cliente.enviarAccion(robo);
    }
}