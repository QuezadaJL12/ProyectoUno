package Main;

import Controlador.ControladorConfiguracion;
import Controlador.ControladorInicio;
import Controlador.ControladorPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.FrmConfigurarJugador;
import vista.FrmInicio;
import vista.FrmPrincipal;
import vista.FrmTablero;

public class Main {


     public static void main(String[] args) {
       FrmConfigurarJugador vista = new FrmConfigurarJugador();
    ControladorConfiguracion controlador = new ControladorConfiguracion(vista);
    controlador.iniciar();
}
}
