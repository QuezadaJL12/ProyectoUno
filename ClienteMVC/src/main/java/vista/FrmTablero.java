package vista;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class FrmTablero extends JFrame {

    public JPanel pnlMisCartas;
    public JPanel pnlCentro;
    public JPanel pnlOponentes;

    public JLabel lblCartaActual; 
    public JButton btnMazo;       
    public JLabel lblDetalleCentro;

    public JLabel lblMiTurno;
    public JLabel lblCronometro; 
    public JButton btnUno; 
    public JButton btnSalir;

    public FrmTablero() {
        setTitle("UNO - Partida en curso");
        setSize(1000, 700); 
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(84, 22, 27)); 

        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        pnlOponentes = new JPanel();
        pnlOponentes.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        pnlOponentes.setBounds(0, 0, 1000, 150);
        pnlOponentes.setOpaque(false);
        add(pnlOponentes);

        pnlCentro = new JPanel();
        pnlCentro.setLayout(null);
        pnlCentro.setBounds(300, 180, 400, 250);
        pnlCentro.setOpaque(false);
        
        lblDetalleCentro = new JLabel("", SwingConstants.CENTER);
        lblDetalleCentro.setFont(new Font("Arial", Font.BOLD, 16));
        lblDetalleCentro.setForeground(Color.WHITE);
        lblDetalleCentro.setBounds(0, 0, 400, 20);
        pnlCentro.add(lblDetalleCentro);

        lblCartaActual = new JLabel("CARTA MESA", SwingConstants.CENTER);
        lblCartaActual.setOpaque(true);
        lblCartaActual.setBackground(Color.WHITE);
        lblCartaActual.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        lblCartaActual.setBounds(210, 25, 130, 200); 
        pnlCentro.add(lblCartaActual);

        btnMazo = new JButton("ROBAR");
        btnMazo.setBackground(Color.DARK_GRAY);
        btnMazo.setForeground(Color.WHITE);
        btnMazo.setFont(new Font("Arial", Font.BOLD, 16));
        btnMazo.setBounds(60, 25, 130, 200); 
        pnlCentro.add(btnMazo);

        add(pnlCentro);

        lblCronometro = new JLabel("", SwingConstants.CENTER);
        lblCronometro.setForeground(Color.WHITE);
        lblCronometro.setFont(new Font("Arial", Font.BOLD, 22));
        lblCronometro.setBounds(0, 400, 1000, 30);
        add(lblCronometro);

        lblMiTurno = new JLabel("Es el turno de: Esperando...", SwingConstants.CENTER);
        lblMiTurno.setForeground(Color.YELLOW);
        lblMiTurno.setFont(new Font("Arial", Font.BOLD, 18));
        lblMiTurno.setBounds(0, 440, 1000, 30);
        add(lblMiTurno);

        pnlMisCartas = new JPanel();
        pnlMisCartas.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlMisCartas.setBackground(new Color(60, 15, 20));

        JScrollPane scrollCartas = new JScrollPane(pnlMisCartas);
        scrollCartas.setBounds(50, 480, 750, 160);
        scrollCartas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollCartas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(scrollCartas);

        btnUno = new JButton("¡UNO!");
        btnUno.setBackground(new Color(228, 56, 64)); 
        btnUno.setForeground(Color.WHITE);
        btnUno.setFont(new Font("Arial", Font.BOLD, 20));
        btnUno.setBounds(820, 480, 120, 70);
        add(btnUno);

        btnSalir = new JButton("Salir");
        btnSalir.setBackground(Color.GRAY);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setBounds(820, 570, 120, 50);
        add(btnSalir);
    }
}