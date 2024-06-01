package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private Connection conexion;
    private Statement stm;
    private ResultSet rs;

    public ConexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String URL = "jdbc:mysql://localhost:3306/BD_EscuelaTopicos?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String username = "root";
            String password = "Kitsune-sama08";
            conexion = DriverManager.getConnection(URL, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Error en el conector de MySQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error en la ruta URL de conexi�n");
            e.printStackTrace();
        }
    }

    public boolean ejecutarInstruccionDML(String instruccionDML) {
        boolean res = false;
        try {
            stm = conexion.createStatement();
            if (stm.executeUpdate(instruccionDML) >= 1) {
                res = true;
            }
        } catch (SQLException e) {
            System.out.println("Error en instrucci�n SQL");
            System.out.println(instruccionDML);
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet ejecutarConsultaDML(String consultaDML) {
        rs = null;
        try {
            stm = conexion.createStatement();
            rs = stm.executeQuery(consultaDML);
        } catch (SQLException e) {
            System.out.println("Error en instrucci�n SQL");
            e.printStackTrace();
        }
        return rs;
    }

    public void cerrarConexion() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

