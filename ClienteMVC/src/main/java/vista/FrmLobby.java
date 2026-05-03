/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author Chino
 */
public class FrmLobby extends JFrame {

    public JButton btnIniciar;
    public JTextArea txtListaJugadores;
    public JLabel lblEstado;

    public FrmLobby() {
        setTitle("UNO - Lobby");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(84, 22, 27));
        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblLogo = new JLabel("UNO!", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Arial", Font.BOLD, 40));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBounds(100, 20, 200, 50);
        add(lblLogo);

        JLabel lblTitulo = new JLabel("Jugadores:");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(50, 90, 200, 20);
        add(lblTitulo);

       
        txtListaJugadores = new JTextArea();
        txtListaJugadores.setBounds(50, 120, 300, 150);
        txtListaJugadores.setBackground(new Color(50, 10, 15)); 
        txtListaJugadores.setForeground(new Color(255, 204, 0)); 
        txtListaJugadores.setFont(new Font("Monospaced", Font.BOLD, 14));
        txtListaJugadores.setEditable(false);
        add(txtListaJugadores);

        lblEstado = new JLabel("Esperando al Host...", SwingConstants.CENTER);
        lblEstado.setForeground(Color.LIGHT_GRAY);
        lblEstado.setBounds(100, 290, 200, 20);
        add(lblEstado);

        btnIniciar = new JButton("Iniciar");
        btnIniciar.setBounds(125, 330, 150, 40);
        btnIniciar.setBackground(new Color(0, 200, 0)); 
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 16));
        btnIniciar.setEnabled(false); 
        add(btnIniciar);
    }
}
