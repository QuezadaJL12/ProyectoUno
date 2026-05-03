package vista;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

public class FrmTablero extends JFrame {

    public JButton btnRobarCarta;
    public JButton btnUno;
    public JLabel lblCartaMesa;
    public JLabel lblColorActual;
    public JLabel lblTurno;
    public JTextArea txtAreaLog;
    public JPanel pnlMiMano;
    public JPanel pnlRivales;

    public FrmTablero() {
        configurarVentana();
        initComponents();
    }

    private void configurarVentana() {
        this.setTitle("UNO - Partida en línea");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(950, 600); 
        this.setResizable(false);
        this.setLayout(new BorderLayout()); 
    }
    
    private void initComponents() {
        JPanel pnlInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        lblTurno = new JLabel("Esperando turno...");
        lblCartaMesa = new JLabel("[Carta en Mesa]");
        lblColorActual = new JLabel("Color: ---");
        
        btnRobarCarta = new JButton("Robar Carta");
        
        btnUno = new JButton("¡UNO!");
        btnUno.setBackground(new Color(228, 56, 64)); 
        btnUno.setForeground(Color.WHITE);
        btnUno.setFont(new Font("Arial", Font.BOLD, 14));

        pnlInfo.add(lblTurno);
        pnlInfo.add(lblCartaMesa);
        pnlInfo.add(lblColorActual);
        pnlInfo.add(btnRobarCarta);
        pnlInfo.add(btnUno);

        txtAreaLog = new JTextArea();
        txtAreaLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(txtAreaLog);
        
        pnlRivales = new JPanel();
        pnlRivales.setLayout(new BoxLayout(pnlRivales, BoxLayout.Y_AXIS));
        pnlRivales.setPreferredSize(new Dimension(150, 0));
        pnlRivales.setBorder(BorderFactory.createTitledBorder("Mesa (Rivales)"));

        pnlMiMano = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlMiMano.setPreferredSize(new Dimension(800, 150));

        this.add(pnlInfo, BorderLayout.NORTH);
        this.add(pnlRivales, BorderLayout.WEST); 
        this.add(scrollLog, BorderLayout.CENTER);
        this.add(pnlMiMano, BorderLayout.SOUTH);
    }
}