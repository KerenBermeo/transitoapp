package com.mujeresdigitales.service;


import java.sql.SQLException;
import com.mujeresdigitales.dao.UserDAO;
import com.mujeresdigitales.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    // Logear un usuario
    public boolean login(String login, String password) {
        try {
            User user = userDAO.loginUser(login, password);
            if (user != null) {
                System.out.println("Inicio válido: Bienvenido " + user.getUserName());
                return true;
            } else {
                System.out.println("Credenciales incorrectas");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error en el inicio de sesión: " + e.getMessage());
            return false;
        }
    }

    // Registrar un nuevo usuario
    public boolean registerUser(String userName, String userLogin, String userPassword) {
        try {
            User user = new User();
            user.setUserName(userName);
            user.setUserLogin(userLogin);
            user.setUserPassword(userPassword); // La contraseña será hasheada en el DAO
            userDAO.createUser(user);
            System.out.println("Usuario registrado con éxito: " + user.getUserName());
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
            return false;
        }
    }

    // Actualizar un usuario
    public boolean updateUser(int userId, String userName, String userLogin, String userPassword) {
        try {
            User user = userDAO.getUserById(userId);
            if (user == null) {
                System.out.println("Usuario no encontrado");
                return false;
            }

            // Actualizar los campos del usuario
            if (userName != null) user.setUserName(userName);
            if (userLogin != null) user.setUserLogin(userLogin);
            if (userPassword != null) user.setUserPassword(userPassword);

            userDAO.updateUser(user);
            System.out.println("Usuario actualizado con éxito: " + user.getUserName());
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
            return false;
        }
    }
}
