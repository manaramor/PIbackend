package com.example.adbenturback.dto;

import java.io.Serializable;

public class OfertanteDTO implements Serializable {
    private String experiencia;
    private String certificacion;
    private Integer valoracion;

    // Constructor vacío
    public OfertanteDTO() {}

    // Getters y Setters
    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }
}
