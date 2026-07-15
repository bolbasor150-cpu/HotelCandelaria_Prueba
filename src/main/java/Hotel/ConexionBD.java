package Hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;databaseName=HotelCandelaria;integratedSecurity=true;encrypt=false;trustServerCertificate=true;";

    private static Connection conexion;

    private ConexionBD() {
    }

    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        return conexion;
    }
}