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
    
    public JLabel lblTitulo;
    public JLabel lblMiAvatar;
    public JLabel lblMiNombre;
    public JTextArea txtJugadores;
    public JButton btnIniciar;
    public JButton btnSalir;

    public FrmLobby() {
        setTitle("UNO - Lobby de Espera");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(84, 22, 27)); 
        
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        lblTitulo = new JLabel("Sala de Espera", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(200, 150, 50));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(0, 20, 500, 30);
        add(lblTitulo);

        lblMiAvatar = new JLabel("", SwingConstants.CENTER);
        lblMiAvatar.setBounds(30, 80, 100, 100);
        add(lblMiAvatar);

        lblMiNombre = new JLabel("Nombre", SwingConstants.CENTER);
        lblMiNombre.setForeground(Color.WHITE);
        lblMiNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblMiNombre.setBounds(30, 190, 100, 20);
        add(lblMiNombre);

        JLabel lblLista = new JLabel("Jugadores Conectados:");
        lblLista.setForeground(Color.WHITE);
        lblLista.setFont(new Font("Arial", Font.BOLD, 14));
        lblLista.setBounds(160, 60, 200, 20);
        add(lblLista);

        txtJugadores = new JTextArea();
        txtJugadores.setEditable(false);
        txtJugadores.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(txtJugadores);
        scroll.setBounds(160, 80, 300, 200);
        add(scroll);

        btnIniciar = new JButton("Iniciar Partida");
        btnIniciar.setBackground(new Color(40, 167, 69)); // Verde
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBounds(160, 300, 140, 40);
        add(btnIniciar);

        btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(228, 56, 64)); // Rojo
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setFocusPainted(false);
        btnSalir.setBounds(320, 300, 140, 40);
        add(btnSalir);
    }
}