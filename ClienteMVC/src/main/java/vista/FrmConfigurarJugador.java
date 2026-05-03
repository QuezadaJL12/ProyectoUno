package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrmConfigurarJugador extends JFrame {

    public JLabel lblIcono;
    public JButton btnIzq;
    public JButton btnDer;
    public JTextField txtUsername;
    public JButton btnSiguiente;

    public FrmConfigurarJugador() {
        setTitle("UNO - Seleccionar Avatar");
        setSize(400, 420); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(84, 22, 27));

        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        
        JLabel lblTitulo = new JLabel("Seleccionar Avatar", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(200, 150, 50));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(0, 20, 400, 30);
        add(lblTitulo);

        lblIcono = new JLabel("", SwingConstants.CENTER);
        lblIcono.setBounds(135, 70, 130, 130); 
        add(lblIcono);

        btnIzq = new JButton("<");
        btnIzq.setBackground(new Color(200, 150, 50));
        btnIzq.setFont(new Font("Arial", Font.BOLD, 18));
        btnIzq.setFocusPainted(false);
        btnIzq.setBounds(75, 115, 50, 40);
        add(btnIzq);

        btnDer = new JButton(">");
        btnDer.setBackground(new Color(200, 150, 50));
        btnDer.setFont(new Font("Arial", Font.BOLD, 18));
        btnDer.setFocusPainted(false);
        btnDer.setBounds(275, 115, 50, 40);
        add(btnDer);

        JLabel lblUsername = new JLabel("USERNAME:");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsername.setBounds(100, 220, 200, 20);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        txtUsername.setHorizontalAlignment(JTextField.CENTER);
        txtUsername.setBounds(100, 245, 200, 35);
        add(txtUsername);

        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 16));
        btnSiguiente.setBackground(new Color(228, 56, 64));
        btnSiguiente.setForeground(Color.WHITE);
        btnSiguiente.setFocusPainted(false);
        btnSiguiente.setBounds(100, 300, 200, 45);
        add(btnSiguiente);
    }
}