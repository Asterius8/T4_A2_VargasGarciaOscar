package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicio extends JFrame {

    public VentanaInicio() {
        // Configuración de la ventana
        setTitle("Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 200));

        // Crear botones
        JButton btnAltas = new JButton("Altas");
        JButton btnBajas = new JButton("Bajas");
        JButton btnCambios = new JButton("Cambios");
        JButton btnConsultas = new JButton("Consultas");

        // Crear panel y agregar componentes
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(btnAltas);
        panel.add(btnBajas);
        panel.add(btnCambios);
        panel.add(btnConsultas);

        // Agregar panel a la ventana
        add(panel);

        // Asignar listener a los botones
        btnAltas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaAltas();
            }
        });

        btnBajas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaBajas();
            }
        });

        btnCambios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaCambios();
            }
        });

        btnConsultas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaConsultas();
            }
        });

        // Configurar ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new VentanaInicio();
    }
}
