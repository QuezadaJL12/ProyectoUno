package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class FrmLobby extends JFrame {

    public JLabel lblEstatus;
    public JLabel lblJugadoresConectados;
    public JTextArea txtListaJugadores;
    public JButton btnIniciar;

    public FrmLobby() {
        setTitle("UNO - Lobby de Espera");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        // Fondo rojo clásico
        getContentPane().setBackground(new Color(178, 34, 34));

        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Sala de Espera", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 20, 450, 40);
        add(lblTitulo);

        lblJugadoresConectados = new JLabel("Conectados: 0", SwingConstants.CENTER);
        lblJugadoresConectados.setFont(new Font("Arial", Font.BOLD, 16));
        lblJugadoresConectados.setForeground(new Color(255, 215, 0)); // Dorado
        lblJugadoresConectados.setBounds(0, 65, 450, 25);
        add(lblJugadoresConectados);

        txtListaJugadores = new JTextArea();
        txtListaJugadores.setEditable(false);
        txtListaJugadores.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20)); // Soporte para Emojis
        txtListaJugadores.setBackground(new Color(84, 22, 27)); // Rojo oscuro
        txtListaJugadores.setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(txtListaJugadores);
        scrollPane.setBounds(50, 100, 335, 230);
        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(255, 215, 0), 2));
        add(scrollPane);

        lblEstatus = new JLabel("Conectando al servidor...", SwingConstants.CENTER);
        lblEstatus.setFont(new Font("Arial", Font.ITALIC, 14));
        lblEstatus.setForeground(Color.LIGHT_GRAY);
        lblEstatus.setBounds(0, 345, 450, 20);
        add(lblEstatus);

        btnIniciar = new JButton("INICIAR PARTIDA");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 18));
        btnIniciar.setBackground(new Color(255, 215, 0)); // Dorado
        btnIniciar.setForeground(Color.BLACK);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBounds(100, 380, 235, 50);
        
        btnIniciar.setVisible(false); 
        
        add(btnIniciar);
    }
}