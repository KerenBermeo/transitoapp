package com.mujeresdigitales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mujeresdigitales.config.DatabaseConnection;
import com.mujeresdigitales.model.Persona;

public class PersonaDAO {
    
    // Crear persona
    public void createPersona(Persona persona) throws SQLException {
        String sql = "INSERT INTO tblPersona (personaNombre, personaCorreo) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, persona.getPersonaNombre());
            stmt.setString(2, persona.getPersonaCorreo());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        persona.setPersonaId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al crear la persona: " + e.getMessage());
            throw e;
        }
    }

    // Obtener persona por ID
    public Persona getPersonaById(int personaId) throws SQLException {
        String sql = "SELECT * FROM tblPersona WHERE personaId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, personaId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Persona(
                        rs.getInt("personaId"),
                        rs.getString("personaNombre"),
                        rs.getString("personaCorreo")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la persona: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // Obtener todas las personas
    public List<Persona> getAllPersonas() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM tblPersona";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                personas.add(new Persona(
                    rs.getInt("personaId"),
                    rs.getString("personaNombre"),
                    rs.getString("personaCorreo")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las personas: " + e.getMessage());
            throw e;
        }
        return personas;
    }

    // Actualizar persona
    public void updatePersona(Persona persona) throws SQLException {
        // Construir la consulta SQL dinámicamente
        StringBuilder sql = new StringBuilder("UPDATE tblPersona SET ");
        List<Object> params = new ArrayList<>();
        
        if (persona.getPersonaNombre() != null) {
            sql.append("personaNombre = ?, ");
            params.add(persona.getPersonaNombre());
        }
        if (persona.getPersonaCorreo() != null) {
            sql.append("personaCorreo = ?, ");
            params.add(persona.getPersonaCorreo());
        }
        
        // Eliminar la última coma y espacio
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE personaId = ?");
        params.add(persona.getPersonaId());
        
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
                System.err.println("No se actualizó ninguna persona con ID: " + persona.getPersonaId());
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la persona: " + e.getMessage());
            throw e;
        }
    }

    // Borrar una persona
    public void deletePersona(int personaId) throws SQLException {
        String sql = "DELETE FROM tblPersona WHERE personaId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecer el parámetro para la consulta
            stmt.setInt(1, personaId);
            
            // Ejecutar la eliminación
            int affectedRows = stmt.executeUpdate();
            
            // Verificar si se eliminó alguna fila
            if (affectedRows == 0) {
                System.err.println("No se eliminó ninguna persona con ID: " + personaId);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la persona: " + e.getMessage());
            throw e;
        }
    }
}
