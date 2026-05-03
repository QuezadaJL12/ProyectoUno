package vista;

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

public class FrmTablero extends JFrame {

    public JButton btnRobarCarta;
    public JLabel lblCartaMesa;
    public JLabel lblColorActual;
    public JLabel lblTurno;
    public JTextArea txtAreaLog;
    public JPanel pnlMiMano;

    public FrmTablero() {
        configurarVentana();
        initComponents();
    }

    private void configurarVentana() {
        this.setTitle("UNO - Partida en línea");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);
        this.setLayout(new BorderLayout()); 
    }
    
    private void initComponents() {
        JPanel pnlInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        lblTurno = new JLabel("Esperando turno...");
        lblCartaMesa = new JLabel("[Carta en Mesa]");
        lblColorActual = new JLabel("Color: ---");
        btnRobarCarta = new JButton("Robar Carta");
        
        pnlInfo.add(lblTurno);
        pnlInfo.add(lblCartaMesa);
        pnlInfo.add(lblColorActual);
        pnlInfo.add(btnRobarCarta);

        txtAreaLog = new JTextArea();
        txtAreaLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(txtAreaLog);
        
        pnlMiMano = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlMiMano.setPreferredSize(new Dimension(800, 150));

        this.add(pnlInfo, BorderLayout.NORTH);
        this.add(scrollLog, BorderLayout.CENTER);
        this.add(pnlMiMano, BorderLayout.SOUTH);
    }
}