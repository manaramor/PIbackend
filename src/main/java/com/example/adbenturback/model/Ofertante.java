package com.example.adbenturback.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "Ofertante")
public class Ofertante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ofertante")
    private Long idOfertante;

    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true, nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @Column(name = "experiencia")
    private String experiencia;

    @Column(name = "certificacion")
    private String certificacion;

    @Column(name = "valoracion")
    private Integer valoracion;

    @OneToMany(mappedBy = "ofertante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Actividad> actividades;

    // Constructor vacio
    public Ofertante() {
    }

    // Getters y setters
    public Long getIdOfertante() {
        return idOfertante;
    }

    public void setIdOfertante(Long idOfertante) {
        this.idOfertante = idOfertante;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null && usuario.getOfertante() != this) {
            usuario.setOfertante(this);
        }
    }

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

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }
}
