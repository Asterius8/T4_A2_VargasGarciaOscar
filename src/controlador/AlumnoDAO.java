package controlador;

import ConexionBD.ConexionBD;
import modelo.Alumno;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {
    ConexionBD conexion = new ConexionBD();

    // M?todos ABCC (CRUD)

    // Altas
    public boolean agregarAlumno(Alumno a) {
        boolean res = false;
        String sql = "INSERT INTO Alumnos VALUES ('" + a.getNumControl() + "','" + a.getNombre() + "','"
                + a.getPrimerAp() + "','" + a.getSegundoAp() + "','" + a.getEdad() + "','" + a.getSemestre() + "','"
                + a.getCarrera() + "')";
        res = conexion.ejecutarInstruccionDML(sql);
        return res;
    }

    // Bajas
    public boolean eliminarAlumno(String numControl) {
        boolean res = false;
        String sql = "DELETE FROM Alumnos WHERE NumControl = '" + numControl + "'";
        res = conexion.ejecutarInstruccionDML(sql);
        return res;
    }

    // Cambios
    public boolean actualizarAlumno(Alumno a) {
        boolean res = false;
        String sql = "UPDATE Alumnos SET Nombre='" + a.getNombre() + "', PrimerAp='" + a.getPrimerAp()
                + "', SegundoAp='" + a.getSegundoAp() + "', Edad=" + a.getEdad() + ", Semestre=" + a.getSemestre()
                + ", Carrera='" + a.getCarrera() + "' WHERE NumControl ='" + a.getNumControl() + "'";
        res = conexion.ejecutarInstruccionDML(sql);
        return res;
    }

    // Consultas
    public List<Alumno> obtenerTodosAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM Alumnos";
        ResultSet res = conexion.ejecutarConsultaDML(sql);

        if (res != null) {
            try {
                while (res.next()) {
                    String numControl = res.getString("Num_Control");
                    String nombre = res.getString("Nombres");
                    String primerAp = res.getString("Primer_Ap");
                    String segundoAp = res.getString("Segundo_Ap");
                    byte edad = res.getByte("Edad");
                    byte semestre = res.getByte("Semestre");
                    String carrera = res.getString("Carrera");

                    Alumno alumno = new Alumno(numControl, nombre, primerAp, segundoAp, edad, semestre, carrera);
                    alumnos.add(alumno);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    res.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return alumnos;
    }

    public Alumno obtenerAlumnoPorNumControl(String numControl) {
        Alumno alumno = null;
        String sql = "SELECT * FROM Alumnos WHERE NumControl = '" + numControl + "'";
        ResultSet res = conexion.ejecutarConsultaDML(sql);

        if (res != null) {
            try {
                if (res.next()) {
                    String nombre = res.getString("Nombre");
                    String primerAp = res.getString("PrimerAp");
                    String segundoAp = res.getString("SegundoAp");
                    byte edad = res.getByte("Edad");
                    byte semestre = res.getByte("Semestre");
                    String carrera = res.getString("Carrera");

                    alumno = new Alumno(numControl, nombre, primerAp, segundoAp, edad, semestre, carrera);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    res.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return alumno;
    }
    public ResultSet buscarAlumnoPorCarrera(String carrera) {
        String sql = "SELECT * FROM Alumnos WHERE Carrera = '" + carrera + "'";
        return conexion.ejecutarConsultaDML(sql);
    }
    public List<String> obtenerCarreras() {
        List<String> carreras = new ArrayList<>();
        String sql = "SELECT DISTINCT Carrera FROM Alumnos";
        ResultSet res = conexion.ejecutarConsultaDML(sql);

        if (res != null) {
            try {
                while (res.next()) {
                    String carrera = res.getString("Carrera");
                    carreras.add(carrera);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    res.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return carreras;
    }


}