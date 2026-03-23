/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import javax.swing.SwingUtilities;
import vista.FrmTablero;

/**
 *
 * @author Chino
 */
public class Main {
    public static void main(String[] args) {
      
        SwingUtilities.invokeLater(() -> {
            FrmTablero tablero = new FrmTablero();
            tablero.setVisible(true);
        });
    }
}
