package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FrmInicio extends JFrame {

    public JButton btnCreateGame;

    public FrmInicio() {
        // Configuración básica de la ventana
        setTitle("UNO - Bienvenido");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(178, 34, 34));

        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("UNO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 60));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 60, 400, 60);
        add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Multijugador", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblSubtitulo.setForeground(new Color(255, 215, 0)); // Color dorado/amarillo
        lblSubtitulo.setBounds(0, 115, 400, 30);
        add(lblSubtitulo);

        // --- BOTÓN CREATE GAME ---
        btnCreateGame = new JButton("CREATE GAME");
        btnCreateGame.setFont(new Font("Arial", Font.BOLD, 16));
        btnCreateGame.setBackground(new Color(255, 215, 0)); // Fondo dorado
        btnCreateGame.setForeground(Color.BLACK);
        // Posición: (X, Y, Ancho, Alto)
        btnCreateGame.setBounds(100, 190, 200, 50);
        
        // Le quitamos el borde feo que a veces pone Windows
        btnCreateGame.setFocusPainted(false); 
        
        add(btnCreateGame);
    }
}