package Controlador;

import Cliente.ClienteRed;
import dtos.EstadoPartidaDTO;
import dtos.CartaDTO;
import Cartas.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.FrmTablero;
import javax.swing.Timer;
import javax.swing.JOptionPane;

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

        vista.lblColorActual.setText("Color actual: " + estadoActual.getColorActual());

        if (vista.txtAreaLog != null) {
            StringBuilder sb = new StringBuilder();
            for (String evento : estadoActual.getHistorialPartida()) {
                sb.append(evento).append("\n");
            }
            vista.txtAreaLog.setText(sb.toString());
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
        
        if (estadoActual.isHayGanador()) {
            timerActualizador.stop();
            JOptionPane.showMessageDialog(vista, "¡El juego terminó! Ganó: " + estadoActual.getIdGanador());
        }
    }
}