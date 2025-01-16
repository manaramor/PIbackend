package com.example.adbenturback.dto;

import java.io.Serializable;

public class RegistroDTO implements Serializable {
    private UsuarioDTO usuario;
    private OfertanteDTO ofertante;
    private DemandanteDTO demandante;

    // Constructor vacío
    public RegistroDTO() {}

    // Getters y Setters
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public OfertanteDTO getOfertante() {
        return ofertante;
    }

    public void setOfertante(OfertanteDTO ofertante) {
        this.ofertante = ofertante;
    }

    public DemandanteDTO getDemandante() {
        return demandante;
    }

    public void setDemandante(DemandanteDTO demandante) {
        this.demandante = demandante;
    }
}
