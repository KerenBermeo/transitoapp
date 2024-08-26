package com.mujeresdigitales.service;

import java.sql.SQLException;
import com.mujeresdigitales.dao.MultaDAO;
import com.mujeresdigitales.dao.PersonaDAO;
import com.mujeresdigitales.model.Multa;
import com.mujeresdigitales.model.Persona;

import java.util.List;
import java.util.ArrayList;

public class MultaService {
    private MultaDAO multaDAO = new MultaDAO();
    private PersonaDAO personaDAO = new PersonaDAO();

    // Calcular y guardar una nueva multa
    public void calcularYGuardarMulta(Multa multa) {
        // Validar que la persona exista
        try {
            Persona persona = personaDAO.getPersonaById(multa.getPersonaId());
            if (persona == null) {
                System.out.println("Error: Persona con ID " + multa.getPersonaId() + " no existe.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia de la persona: " + e.getMessage());
            return;
        }

        // Calcular el valor a pagar según los días de mora
        int diasMora = multa.getMultaDiasMora();
        double valorMulta = multa.getMultaValorMulta();
        double valorPagar;

        if (diasMora < 30) {
            valorPagar = valorMulta * 1.05;
        } else if (diasMora == 30) {
            valorPagar = valorMulta * 1.10;
        } else {
            valorPagar = valorMulta * 1.15;
        }

        multa.setMultaValorPagar(valorPagar);

        try {
            multaDAO.createMulta(multa);
            System.out.println("Multa creada exitosamente para la persona con ID " + multa.getPersonaId());
        } catch (SQLException e) {
            System.out.println("Error al guardar la multa: " + e.getMessage());
        }
    }

    // Obtener una multa por ID
    public Multa obtenerMultaPorId(int multaId) {
        try {
            return multaDAO.getMultaById(multaId);
        } catch (SQLException e) {
            System.out.println("Error al obtener la multa: " + e.getMessage());
            return null;
        }
    }

    // Obtener todas las multas
    public List<Multa> obtenerTodasLasMultas() {
        try {
            return multaDAO.getAllMultas();
        } catch (SQLException e) {
            System.out.println("Error al obtener todas las multas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Actualizar una multa existente
    public void actualizarMulta(Multa multa) {
        try {
            multaDAO.updateMulta(multa);
        } catch (SQLException e) {
            System.out.println("Error al actualizar la multa: " + e.getMessage());
        }
    }

    // Eliminar una multa por ID
    public void eliminarMulta(int multaId) {
        try {
            multaDAO.deleteMulta(multaId);
        } catch (SQLException e) {
            System.out.println("Error al eliminar la multa: " + e.getMessage());
        }
    }
}

