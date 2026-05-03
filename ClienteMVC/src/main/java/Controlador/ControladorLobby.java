package Controlador;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import red.ClienteHilo;
import vista.FrmConfigurarJugador;
import vista.FrmLobby;

public class ControladorLobby {
    
    private FrmLobby vista;
    private String nombreJugador;
    private String rutaAvatar;
    private String partidaId;
    private boolean esHost;
    private ClienteHilo conexion;

    public ControladorLobby(FrmLobby vista, String nombreJugador, String rutaAvatar, String partidaId, boolean esHost) {
        this.vista = vista;
        this.nombreJugador = nombreJugador;
        this.rutaAvatar = rutaAvatar;
        this.partidaId = partidaId;
        this.esHost = esHost;
        configurarEventos();
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.lblMiNombre.setText(nombreJugador);
        vista.lblTitulo.setText("Sala: " + partidaId);
        
        cargarMiAvatar();

        vista.btnIniciar.setEnabled(esHost);
        if (!esHost) vista.btnIniciar.setToolTipText("Solo el Host puede iniciar la partida");
        
        vista.setVisible(true);
        conectarAlServidor();
    }

    private void conectarAlServidor() {
        conexion = new ClienteHilo("127.0.0.1", 5000, this);
        new Thread(conexion).start();
        conexion.unirseLobby(partidaId, nombreJugador, rutaAvatar);
    }

    public void agregarTextoLobby(String texto) {
        SwingUtilities.invokeLater(() -> {
            vista.txtJugadores.append(texto);
            vista.txtJugadores.setCaretPosition(vista.txtJugadores.getDocument().getLength());
        });
    }

    private void cargarMiAvatar() {
        try {
            String recurso = rutaAvatar.startsWith("/") ? rutaAvatar.substring(1) : rutaAvatar;
            URL url = Thread.currentThread().getContextClassLoader().getResource(recurso);
            
            if (url != null) {
                Image img = new ImageIcon(url).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                vista.lblMiAvatar.setIcon(new ImageIcon(img));
            } else {
                vista.lblMiAvatar.setText("Sin Imagen");
            }
        } catch (Exception e) {
        }
    }

    private void configurarEventos() {
        vista.btnSalir.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(vista, "Salir?", "Lobby", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                new ControladorConfiguracion(new FrmConfigurarJugador()).iniciar();
                vista.dispose();
            }
        });

        vista.btnIniciar.addActionListener(e -> {
            if (esHost) conexion.iniciarPartida(partidaId);
        });
    }
}