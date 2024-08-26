package com.mujeresdigitales.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/transitobd";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static Connection connection;

    private DatabaseConnection() {
        // Constructor privado para evitar instanciación
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar la conexión
    public static boolean checkConnection() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {
            // Si la consulta tiene éxito, la conexión está activa
            return true;
        } catch (SQLException e) {
            // Si ocurre una excepción, la conexión no está activa
            e.printStackTrace();
            return false;
        }
    }


}
