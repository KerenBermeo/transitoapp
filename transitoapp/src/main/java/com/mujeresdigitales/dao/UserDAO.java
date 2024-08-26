package com.mujeresdigitales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.mujeresdigitales.config.DatabaseConnection;
import com.mujeresdigitales.model.User;

public class UserDAO {
    // Crear usuario
    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO tblUser (userName, userLogin, userPassword) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getUserLogin());
            
            // Hashing de la contraseña
            String hashedPassword = BCrypt.hashpw(user.getUserPassword(), BCrypt.gensalt());
            stmt.setString(3, hashedPassword);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al crear el usuario: " + e.getMessage());
            throw e;
        }
    }

    // Login de usuario
    public User loginUser(String userLogin, String userPassword) throws SQLException {
        String sql = "SELECT * FROM tblUser WHERE userLogin = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userLogin);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("userPassword");
                    if (BCrypt.checkpw(userPassword, hashedPassword)) {
                        return new User(
                            rs.getInt("userId"),
                            rs.getString("userName"),
                            rs.getString("userLogin"),
                            hashedPassword
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al iniciar sesión: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // Obtener usuario por ID
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM tblUser WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("userLogin"),
                        rs.getString("userPassword")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el usuario: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // Obtener todos los Usuarios
    public List<User> getAllUsers() throws SQLException {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM tblUser";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            users.add(new User(
                rs.getInt("userId"),
                rs.getString("userName"),
                rs.getString("userLogin"),
                rs.getString("userPassword")
            ));
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
        throw e;
    }
    return users;
    }

    // Actualizar usuarios
    public void updateUser(User user) throws SQLException {
        // Construir la consulta SQL dinámicamente
        StringBuilder sql = new StringBuilder("UPDATE tblUser SET ");
        List<Object> params = new ArrayList<>();
        
        if (user.getUserName() != null) {
            sql.append("userName = ?, ");
            params.add(user.getUserName());
        }
        if (user.getUserLogin() != null) {
            sql.append("userLogin = ?, ");
            params.add(user.getUserLogin());
        }
        if (user.getUserPassword() != null) {
            sql.append("userPassword = ?, ");
            params.add(user.getUserPassword());
        }
        
        // Eliminar la última coma y espacio
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE userId = ?");
        params.add(user.getUserId());
        
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
                System.err.println("No se actualizó ningún usuario con ID: " + user.getUserId());
            }
        } catch (SQLException e) {
           
            System.err.println("Error al actualizar el usuario: " + e.getMessage());
            throw e;
        }
    }
    
    // Borrar un usuario
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM tblUser WHERE userId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecer el parámetro para la consulta
            stmt.setInt(1, userId);
            
            // Ejecutar la eliminación
            int affectedRows = stmt.executeUpdate();
            
            // Verificar si se eliminó alguna fila
            if (affectedRows == 0) {
                System.err.println("No se eliminó ningún usuario con ID: " + userId);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
            throw e;
        }
    }
      
    
}
