package vista;

import controlador.AlumnoDAO;
import modelo.Alumno;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VentanaConsultas extends JFrame {

    private AlumnoDAO alumnoDAO;
    private JTextArea txtResultados;
    private JComboBox<String> comboCarrera;
    private JTextField txtNumRegistro;
    private int numRegistroActual;
    private JTextArea txtNumControl;
    private JTextArea txtNombre;
    private JTextArea txtPrimerAp;
    private JTextArea txtSegundoAp;
    private JTextArea txtEdad;
    private JTextArea txtSemestre;
    private JTextArea txtCarrera;
    private JTable tablaAlumnos;
   private JCheckBox par;
   private JCheckBox todos;
    private List<Alumno> alumnos;

    public VentanaConsultas() {
        alumnoDAO = new AlumnoDAO();
        alumnos = alumnoDAO.obtenerTodosAlumnos();
        numRegistroActual = 0;
        
        // Configuraci�n de la ventana
        setTitle("Consultas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));

        // Crear etiquetas y componentes
        JLabel lblCarrera = new JLabel("Carrera:");
        comboCarrera = new JComboBox<>();
        comboCarrera.addItem("Todas las carreras");
        cargarCarreras();

        JLabel lblConsulta = new JLabel("Consulta:");
        txtResultados = new JTextArea(2, 10);
        txtResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResultados);

        JLabel lblNumRegistro = new JLabel("Registro:");
        txtNumRegistro = new JTextField(5);
        txtNumRegistro.setEditable(false);
        
     // Crear etiquetas y campos de texto
        JLabel lblNumControl = new JLabel("N�mero de Control:");
        txtNumControl = new JTextArea(2,5);

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextArea(2,5);

        JLabel lblPrimerAp = new JLabel("Primer Apellido:");
        txtPrimerAp = new JTextArea(2,5);

        JLabel lblSegundoAp = new JLabel("Segundo Apellido:");
        txtSegundoAp = new JTextArea(2,5);

        JLabel lblEdad = new JLabel("Edad:");
        txtEdad = new JTextArea(2,5);

        JLabel lblSemestre = new JLabel("Semestre:");
        txtSemestre = new JTextArea(2,5);

        JLabel lblCarrera2 = new JLabel("Carrera:");
        txtCarrera = new JTextArea(2,5);
        
        par = new JCheckBox("par");
     //  par.addChangeListener( this);

        todos = new JCheckBox("todos");
       // todos.addChangeListener((ChangeListener) this);
        // Crear botones
        JButton btnPrimero = new JButton("<<");
        JButton btnAnterior = new JButton("<");
        JButton btnSiguiente = new JButton(">");
        JButton btnUltimo = new JButton(">>");
        JButton btnConsultar = new JButton("Consultar");

        tablaAlumnos = new JTable();
        
        // Crear paneles y agregar componentes
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel formPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel resultPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPanel = new JScrollPane(tablaAlumnos);

        formPanel.add(lblCarrera);
        formPanel.add(comboCarrera);
        formPanel.add(lblNumRegistro);
        formPanel.add(txtNumRegistro);
        formPanel.add(btnPrimero);
        formPanel.add(btnAnterior);
        formPanel.add(btnSiguiente);
        formPanel.add(btnUltimo);
        formPanel.add(btnConsultar);
        formPanel.add(par);
        formPanel.add(todos);
       formPanel2.add(lblNumControl);
        formPanel2.add(txtNumControl);
        formPanel2.add(lblNombre);
        formPanel2.add(txtNombre);
        formPanel2.add(lblPrimerAp);
        formPanel2.add(txtPrimerAp);
        formPanel2.add(lblSegundoAp);
        formPanel2.add(txtSegundoAp);
        formPanel2.add(lblEdad);
        formPanel2.add(txtEdad);
        formPanel2.add(lblSemestre);
        formPanel2.add(txtSemestre);
        formPanel2.add(lblCarrera2);
        formPanel2.add(txtCarrera);
        
        resultPanel.add(lblConsulta, BorderLayout.NORTH);
        resultPanel.add(scrollPanel, BorderLayout.CENTER);
        cargarDatosAlumnos();
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        // Agregar panel a la ventana
        add(panel);

        // Cargar los datos del primer registro al iniciar la ventana
        cargarDatosRegistro();

        // Asignar listeners a los botones
        btnPrimero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numRegistroActual = 0;
                cargarDatosRegistro();
            }
        });

        btnAnterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (numRegistroActual > 0) {
                    numRegistroActual--;
                    cargarDatosRegistro();
                }
            }
        });

        btnSiguiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (numRegistroActual < alumnos.size() - 1) {
                    numRegistroActual++;
                    cargarDatosRegistro();
                }
            }
        });

        btnUltimo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numRegistroActual = alumnos.size() - 1;
                cargarDatosRegistro();
            }
        });

        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String carreraSeleccionada = (String) comboCarrera.getSelectedItem();
                ResultSet rs = alumnoDAO.buscarAlumnoPorCarrera(carreraSeleccionada);
                if (rs != null) {
                    try {
                        txtResultados.setText("");
                        while (rs.next()) {
                            // Leer los datos del resultado y mostrarlos en el JTextArea
                            String numControl = rs.getString("NumControl");
                            String nombre = rs.getString("Nombre");
                            String primerAp = rs.getString("PrimerAp");
                            String segundoAp = rs.getString("SegundoAp");
                            byte edad = rs.getByte("Edad");
                            byte semestre = rs.getByte("Semestre");
                            String carrera = rs.getString("Carrera");

                            txtNumControl.append("N�mero de Control: " + numControl );
                            txtNombre.append("Nombre: " + nombre );
                            txtPrimerAp.append("Primer Apellido: " + primerAp );
                            txtSegundoAp.append("Segundo Apellido: " + segundoAp );
                            txtEdad.append("Edad: " + edad );
                            txtSemestre.append("Semestre: " + semestre );
                            txtCarrera.append("Carrera: " + carrera );
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al ejecutar consulta");
                }
            }
        });
        // Configurar ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void stateChange(ChangeEvent e) {
    	if(par.isSelected()) {
            cargarDatosAlumnosPares();
    	}
    	if(todos.isSelected()) {
            cargarDatosAlumnos();
    	}
    }
    private void cargarCarreras() {
        List<String> carreras = alumnoDAO.obtenerCarreras();
        for (String carrera : carreras) {
            comboCarrera.addItem(carrera);
        }
    }

    private void cargarDatosRegistro() {
        if (alumnos.isEmpty()) {
            txtNumRegistro.setText("0/0");
            txtResultados.setText("No hay registros");
        } else {
            Alumno alumno = alumnos.get(numRegistroActual);
            txtNumRegistro.setText((numRegistroActual + 1) + "/" + alumnos.size());
            txtResultados.setText(alumno.toString());
        }
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
    private void cargarDatosAlumnosPares() {
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
        	int num=Integer.parseInt(alumno.getNumControl())%2;
           if(num==0) {
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
        }
        // Establecer el modelo de tabla en la JTable
        tablaAlumnos.setModel(model);
    }
}
