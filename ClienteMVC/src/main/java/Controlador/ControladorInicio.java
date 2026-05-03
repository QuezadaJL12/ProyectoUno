package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.FrmInicio;
import vista.FrmConfigurarJugador;

public class ControladorInicio {

    private FrmInicio vista;

    public ControladorInicio(FrmInicio vista) {
        this.vista = vista;
        configurarEventos();
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true); 
    }

    private void configurarEventos() {
        vista.btnCreateGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmConfigurarJugador frmConfig = new FrmConfigurarJugador();
                frmConfig.setVisible(true);
                
                vista.dispose();
            }
        });
    }
}