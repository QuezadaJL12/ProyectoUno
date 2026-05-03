package vista;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FrmGameOver extends JFrame {

    private Map<String, Integer> puntuaciones;
    private String idGanador;

    public FrmGameOver(Map<String, Integer> puntuaciones, String idGanador) {
        this.puntuaciones = puntuaciones;
        this.idGanador = idGanador;
        
        setTitle("UNO - GAME OVER");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(60, 10, 15)); // Un fondo oscuro

        initComponents();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("GAME OVER", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(255, 60, 60)); // Texto rojo
        lblTitulo.setBounds(0, 30, 350, 40);
        add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Scores", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblSubtitulo.setForeground(new Color(200, 150, 50)); // Dorado/Amarillo
        lblSubtitulo.setBounds(0, 80, 350, 30);
        add(lblSubtitulo);

        // Ordenamos las puntuaciones de menor (mejor) a mayor (peor)
        List<Map.Entry<String, Integer>> listaPuntos = new ArrayList<>(puntuaciones.entrySet());
        listaPuntos.sort(Map.Entry.comparingByValue());

        int y = 140;
        int posicion = 1;
        for (Map.Entry<String, Integer> entrada : listaPuntos) {
            String texto = posicion + ".- " + entrada.getKey() + " -- " + entrada.getValue() + " pts";
            
            JLabel lblJugador = new JLabel(texto, SwingConstants.CENTER);
            lblJugador.setFont(new Font("Arial", Font.BOLD, 20));
            
            if (entrada.getKey().equals(idGanador)) {
                lblJugador.setForeground(Color.GREEN); // Resaltamos al ganador
            } else {
                lblJugador.setForeground(new Color(200, 150, 50)); // Dorado
            }
            
            lblJugador.setBounds(0, y, 350, 30);
            add(lblJugador);
            
            y += 40;
            posicion++;
        }
    }
}