package Controlador;

import Cliente.ClienteRed;
import dtos.EstadoPartidaDTO;
import dtos.CartaDTO; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.FrmTablero;
import javax.swing.Timer; // Importante para la actualización automática

public class ControladorPrincipal {
    
    private FrmTablero vista;
    private ClienteRed red;
    private EstadoPartidaDTO estadoActual;
    private Timer timerActualizador;

    public ControladorPrincipal(FrmTablero vista) {
        this.vista = vista;
        this.red = new ClienteRed();
        
        // Intentamos conectar al iniciar
        this.red.conectar();
        
        configurarEventos();
        iniciarTimerSincronizacion();
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        
        // Pedimos el estado inicial nada más empezar
        refrescarEstadoAutomatico();
    }

    /**
     * Configura un Timer que pregunta al servidor por cambios cada 1.5 segundos.
     * Esto permite ver las jugadas de otros jugadores.
     */
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
        // Suponiendo que añadiste pedirEstadoActual() a tu ClienteRed
        EstadoPartidaDTO nuevoEstado = red.pedirEstadoActual();
        if (nuevoEstado != null) {
            estadoActual = nuevoEstado;
            actualizarPantalla();
        }
    }

    private void configurarEventos() {
        // Evento para Robar Carta
        vista.btnRobarCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("[CONTROLADOR] Jugador pidió robar carta...");
                EstadoPartidaDTO nuevoEstado = red.pedirRobarCarta();
                
                if (nuevoEstado != null) {
                    estadoActual = nuevoEstado;
                    actualizarPantalla();
                } else {
                    System.err.println("No se pudo obtener el estado del servidor.");
                }
            }
        });
    }

    private void actualizarPantalla() {
        if (estadoActual == null) return;

        System.out.println("--- ACTUALIZANDO TABLERO VISUAL ---");
        
        // Actualizar etiqueta de color y turno
        vista.lblColorActual.setText("Color actual: " + estadoActual.getColorActual());
        
        // Mostrar quién tiene el turno (Opcional si tienes el label)
        // vista.lblTurno.setText("Turno de: " + estadoActual.getIdJugadorTurno());

        // Actualizar carta en la mesa
        if (estadoActual.getCartaEnCima() != null) {
            String nombreFotoCima = estadoActual.getCartaEnCima().getFotoId(); 
            java.net.URL urlImagen = getClass().getResource("/Cartas/" + nombreFotoCima + ".png");
            
            if (urlImagen != null) {
                vista.lblCartaMesa.setIcon(new javax.swing.ImageIcon(urlImagen));
                vista.lblCartaMesa.setText(""); 
            } else {
                vista.lblCartaMesa.setIcon(null);
                vista.lblCartaMesa.setText(nombreFotoCima); 
            }
        }

        // Limpiar y repintar la mano del jugador
        vista.pnlMiMano.removeAll(); 
        
        for (int i = 0; i < estadoActual.getMiMano().size(); i++) {
            CartaDTO carta = estadoActual.getMiMano().get(i);
            String nombreCarta = carta.getFotoId();
            
            javax.swing.JButton btnCarta = new javax.swing.JButton();
            
            java.net.URL urlMano = getClass().getResource("/Cartas/" + nombreCarta + ".png");
            if (urlMano != null) {
                btnCarta.setIcon(new javax.swing.ImageIcon(urlMano));
                btnCarta.setBorderPainted(false); 
                btnCarta.setContentAreaFilled(false);
            } else {
                btnCarta.setText(nombreCarta);
            }
            
            // Evento para Jugar Carta al hacer clic en ella
            btnCarta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("[CONTROLADOR] Intentando jugar la carta: " + nombreCarta);
                    
                    EstadoPartidaDTO nuevoEstado = red.pedirJugarCarta(carta);
                    
                    if (nuevoEstado != null) {
                        estadoActual = nuevoEstado;
                        actualizarPantalla(); 
                    } else {
                        System.err.println("El servidor rechazó la jugada o falló la conexión.");
                    }
                }
            });
            
            vista.pnlMiMano.add(btnCarta);
        }

        vista.pnlMiMano.revalidate();
        vista.pnlMiMano.repaint();
        
        // Verificación de ganador
        if (estadoActual.isHayGanador()) {
            timerActualizador.stop(); // Detenemos el refresco automático
            javax.swing.JOptionPane.showMessageDialog(vista, "¡El juego terminó! Ganó: " + estadoActual.getIdGanador());
        }
    }
}