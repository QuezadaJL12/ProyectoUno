package Controlador;

import Cliente.ClienteHilo;
import dtos.EstadoLobbyDTO;
import dtos.EstadoPartidaDTO;
import interfaces.ObservadorRed;
import java.awt.Image;
import java.net.URL;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import vista.FrmConfigurarJugador;
import vista.FrmLobby;
import vista.FrmTablero; 

public class ControladorLobby implements ObservadorRed {
    
    private FrmLobby vista;
    private String nombreJugador;
    private String rutaAvatar;
    private String partidaId;
    private boolean esHost; 
    private ClienteHilo conexion;
    
    private Timer timerInicio;
    private int tiempoRestante;

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
        
        vista.btnIniciar.setEnabled(false); 
        vista.setVisible(true);
        conectarAlServidor();
    }

    private void conectarAlServidor() {
        conexion = new ClienteHilo("127.0.0.1", 5000);
        conexion.agregarObservador(this); 
        conexion.unirseLobby(partidaId, nombreJugador, rutaAvatar);
        new Thread(conexion).start();
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
                detenerCronometroLobby();
                new ControladorConfiguracion(new FrmConfigurarJugador()).iniciar();
                vista.dispose();
            }
        });

        vista.btnIniciar.addActionListener(e -> {
            if (conexion != null && esHost) {
                conexion.iniciarPartida(partidaId);
            }
        });
    }
    
    private void iniciarCronometroLobby() {
        tiempoRestante = 30;
        vista.btnIniciar.setEnabled(false); 
        
        timerInicio = new Timer(1000, e -> {
            tiempoRestante--;
            vista.btnIniciar.setText("Iniciando en " + tiempoRestante + "s...");
            
            if (tiempoRestante <= 0) {
                detenerCronometroLobby();
                
                if (esHost && conexion != null) {
                    conexion.iniciarPartida(partidaId);
                }
            }
        });
        timerInicio.start();
        vista.btnIniciar.setText("Iniciando en 30s...");
    }

    private void detenerCronometroLobby() {
        if (timerInicio != null && timerInicio.isRunning()) {
            timerInicio.stop();
        }
    }

    @Override
    public void enActualizacionLobby(EstadoLobbyDTO estado) {
        SwingUtilities.invokeLater(() -> {
            
            if (estado.isJuegoIniciado()) {
                detenerCronometroLobby();
                FrmTablero frmTablero = new FrmTablero();
                ControladorTablero ctrlTablero = new ControladorTablero(frmTablero, conexion, nombreJugador, partidaId);
                ctrlTablero.iniciar();
                vista.dispose();
                return;
            }

            vista.txtJugadores.setText(""); 
            boolean esElPrimero = true;
            
            for (Map.Entry<String, String> entry : estado.getJugadores().entrySet()) {
                if (esElPrimero) {
                    vista.txtJugadores.append("[HOST] " + entry.getKey() + "\n");
                    esHost = entry.getKey().equals(nombreJugador);
                    esElPrimero = false; 
                } else {
                    vista.txtJugadores.append("[JUGADOR] " + entry.getKey() + "\n");
                }
            }
            
            int cantidadJugadores = estado.getJugadores().size();
            
            if (cantidadJugadores == 4) {
                if (timerInicio == null || !timerInicio.isRunning()) {
                    iniciarCronometroLobby();
                }
            } else {
                if (timerInicio != null && timerInicio.isRunning()) {
                    detenerCronometroLobby();
                }
                vista.btnIniciar.setText("Iniciar Partida");
                vista.btnIniciar.setEnabled(esHost); // Solo habilitado para el Host
            }
        });
    }

    @Override
    public void enActualizacionPartida(EstadoPartidaDTO estado) {
  
    }
}