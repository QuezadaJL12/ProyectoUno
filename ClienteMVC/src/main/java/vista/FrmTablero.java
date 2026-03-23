package vista;

import Controlador.ControladorPrincipal;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FrmTablero extends JFrame {

    private ControladorPrincipal controlador;
    private JLabel lblCartaCima, lblTurno, lblColorActual;
    private JPanel panelMano;
    private JButton btnRobar;

    public FrmTablero() {
        setTitle("UNO Online - Chino");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initUI();
        this.controlador = new ControladorPrincipal(this);
    }

    private void initUI() {
        JPanel pNorte = new JPanel(new BorderLayout());
        pNorte.setBorder(new EmptyBorder(10, 20, 10, 20));
        lblTurno = new JLabel("Esperando...", JLabel.LEFT);
        lblColorActual = new JLabel("Color: -", JLabel.RIGHT);
        pNorte.add(lblTurno, BorderLayout.WEST);
        pNorte.add(lblColorActual, BorderLayout.EAST);
        add(pNorte, BorderLayout.NORTH);

        lblCartaCima = new JLabel();
        lblCartaCima.setHorizontalAlignment(JLabel.CENTER);
        lblCartaCima.setBorder(BorderFactory.createTitledBorder("Mesa"));
        add(lblCartaCima, BorderLayout.CENTER);

        JPanel pSur = new JPanel(new BorderLayout());
        btnRobar = new JButton("Robar Carta");
        btnRobar.addActionListener(e -> controlador.robarCarta());
        pSur.add(btnRobar, BorderLayout.NORTH);

        panelMano = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelMano.setBackground(new Color(34, 139, 34));
        pSur.add(new JScrollPane(panelMano), BorderLayout.CENTER);
        add(pSur, BorderLayout.SOUTH);
    }

  public void actualizarMesa(String id, String col) {
    String path = "/Cartas/" + id + ".png";
    java.net.URL imgURL = getClass().getResource(path);
    
    if (imgURL != null) {
        lblCartaCima.setIcon(new ImageIcon(imgURL));
        lblCartaCima.setText(""); // Borra el texto si hay imagen
    } else {
        // Esto te dirá en la consola exactamente qué nombre está fallando
        System.err.println("ERROR: No se encontró el archivo: " + path);
        lblCartaCima.setIcon(null);
        lblCartaCima.setText("Falta: " + id); 
    }
    lblColorActual.setText("Color: " + col);
}

public void actualizarMano(List<String> ids) {
    panelMano.removeAll();
    for (int i = 0; i < ids.size(); i++) {
        final int idx = i;
        String idCarta = ids.get(i);
        String path = "/Cartas/" + idCarta + ".png";
        java.net.URL imgURL = getClass().getResource(path);
        
        JButton b = new JButton();
        if (imgURL != null) {
            b.setIcon(new ImageIcon(imgURL));
        } else {
            b.setText(idCarta);
            System.err.println("ERROR: No se encontró en mano: " + path);
        }
        
        b.addActionListener(e -> controlador.jugarCarta(idx));
        panelMano.add(b);
    }
    panelMano.revalidate();
    panelMano.repaint();
}

    public void setTurno(String n, boolean m) {
        lblTurno.setText(m ? "TU TURNO" : "Turno de: " + n);
        btnRobar.setEnabled(m);
    }
}