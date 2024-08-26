package com.mujeresdigitales.model;


public class Persona {
    private int personaId;
    private String personaNombre;
    private String personaCorreo;

    public Persona() {}

    // Constructor con todos los parámetros
    public Persona(int personaId, String personaNombre, String personaCorreo) {
        this.personaId = personaId;
        this.personaNombre = personaNombre;
        this.personaCorreo = personaCorreo;
    }

    // Constructor sin el personaId (para cuando se crea una nueva persona)
    public Persona(String personaNombre, String personaCorreo) {
        this.personaNombre = personaNombre;
        this.personaCorreo = personaCorreo;
    }

    // Getters y setters

    public int getPersonaId() {
        return personaId;
    }

    // Setter para personaId
    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    // Getter para personaNombre
    public String getPersonaNombre() {
        return personaNombre;
    }

    // Setter para personaNombre
    public void setPersonaNombre(String personaNombre) {
        this.personaNombre = personaNombre;
    }

    // Getter para personaCorreo
    public String getPersonaCorreo() {
        return personaCorreo;
    }

    // Setter para personaCorreo
    public void setPersonaCorreo(String personaCorreo) {
        this.personaCorreo = personaCorreo;
    }
}