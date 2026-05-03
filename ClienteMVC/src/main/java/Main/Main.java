package Main;

import Controlador.ControladorPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.FrmConfigurarJugador;
import vista.FrmPrincipal;
import vista.FrmTablero;

public class Main {

public static void main(String[] args) {
        FrmPrincipal frmPrincipal = new FrmPrincipal();
        
        // Evento para ir a la pantalla de JoinGame
        frmPrincipal.btnUnirsePartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmConfigurarJugador frmConfig = new FrmConfigurarJugador();
                frmConfig.setVisible(true);
                frmPrincipal.dispose();
            }
        });

        frmPrincipal.setVisible(true);
    }
}