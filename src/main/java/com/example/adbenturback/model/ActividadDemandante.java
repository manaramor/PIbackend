package com.example.adbenturback.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "actividaddemandante")
@IdClass(ActividadDemandanteId.class)
public class ActividadDemandante implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id", nullable = false)
    private Actividad actividad;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandante_id", nullable = false)
    private Demandante demandante;

    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion; 

    //Constructor vacio
    public ActividadDemandante() {
    }

    //Getters y setters
    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Demandante getDemandante() {
        return demandante;
    }

    public void setDemandante(Demandante demandante) {
        this.demandante = demandante;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }
    
    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
