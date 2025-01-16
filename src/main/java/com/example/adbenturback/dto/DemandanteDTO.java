package com.example.adbenturback.dto;

import java.io.Serializable;

public class DemandanteDTO implements Serializable {
    private String telefonoEmergencia;
    private Integer edad;
    private Integer saldo;

    // Constructor vacío
    public DemandanteDTO() {}

    // Getters y Setters
    public String getTelefonoEmergencia() {
        return telefonoEmergencia;
    }

    public void setTelefonoEmergencia(String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }
}
