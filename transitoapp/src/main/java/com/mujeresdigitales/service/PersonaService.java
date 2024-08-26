package com.mujeresdigitales.service;

import java.sql.SQLException;
import com.mujeresdigitales.dao.PersonaDAO;
import com.mujeresdigitales.model.Persona;
import java.util.List;

public class PersonaService {
    private PersonaDAO personaDAO = new PersonaDAO();

    public List<Persona> getAllPersonas() {
        try {
            return personaDAO.getAllPersonas();
        } catch (SQLException e) {
            System.out.println("Error al obtener personas: " + e.getMessage());
            return null;
        }
    }

    // Servicio para crear una persona
    public Persona createPersona(Persona persona) {
        try {
            personaDAO.createPersona(persona);
            return persona;
        } catch (SQLException e) {
            System.out.println("Error al crear persona: " + e.getMessage());
            return null;
        }
    }

    // Obtener una persona por ID
    public Persona getPersonaById(int personaId) {
        try {
            return personaDAO.getPersonaById(personaId);
        } catch (SQLException e) {
            System.err.println("Error al obtener la persona por ID: " + e.getMessage());
            return null; // O manejar el error según tu lógica de negocio
        }
    }
}
