package Controlador;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import vista.FrmConfigurarJugador;
import vista.FrmLobby;

public class ControladorConfiguracion {

    private FrmConfigurarJugador vista;
    private String[] rutasAvatares = {
        "avatares/avatar1.png", 
      
        "avatares/avatar3.png",
        "avatares/avatar4.png",
        "avatares/avatar5.png",
        "avatares/avatar6.png"
    };
    private int indiceAvatar = 0;

    public ControladorConfiguracion(FrmConfigurarJugador vista) {
        this.vista = vista;
        configurarEventos(); 
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        actualizarImagen();
        vista.setVisible(true);
    }

    private void actualizarImagen() {
        try {
            String recurso = rutasAvatares[indiceAvatar];
            URL url = Thread.currentThread().getContextClassLoader().getResource(recurso);
            
            if (url == null) {
                url = getClass().getResource("/" + recurso);
            }

            if (url != null) {
                ImageIcon icono = new ImageIcon(url);
                Image img = icono.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                vista.lblIcono.setIcon(new ImageIcon(img));
                vista.lblIcono.setText("");
            } else {
                vista.lblIcono.setText("Sin Imagen");
            }
            vista.lblIcono.repaint();
        } catch (Exception e) {
            System.err.println("Error en actualizarImagen: " + e.getMessage());
        }
    }

    private void configurarEventos() {
        vista.btnIzq.addActionListener(e -> {
            indiceAvatar = (indiceAvatar - 1 + rutasAvatares.length) % rutasAvatares.length;
            actualizarImagen();
        });

        vista.btnDer.addActionListener(e -> {
            indiceAvatar = (indiceAvatar + 1) % rutasAvatares.length;
            actualizarImagen();
        });

        vista.btnSiguiente.addActionListener(e -> {
            String nombre = vista.txtUsername.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingresa un nombre.");
                return;
            }
            
            String avatarElegido = "/" + rutasAvatares[indiceAvatar];
            //  para Jose Luis (Chino) como host
            boolean soyHost = nombre.equalsIgnoreCase("Chino") || nombre.toLowerCase().contains("host");

            FrmLobby vistaLobby = new FrmLobby();
            ControladorLobby ctrl = new ControladorLobby(vistaLobby, nombre, avatarElegido, "partida-1", soyHost);
            ctrl.iniciar();
            vista.dispose();
        });
    }
}