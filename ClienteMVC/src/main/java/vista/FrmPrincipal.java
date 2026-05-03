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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Chino
 */
public class FrmPrincipal extends JFrame {

    public JButton btnCrearPartida;
    public JButton btnUnirsePartida;

    public FrmPrincipal() {
        setTitle("UNO - Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(84, 22, 27)); 

        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblLogo = new JLabel("UNO!", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Arial", Font.BOLD, 48));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setBounds(100, 40, 200, 60);
        add(lblLogo);

        btnCrearPartida = new JButton("CREATE GAME");
        btnCrearPartida.setBounds(100, 130, 200, 40);
        btnCrearPartida.setBackground(new Color(228, 56, 64));
        btnCrearPartida.setForeground(Color.WHITE);
        add(btnCrearPartida);

        btnUnirsePartida = new JButton("JOIN GAME");
        btnUnirsePartida.setBounds(100, 190, 200, 40);
        btnUnirsePartida.setBackground(new Color(50, 50, 50));
        btnUnirsePartida.setForeground(Color.WHITE);
        add(btnUnirsePartida);
    }
}