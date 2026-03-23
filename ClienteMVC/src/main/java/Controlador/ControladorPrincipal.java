package Controlador;

import Cliente.ClienteRed;
import dtos.EstadoJuegoDTO;
import vista.FrmTablero;
import javax.swing.SwingUtilities;

public class ControladorPrincipal {

    private ClienteRed cliente;
    private FrmTablero vista;

    public ControladorPrincipal(FrmTablero vista) {
        this.vista = vista;
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
        cliente.enviarAccion("JUGAR:" + indice);
    }

    public void robarCarta() {
        cliente.enviarAccion("ROBAR");
    }
}