package Controlador;

import Cliente.ClienteRed;
import dtos.EstadoPartidaDTO;
import dtos.CartaDTO;
import dtos.JugadorResumenDTO;
import Cartas.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.FrmTablero;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import vista.FrmGameOver;

public class ControladorPrincipal {

    private FrmTablero vista;
    private ClienteRed red;
    private EstadoPartidaDTO estadoActual;
    private Timer timerActualizador;
    private String idPropio;

    public ControladorPrincipal(FrmTablero vista, String idPropio) {
        this.vista = vista;
        this.idPropio = idPropio;
        this.red = new ClienteRed();
        this.red.conectar();
        configurarEventos();
        iniciarTimerSincronizacion();
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        refrescarEstadoAutomatico();
    }

    private void iniciarTimerSincronizacion() {
        timerActualizador = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrescarEstadoAutomatico();
            }
        });
        timerActualizador.start();
    }

    private void refrescarEstadoAutomatico() {
        EstadoPartidaDTO nuevoEstado = red.pedirEstadoActual();
        if (nuevoEstado != null) {
            estadoActual = nuevoEstado;
            actualizarPantalla();
        }
    }

    private void configurarEventos() {
        vista.btnRobarCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstadoPartidaDTO nuevoEstado = red.pedirRobarCarta();
                if (nuevoEstado != null) {
                    estadoActual = nuevoEstado;
                    actualizarPantalla();
                }
            }
        });

        vista.btnUno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstadoPartidaDTO nuevoEstado = red.pedirCantarUno();
                if (nuevoEstado != null) {
                    estadoActual = nuevoEstado;
                    actualizarPantalla();
                }
            }
        });
    }

    private void actualizarPantalla() {
        if (estadoActual == null) return;

        boolean miTurno = estadoActual.getIdJugadorTurno().equals(idPropio);

        if (vista.lblTurno != null) {
            vista.lblTurno.setText(miTurno ? "¡ES TU TURNO!" : "Turno de: " + estadoActual.getIdJugadorTurno());
        }
        
        if (vista.btnRobarCarta != null) {
            vista.btnRobarCarta.setEnabled(miTurno);
        }

        if (vista.btnUno != null) {
            boolean puedeCantar = miTurno && estadoActual.getMiMano() != null && estadoActual.getMiMano().size() == 2;
            vista.btnUno.setEnabled(puedeCantar);
        }

        vista.lblColorActual.setText("Color actual: " + estadoActual.getColorActual());

        if (vista.txtAreaLog != null && estadoActual.getHistorialPartida() != null) {
            StringBuilder sb = new StringBuilder();
            for (String evento : estadoActual.getHistorialPartida()) {
                sb.append(evento).append("\n");
            }
            vista.txtAreaLog.setText(sb.toString());
        }

        if (vista.pnlRivales != null && estadoActual.getRivales() != null) {
            vista.pnlRivales.removeAll();
            for (JugadorResumenDTO rival : estadoActual.getRivales()) {
                if (rival.getId().equals(idPropio)) continue; 

                JPanel pnlRival = new JPanel(new BorderLayout());
                pnlRival.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel lblAvatar = new JLabel(rival.getAvatar(), SwingConstants.CENTER);
                lblAvatar.setFont(new Font("Arial", Font.PLAIN, 40));

                JLabel lblNombre = new JLabel(rival.getNombre(), SwingConstants.CENTER);
                JLabel lblCartas = new JLabel("Cartas: " + rival.getCantidadCartas(), SwingConstants.CENTER);

                pnlRival.add(lblAvatar, BorderLayout.NORTH);
                pnlRival.add(lblNombre, BorderLayout.CENTER);
                pnlRival.add(lblCartas, BorderLayout.SOUTH);

                vista.pnlRivales.add(pnlRival);
            }
            vista.pnlRivales.revalidate();
            vista.pnlRivales.repaint();
        }

     
        if (estadoActual.getCartaEnCima() != null) {
            String id = estadoActual.getCartaEnCima().getFotoId();
            java.net.URL url = getClass().getResource("/Cartas/" + id + ".png");
            if (url != null) {
                vista.lblCartaMesa.setIcon(new javax.swing.ImageIcon(url));
                vista.lblCartaMesa.setText(""); 
            } else {
                vista.lblCartaMesa.setIcon(null);
                vista.lblCartaMesa.setText(id); 
            }
        }

   
        if (estadoActual.getMiMano() != null) {
            vista.pnlMiMano.removeAll(); 
            for (CartaDTO carta : estadoActual.getMiMano()) {
                String nombreCarta = carta.getFotoId();
                javax.swing.JButton btn = new javax.swing.JButton();
                java.net.URL url = getClass().getResource("/Cartas/" + nombreCarta + ".png");
                
                if (url != null) {
                    btn.setIcon(new javax.swing.ImageIcon(url));
                    btn.setBorderPainted(false);
                    btn.setContentAreaFilled(false);
                } else {
                    btn.setText(nombreCarta);
                }
                
                btn.setEnabled(miTurno);

                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Color colorElegido = null;
                        if (carta.getColor() != null && carta.getColor().toString().equals("ESPECIAL")) {
                            String[] opciones = {"ROJO", "AZUL", "VERDE", "AMARILLO"};
                            int sel = JOptionPane.showOptionDialog(vista, "Elige color", "Comodín",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                            if (sel != -1) colorElegido = Color.valueOf(opciones[sel]);
                            else return;
                        }

                        EstadoPartidaDTO n = red.pedirJugarCarta(carta, colorElegido);
                        if (n != null) {
                            estadoActual = n;
                            actualizarPantalla(); 
                        }
                    }
                });
                vista.pnlMiMano.add(btn);
            }
            vista.pnlMiMano.revalidate();
            vista.pnlMiMano.repaint();
        }
        
       
        if (estadoActual.isHayGanador()) {
            timerActualizador.stop();
            
          
            vista.dispose();
            
            
            FrmGameOver frmGameOver = new FrmGameOver(estadoActual.getPuntuaciones(), estadoActual.getIdGanador());
            frmGameOver.setVisible(true);
        }
    }
}