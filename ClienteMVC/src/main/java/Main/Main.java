package Main;

import Controlador.ControladorPrincipal;
import vista.FrmTablero;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO CLIENTE UNO ---");

      
        FrmTablero vista = new FrmTablero();

   
        ControladorPrincipal controlador = new ControladorPrincipal(vista);

        controlador.iniciar();
    }
}