package Controlador;

import Cliente.ClienteHilo;
import dtos.CartaDTO;
import dtos.EstadoLobbyDTO;
import dtos.EstadoPartidaDTO;
import dtos.RespuestaLobbyDTO;
import interfaces.ObservadorRed;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import vista.FrmTablero;

public class ControladorTablero implements ObservadorRed {

    private FrmTablero vista;
    private ClienteHilo conexion;
    private String nombreJugador;
    private String partidaId;
    
    private Timer timerTurno;
    private int tiempoRestante;
    private boolean miTurnoActivo = false;

    public ControladorTablero(FrmTablero vista, ClienteHilo conexion, String nombreJugador, String partidaId) {
        this.vista = vista;
        this.conexion = conexion;
        this.nombreJugador = nombreJugador;
        this.partidaId = partidaId;
        configurarCronometro();
        configurarEventos();
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null); 
        vista.setVisible(true);
        conexion.agregarObservador(this);
        conexion.pedirEstadoPartida();
    }
    
    private void configurarCronometro() {
        timerTurno = new Timer(1000, e -> {
            tiempoRestante--;
            if (vista.lblCronometro != null) {
                vista.lblCronometro.setText("Tiempo restante: " + tiempoRestante + "s");
                if (tiempoRestante <= 5) {
                    vista.lblCronometro.setForeground(Color.RED); 
                }
            }

            if (tiempoRestante <= 0) {
                detenerCronometro();
                
                if (miTurnoActivo) {
                    JOptionPane.showMessageDialog(vista, "¡Tiempo agotado! Se robará una carta automáticamente.", "Penalización", JOptionPane.WARNING_MESSAGE);
                    conexion.robarCarta();
                }
            }
        });
    }

    private void iniciarCronometro() {
        detenerCronometro(); 
        tiempoRestante = 20;
        if (vista.lblCronometro != null) {
            vista.lblCronometro.setText("Tiempo restante: 20s");
            vista.lblCronometro.setForeground(Color.WHITE);
            vista.lblCronometro.setVisible(true);
        }
        timerTurno.start();
    }

    private void detenerCronometro() {
        if (timerTurno != null && timerTurno.isRunning()) {
            timerTurno.stop();
        }
        if (vista.lblCronometro != null) {
            vista.lblCronometro.setText("");
        }
    }

    @Override
    public void enActualizacionPartida(EstadoPartidaDTO estado) {
        if (estado.isPartidaTerminada()) {
            detenerCronometro();
            manejarFinPartida(estado);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            if (estado.getCartaEnCima() != null) {
                actualizarCartaCima(estado.getCartaEnCima(), estado.getColorActual());
            }

            miTurnoActivo = false;
            if (estado.getIdJugadorTurno() != null && estado.getIdJugadorTurno().equalsIgnoreCase(nombreJugador)) {
                miTurnoActivo = true;
                if (estado.getAcumulado() > 0) {
                    vista.lblMiTurno.setText("¡RECIBES: +" + estado.getAcumulado() + "!");
                    vista.lblMiTurno.setForeground(Color.ORANGE);
                } else {
                    vista.lblMiTurno.setText("¡TU TURNO!");
                    vista.lblMiTurno.setForeground(Color.GREEN);
                }
            } else {
                vista.lblMiTurno.setText("Turno de: " + estado.getIdJugadorTurno());
                vista.lblMiTurno.setForeground(Color.WHITE);
            }

            iniciarCronometro();

            actualizarPanelOponentes(estado);
            actualizarMiMano(estado, miTurnoActivo);
        });
    }

    private void actualizarCartaCima(CartaDTO carta, String colorActual) {
        String ruta = "/Cartas/" + carta.getFotoId() + ".png";
        URL url = getClass().getResource(ruta);
        if (url != null) {
            Image img = new ImageIcon(url).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            vista.lblCartaActual.setIcon(new ImageIcon(img));
            vista.lblCartaActual.setText("");
        } else {
            vista.lblCartaActual.setText(carta.getTipo());
            vista.lblCartaActual.setIcon(null);
            vista.lblCartaActual.setOpaque(true);
            vista.lblCartaActual.setBackground(obtenerColorHex(carta.getColor()));
        }
        
        if (vista.lblDetalleCentro != null) {
            String textoCentro = "COLOR: " + colorActual + "  |  CARTA: " + carta.getId();
            vista.lblDetalleCentro.setText(textoCentro.toUpperCase());
            Color colorFuente = obtenerColorHex(colorActual);
            if (colorFuente != Color.DARK_GRAY) {
                vista.lblDetalleCentro.setForeground(colorFuente);
            } else {
                vista.lblDetalleCentro.setForeground(Color.WHITE);
            }
        }
    }

    private void actualizarMiMano(EstadoPartidaDTO estado, boolean esMiTurno) {
        vista.pnlMisCartas.removeAll();
        for (CartaDTO carta : estado.getMiMano()) {
            JButton btnCarta = new JButton();
            btnCarta.setPreferredSize(new Dimension(80, 120));
            
            String rutaC = "/Cartas/" + carta.getFotoId() + ".png";
            URL urlC = getClass().getResource(rutaC);
            
            if (urlC != null) {
                Image imgC = new ImageIcon(urlC).getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
                btnCarta.setIcon(new ImageIcon(imgC));
                btnCarta.setBorderPainted(false);
                btnCarta.setContentAreaFilled(false);
            } else {
                btnCarta.setText(carta.getTipo());
                btnCarta.setBackground(obtenerColorHex(carta.getColor()));
            }

            btnCarta.addActionListener(e -> {
                if (esMiTurno) {
                    procesarJugada(carta);
                } else {
                    JOptionPane.showMessageDialog(vista, "Espera tu turno.");
                }
            });
            vista.pnlMisCartas.add(btnCarta);
        }
        vista.pnlMisCartas.revalidate();
        vista.pnlMisCartas.repaint();
    }

    private void procesarJugada(CartaDTO carta) {
        detenerCronometro(); 
        
        String colorAEnviar = carta.getColor();
        if (colorAEnviar != null && colorAEnviar.equalsIgnoreCase("ESPECIAL")) {
            String[] opciones = {"ROJO", "AZUL", "VERDE", "AMARILLO"};
            int seleccion = JOptionPane.showOptionDialog(vista, "Elige un color:", "Cambio de Color",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (seleccion >= 0) colorAEnviar = opciones[seleccion];
            else {
                iniciarCronometro(); 
                return;
            }
        }
        conexion.jugarCarta(carta, colorAEnviar);
    }

    private void actualizarPanelOponentes(EstadoPartidaDTO estado) {
        vista.pnlOponentes.removeAll();
        if (estado.getConteoCartasOponentes() != null) {
            estado.getConteoCartasOponentes().forEach((nombre, cantidad) -> {
                JPanel pnlOponente = new JPanel();
                pnlOponente.setLayout(new BoxLayout(pnlOponente, BoxLayout.Y_AXIS));
                pnlOponente.setOpaque(false);

                JLabel lblAvatar = new JLabel();
                String rutaAvatar = estado.getAvataresOponentes().get(nombre);
                if (rutaAvatar != null && !rutaAvatar.isEmpty()) {
                    URL imgUrl = getClass().getResource(rutaAvatar);
                    if (imgUrl != null) {
                        Image img = new ImageIcon(imgUrl).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                        lblAvatar.setIcon(new ImageIcon(img));
                    }
                }
                lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel lblInfo = new JLabel(nombre + " (" + cantidad + ")");
                lblInfo.setForeground(Color.WHITE);
                lblInfo.setFont(new Font("Arial", Font.BOLD, 12));
                lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

                pnlOponente.add(lblAvatar);
                pnlOponente.add(lblInfo);
                vista.pnlOponentes.add(pnlOponente);
            });
        }
        vista.pnlOponentes.revalidate();
        vista.pnlOponentes.repaint();
    }

    private void manejarFinPartida(EstadoPartidaDTO estado) {
        SwingUtilities.invokeLater(() -> {
            String msg = "¡Ganador: " + estado.getGanador() + "!";
            JOptionPane.showMessageDialog(vista, msg, "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
            vista.dispose();
            System.exit(0);
        });
    }

    private Color obtenerColorHex(String colorStr) {
        if (colorStr == null) return Color.DARK_GRAY;
        switch (colorStr.toUpperCase()) {
            case "ROJO": return new Color(228, 56, 64);
            case "AZUL": return new Color(0, 102, 204);
            case "VERDE": return new Color(40, 167, 69);
            case "AMARILLO": return new Color(255, 193, 7);
            default: return Color.DARK_GRAY;
        }
    }

    private void configurarEventos() {
        vista.btnSalir.addActionListener(e -> {
            detenerCronometro();
            System.exit(0);
        });
        
        vista.btnMazo.addActionListener(e -> {
            if(miTurnoActivo) detenerCronometro(); 
            conexion.robarCarta();
        });
        
        vista.btnUno.addActionListener(e -> {
            conexion.cantarUno();
            JOptionPane.showMessageDialog(vista, "¡Cantaste UNO!");
        });
    }

    @Override public void enActualizacionLobby(EstadoLobbyDTO estado) {}

    @Override
    public void onError(RespuestaLobbyDTO res) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}