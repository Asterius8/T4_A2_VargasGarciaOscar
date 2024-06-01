package vista;
import java.util.List;

import controlador.AlumnoDAO;
import modelo.Alumno;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaBajas extends JFrame {

    private AlumnoDAO alumnoDAO;
    private JTextField txtNumControl;
    private JTable tablaAlumnos;
    public VentanaBajas() {
        alumnoDAO = new AlumnoDAO();

        // Configuraci�n de la ventana
        setTitle("Bajas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));

        // Crear etiquetas y campos de texto
        JLabel lblNumControl = new JLabel("N�mero de Control:");
        txtNumControl = new JTextField(10);

        // Crear bot�n de eliminar
        JButton btnEliminar = new JButton("Eliminar");
        tablaAlumnos = new JTable();

        // Crear panel y agregar componentes
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(8, 2));
        JScrollPane scrollPane = new JScrollPane(tablaAlumnos);

        formPanel.add(lblNumControl);
        formPanel.add(txtNumControl);
        formPanel.add(btnEliminar);
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        cargarDatosAlumnos();
     // Agregar panel a la ventana
        add(panel);
        // Asignar listener al bot�n de eliminar
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numControl = txtNumControl.getText();
                if (alumnoDAO.eliminarAlumno(numControl)) {
                    JOptionPane.showMessageDialog(null, "Alumno eliminado exitosamente");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar alumno");
                }
            }
        });

        // Configurar ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void cargarDatosAlumnos() {
        // Obtener la lista de alumnos de la base de datos
        List<Alumno> alumnos = alumnoDAO.obtenerTodosAlumnos();

        // Crear el modelo de tabla con las columnas adecuadas
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("N�mero de Control");
        model.addColumn("Nombre");
        model.addColumn("Primer Apellido");
        model.addColumn("Segundo Apellido");
        model.addColumn("Edad");
        model.addColumn("Semestre");
        model.addColumn("Carrera");

        // Agregar los datos de los alumnos al modelo de tabla
        for (Alumno alumno : alumnos) {
            Object[] rowData = {
                    alumno.getNumControl(),
                    alumno.getNombre(),
                    alumno.getPrimerAp(),
                    alumno.getSegundoAp(),
                    alumno.getEdad(),
                    alumno.getSemestre(),
                    alumno.getCarrera()
            };
            model.addRow(rowData);
        }

        // Establecer el modelo de tabla en la JTable
        tablaAlumnos.setModel(model);
    }
}
