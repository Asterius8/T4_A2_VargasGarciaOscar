package vista;
import java.util.List;

import controlador.AlumnoDAO;
import modelo.Alumno;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaAltas extends JFrame {

    private AlumnoDAO alumnoDAO;

    private JTextField txtNumControl;
    private JTextField txtNombre;
    private JTextField txtPrimerAp;
    private JTextField txtSegundoAp;
    private JTextField txtEdad;
    private JTextField txtSemestre;
    private JTextField txtCarrera;
    private JTable tablaAlumnos;
    
    public VentanaAltas() {
        alumnoDAO = new AlumnoDAO();

        // Configuraci�n de la ventana
        setTitle("Altas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));

        // Crear etiquetas y campos de texto
        JLabel lblNumControl = new JLabel("N�mero de Control:");
        txtNumControl = new JTextField(10);

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(10);

        JLabel lblPrimerAp = new JLabel("Primer Apellido:");
        txtPrimerAp = new JTextField(10);

        JLabel lblSegundoAp = new JLabel("Segundo Apellido:");
        txtSegundoAp = new JTextField(10);

        JLabel lblEdad = new JLabel("Edad:");
        txtEdad = new JTextField(10);

        JLabel lblSemestre = new JLabel("Semestre:");
        txtSemestre = new JTextField(10);

        JLabel lblCarrera = new JLabel("Carrera:");
        txtCarrera = new JTextField(10);

        // Crear bot�n de guardar
        JButton btnGuardar = new JButton("Guardar");

        tablaAlumnos = new JTable();

        // Crear panel y agregar componentes
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(8, 2));
        JScrollPane scrollPane = new JScrollPane(tablaAlumnos);

        formPanel.add(lblNumControl);
        formPanel.add(txtNumControl);
        formPanel.add(lblNombre);
        formPanel.add(txtNombre);
        formPanel.add(lblPrimerAp);
        formPanel.add(txtPrimerAp);
        formPanel.add(lblSegundoAp);
        formPanel.add(txtSegundoAp);
        formPanel.add(lblEdad);
        formPanel.add(txtEdad);
        formPanel.add(lblSemestre);
        formPanel.add(txtSemestre);
        formPanel.add(lblCarrera);
        formPanel.add(txtCarrera);
        formPanel.add(btnGuardar);
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        cargarDatosAlumnos();
     // Agregar panel a la ventana
        add(panel);

        // Asignar listener al bot�n de guardar
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    String numControl = txtNumControl.getText();
        	    String nombre = txtNombre.getText();
        	    String primerAp = txtPrimerAp.getText();
        	    String segundoAp = txtSegundoAp.getText();
        	    String edadText = txtEdad.getText();
        	    String semestreText = txtSemestre.getText();

        	    byte edad;
        	    byte semestre;

        	    try {
        	        edad = Byte.parseByte(edadText);
        	        semestre = Byte.parseByte(semestreText);
        	    } catch (NumberFormatException ex) {
        	        JOptionPane.showMessageDialog(null, "Por favor, ingresa valores num�ricos v�lidos para Edad y Semestre.");
        	        return; // Salir del m�todo para evitar continuar con la ejecuci�n
        	    }

        	    String carrera = txtCarrera.getText();

        	    // Validar que los campos de nombre, primer apellido y segundo apellido contengan solo letras
        	    if (!nombre.matches("[A-Za-z]+")) {
        	        JOptionPane.showMessageDialog(null, "El campo Nombre solo puede contener letras.");
        	        return; // Salir del m�todo para evitar continuar con la ejecuci�n
        	    }

        	    if (!primerAp.matches("[A-Za-z]+")) {
        	        JOptionPane.showMessageDialog(null, "El campo Primer Apellido solo puede contener letras.");
        	        return; // Salir del m�todo para evitar continuar con la ejecuci�n
        	    }

        	    if (!segundoAp.matches("[A-Za-z]+")) {
        	        JOptionPane.showMessageDialog(null, "El campo Segundo Apellido solo puede contener letras.");
        	        return; // Salir del m�todo para evitar continuar con la ejecuci�n
        	    }

        	    Alumno alumno = new Alumno(numControl, nombre, primerAp, segundoAp, edad, semestre, carrera);
        	    if (alumnoDAO.agregarAlumno(alumno)) {
        	        JOptionPane.showMessageDialog(null, "Alumno agregado exitosamente");
        	        dispose();
        	    } else {
        	        JOptionPane.showMessageDialog(null, "Error al agregar alumno");
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
