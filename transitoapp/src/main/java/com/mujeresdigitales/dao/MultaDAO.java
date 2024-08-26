package com.mujeresdigitales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mujeresdigitales.config.DatabaseConnection;
import com.mujeresdigitales.model.Multa;

public class MultaDAO {
    
    // Crear multa
    public void createMulta(Multa multa) throws SQLException {
        String sql = "INSERT INTO tblMulta (multaValorMulta, multaDiasMora, multaValorPagar, personaId) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, multa.getMultaValorMulta());
            stmt.setInt(2, multa.getMultaDiasMora());
            stmt.setDouble(3, multa.getMultaValorPagar());
            stmt.setInt(4, multa.getPersonaId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        multa.setMultaId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al crear la multa: " + e.getMessage());
            throw e;
        }
    }

    // Obtener multa por ID
    public Multa getMultaById(int multaId) throws SQLException {
        String sql = "SELECT * FROM tblMulta WHERE multaId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, multaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Multa(
                        rs.getInt("multaId"),
                        rs.getDouble("multaValorMulta"),
                        rs.getInt("multaDiasMora"),
                        rs.getDouble("multaValorPagar"),
                        rs.getInt("personaId")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la multa: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // Obtener todas las multas
    public List<Multa> getAllMultas() throws SQLException {
        List<Multa> multas = new ArrayList<>();
        String sql = "SELECT * FROM tblMulta";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                multas.add(new Multa(
                    rs.getInt("multaId"),
                    rs.getDouble("multaValorMulta"),
                    rs.getInt("multaDiasMora"),
                    rs.getDouble("multaValorPagar"),
                    rs.getInt("personaId")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las multas: " + e.getMessage());
            throw e;
        }
        return multas;
    }

    // Actualizar multa
    public void updateMulta(Multa multa) throws SQLException {
        
        StringBuilder sql = new StringBuilder("UPDATE tblMulta SET ");
        List<Object> params = new ArrayList<>();
    
        // Verificar y añadir cada campo a la consulta si no es el valor por defecto
        if (multa.getMultaValorMulta() != 0) { 
            sql.append("multaValorMulta = ?, ");
            params.add(multa.getMultaValorMulta());
        }
        if (multa.getMultaDiasMora() != 0) { 
            sql.append("multaDiasMora = ?, ");
            params.add(multa.getMultaDiasMora());
        }
        if (multa.getMultaValorPagar() != 0) { 
            sql.append("multaValorPagar = ?, ");
            params.add(multa.getMultaValorPagar());
        }
        if (multa.getPersonaId() != 0) { 
            sql.append("personaId = ?, ");
            params.add(multa.getPersonaId());
        }
    
        // Eliminar la última coma y espacio
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE multaId = ?");
        params.add(multa.getMultaId());
    
        // Preparar y ejecutar la consulta
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
    
            // Establecer los parámetros en el PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
    
            // Ejecutar la actualización
            int affectedRows = stmt.executeUpdate();
    
            // Verificar si la actualización fue exitosa
            if (affectedRows == 0) {
                System.err.println("No se actualizó ninguna multa con ID: " + multa.getMultaId());
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la multa: " + e.getMessage());
            throw e;
        }
    }
    

    // Borrar una multa
    public void deleteMulta(int multaId) throws SQLException {
        String sql = "DELETE FROM tblMulta WHERE multaId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecer el parámetro para la consulta
            stmt.setInt(1, multaId);
            
            // Ejecutar la eliminación
            int affectedRows = stmt.executeUpdate();
            
            // Verificar si se eliminó alguna fila
            if (affectedRows == 0) {
                System.err.println("No se eliminó ninguna multa con ID: " + multaId);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la multa: " + e.getMessage());
            throw e;
        }
    }
}
