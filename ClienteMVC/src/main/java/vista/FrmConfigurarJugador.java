/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import Controlador.ControladorPrincipal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Chino
 */
public class FrmConfigurarJugador extends JFrame {

    private JTextField txtUsername;
    private JButton btnSiguiente;

    public FrmConfigurarJugador() {
        setTitle("UNO - Configurar Jugador");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(84, 22, 27));

        initComponents();
        configurarEventos();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblIcono = new JLabel("👤", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Arial", Font.PLAIN, 60));
        lblIcono.setForeground(Color.WHITE);
        lblIcono.setBounds(150, 30, 100, 80);
        add(lblIcono);

        JLabel lblUsername = new JLabel("USERNAME");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setBounds(100, 130, 200, 20);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(100, 150, 200, 35);
        add(txtUsername);

        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setBounds(100, 210, 200, 40);
        btnSiguiente.setBackground(new Color(228, 56, 64));
        btnSiguiente.setForeground(Color.WHITE);
        add(btnSiguiente);
    }

    private void configurarEventos() {
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtUsername.getText().trim();
                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un nombre de usuario", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                FrmLobby lobby = new FrmLobby();
                lobby.setVisible(true);
                
                lobby.txtListaJugadores.setText(nombre + " \t\t(Tú) - Listo\nEsperando rivales...");
                
                
                
                dispose(); 
            }
        });
    }
}