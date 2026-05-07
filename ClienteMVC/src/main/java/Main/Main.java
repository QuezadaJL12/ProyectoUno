package Main;

import Controlador.ControladorConfiguracion;
import vista.FrmConfigurarJugador;

public class Main {

    public static void main(String[] args) {
        
        // Creamos la primera pantalla del juego 
        FrmConfigurarJugador vistaConfiguracion = new FrmConfigurarJugador();
        
        // Se la pasamos a su controlador
        ControladorConfiguracion controlador = new ControladorConfiguracion(vistaConfiguracion);
        
        //  Arrancamos el juego
        controlador.iniciar();
        
        
    }
}