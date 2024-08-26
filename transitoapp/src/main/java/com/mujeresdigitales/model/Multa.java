package com.mujeresdigitales.model;


public class Multa {
    private int multaId;
    private double multaValorMulta;
    private int multaDiasMora;
    private double multaValorPagar;
    private int personaId; // clave foránea

    public Multa() {}

    // Constructor con todos los parámetros
    public Multa(int multaId, double multaValorMulta, int multaDiasMora, double multaValorPagar, int personaId) {
        this.multaId = multaId;
        this.multaValorMulta = multaValorMulta;
        this.multaDiasMora = multaDiasMora;
        this.multaValorPagar = multaValorPagar;
        this.personaId = personaId;
    }

    // Constructor sin el multaId (para cuando se crea una nueva multa)
    public Multa(double multaValorMulta, int multaDiasMora, double multaValorPagar, int personaId) {
        this.multaValorMulta = multaValorMulta;
        this.multaDiasMora = multaDiasMora;
        this.multaValorPagar = multaValorPagar;
        this.personaId = personaId;
    }

   
    // Getters y setters

    public int getMultaId() {
        return multaId;
    }

    public void setMultaId(int multaId) {
        this.multaId = multaId;
    }

    public double getMultaValorMulta() {
        return multaValorMulta;
    }

    public void setMultaValorMulta(double multaValorMulta) {
        this.multaValorMulta = multaValorMulta;
    }

    public int getMultaDiasMora() {
        return multaDiasMora;
    }

    public void setMultaDiasMora(int multaDiasMora) {
        this.multaDiasMora = multaDiasMora;
    }

    public double getMultaValorPagar() {
        return multaValorPagar;
    }

    public void setMultaValorPagar(double multaValorPagar) {
        this.multaValorPagar = multaValorPagar;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }
}


