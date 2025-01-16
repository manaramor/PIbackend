package com.example.adbenturback.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Demandante")
public class Demandante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demandante")
    private Long idDemandante;

    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true, nullable = false)
    private Usuario usuario;

    @Column(name = "telefono_emergencia")
    private String telefonoEmergencia;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "saldo")
    private Integer saldo;

    // Constructor vacío
    public Demandante() {}

    // Getters y setters
    public Long getIdDemandante() {
        return idDemandante;
    }

    public void setIdDemandante(Long idDemandante) {
        this.idDemandante = idDemandante;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null && usuario.getDemandante() != this) {
            usuario.addDemandante(this);
        }
    }

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

    public Object map(Object object) {
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }
}
