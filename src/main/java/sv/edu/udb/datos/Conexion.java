package sv.edu.udb.datos;

import java.sql.*;

/**
 * Clase que gestiona la conexión a la base de datos MySQL.
 * Implementa el patrón Singleton para el driver JDBC.
 */
public class Conexion {

    // Configuración de la conexión a MySQL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost/mediateca";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "rootpassword";
    
    // Driver estático compartido por todas las conexiones
    private static Driver driver = null;

    /**
     * Obtiene una conexión a la base de datos.
     * @return Connection objeto de conexión a la BD
     * @throws SQLException si hay error al conectar
     */
    public static synchronized Connection getConnection() throws SQLException {
        // Cargar el driver solo la primera vez
        if (driver == null) {
            try {
                // Cargar la clase del driver de MySQL
                Class<?> jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver) jdbcDriverClass.newInstance();
                // Registrar el driver con el DriverManager
                DriverManager.registerDriver(driver);
            } catch (Exception e) {
                System.out.println("Fallo en cargar el driver JDBC");
                e.printStackTrace();
            }
        }
        // Retornar una nueva conexión usando las credenciales configuradas
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    /**
     * Cierra un ResultSet de forma segura.
     * Verifica que no sea null antes de cerrarlo para evitar excepciones.
     * 
     * @param rs ResultSet a cerrar
     */
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra un PreparedStatement de forma segura.
     * Importante para liberar recursos de la BD.
     * 
     * @param stmt PreparedStatement a cerrar
     */
    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}