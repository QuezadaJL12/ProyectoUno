package vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.FlowLayout;

public class FrmTablero extends JFrame {

    // 1. Declaramos
    public JButton btnRobarCarta;
    public JLabel lblCartaMesa;
    public JLabel lblColorActual;
    public JPanel pnlMiMano;

    public FrmTablero() {
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        this.setTitle("UNO - Partida en línea");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);
    }
    
    private void initComponents() {
        // 2. Le damos Layout (para que no se amontonen)
        this.setLayout(new FlowLayout());

        // 3. ¡Los instanciamos para que nazcan!
        btnRobarCarta = new JButton("Robar Carta");
        lblCartaMesa = new JLabel("[Carta en Mesa]");
        lblColorActual = new JLabel("Color: ---");
        pnlMiMano = new JPanel();

        // 4. Los agregamos a la ventana
        this.add(lblCartaMesa);
        this.add(lblColorActual);
        this.add(btnRobarCarta);
        this.add(pnlMiMano);
    }
}