package com.example.adbenturback.dto;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ActividadDemandanteDTO implements Serializable {

    private Long idActividad;
    private Long idDemandante;
    private LocalDateTime fechaReserva;

    private String tituloActividad;
    private LocalDate fechaRealizacion;

    // Constructor vacío
    public ActividadDemandanteDTO() {
    }

    // Constructor con parámetros
    public ActividadDemandanteDTO(Long idActividad, Long idDemandante, LocalDateTime fechaReserva) {
        this.idActividad = idActividad;
        this.idDemandante = idDemandante;
        this.fechaReserva = fechaReserva;
    }

    // Constructor con parámetros
    public ActividadDemandanteDTO(String tituloActividad, LocalDate fechaRealizacion) {
        this.tituloActividad = tituloActividad;
        this.fechaRealizacion = fechaRealizacion;
    }

    // Getters y Setters
    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public Long getIdDemandante() {
        return idDemandante;
    }

    public void setIdDemandante(Long idDemandante) {
        this.idDemandante = idDemandante;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getTituloActividad() {
        return tituloActividad;
    }

    public void setTituloActividad(String tituloActividad) {
        this.tituloActividad = tituloActividad;
    }

    public LocalDate getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(LocalDate fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }
}