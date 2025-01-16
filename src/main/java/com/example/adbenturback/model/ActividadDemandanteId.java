package com.example.adbenturback.model;

import java.io.Serializable;
import java.util.Objects;

public class ActividadDemandanteId implements Serializable {
    private Long actividad;
    private Long demandante;

    public ActividadDemandanteId() {}

    public ActividadDemandanteId(Long actividad, Long demandante) {
        this.actividad = actividad;
        this.demandante = demandante;
    }

    // Getters y setters
    public Long getActividad() {
        return actividad;
    }

    public void setActividad(Long actividad) {
        this.actividad = actividad;
    }

    public Long getDemandante() {
        return demandante;
    }

    public void setDemandante(Long demandante) {
        this.demandante = demandante;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActividadDemandanteId that = (ActividadDemandanteId) o;
        return Objects.equals(actividad, that.actividad) && Objects.equals(demandante, that.demandante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actividad, demandante);
    }
}
