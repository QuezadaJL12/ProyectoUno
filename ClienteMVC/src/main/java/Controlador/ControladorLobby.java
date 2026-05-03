package Controlador;

import Cliente.ClienteRed;
import dtos.EstadoLobbyDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.Timer;
import vista.FrmLobby;
import vista.FrmTablero;

public class ControladorLobby {

    private FrmLobby vista;
    private ClienteRed red;
    private Timer timerActualizador;
    
    private String nombreJugador;
    private String avatarJugador;
    private String idSala;
    private boolean esHost;

    public ControladorLobby(FrmLobby vista, String nombreJugador, String avatarJugador, String idSala, boolean esHost) {
        this.vista = vista;
        this.nombreJugador = nombreJugador;
        this.avatarJugador = avatarJugador;
        this.idSala = idSala;
        this.esHost = esHost;
        
        this.red = new ClienteRed();
        this.red.conectar();
        
        configurarEventos();
    }

    public void iniciar() {
        vista.setLocationRelativeTo(null);
        vista.setTitle("Lobby - Sala: " + idSala);
        
        vista.btnIniciar.setEnabled(false);
        vista.btnIniciar.setVisible(esHost); 
        if (!esHost) {
            vista.lblEstatus.setText("Esperando a que el host inicie...");
        }

        vista.setVisible(true);
        ingresarAlLobby();
        iniciarTimer();
    }

    private void ingresarAlLobby() {
        EstadoLobbyDTO estadoInicial = red.unirseLobby(idSala, nombreJugador, avatarJugador);
        actualizarPantalla(estadoInicial);
    }

    private void iniciarTimer() {
        timerActualizador = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstadoLobbyDTO estado = red.pedirEstadoLobby(idSala);
                actualizarPantalla(estado);
            }
        });
        timerActualizador.start();
    }

    private void configurarEventos() {
        vista.btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                red.pedirIniciarPartida(idSala);
                vista.btnIniciar.setEnabled(false);
                vista.lblEstatus.setText("Iniciando...");
            }
        });
    }

    private void actualizarPantalla(EstadoLobbyDTO estado) {
        if (estado == null) return;

        StringBuilder sb = new StringBuilder();
        
      
        for (Map.Entry<String, String> entry : estado.getJugadores().entrySet()) {
            sb.append(entry.getValue()).append(" ").append(entry.getKey());
            if (entry.getKey().equals(nombreJugador)) {
                sb.append(" (Tú)");
            }
            sb.append(" - Listo\n");
        }
        
        vista.txtListaJugadores.setText(sb.toString());
        vista.lblJugadoresConectados.setText("Conectados: " + estado.getJugadores().size());

        if (esHost && !estado.isJuegoIniciado()) {
            vista.btnIniciar.setEnabled(estado.isPuedeIniciar());
            vista.lblEstatus.setText(estado.isPuedeIniciar() ? "¡Listos para jugar!" : "Esperando más jugadores...");
        }

       
        if (estado.isJuegoIniciado()) {
            timerActualizador.stop(); 
            
            FrmTablero tablero = new FrmTablero();
            ControladorPrincipal controladorJuego = new ControladorPrincipal(tablero, nombreJugador);
            controladorJuego.iniciar();
            
            vista.dispose();
        }
    }
}