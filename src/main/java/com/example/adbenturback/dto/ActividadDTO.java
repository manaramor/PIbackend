package com.example.adbenturback.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ActividadDTO implements Serializable {

    private Long idActividad;
    private String titulo;
    private String descripcion;
    private Double precio;
    private String tipoPago;
    private String tipoActividad;
    private Integer edadRequerida;
    private String estadoActividad;
    private String puntoEncuentro;
    private Integer minimoPersonas;
    private Integer maximoPersonas;
    private LocalDate fechaPublicacion;
    private LocalDate fechaLimiteInscripcion;
    private LocalDate fechaRealizacion;
    private String duracion;
    private String ubicacion;
    private String dificultad;
    private String politicaCancelacion;
    private String materialNecesario;
    private Long idOfertante;

    // Constructor vacío
    public ActividadDTO() {
    }
    // Getters y Setters
    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public Integer getEdadRequerida() {
        return edadRequerida;
    }

    public void setEdadRequerida(Integer edadRequerida) {
        this.edadRequerida = edadRequerida;
    }

    public String getEstadoActividad() {
        return estadoActividad;
    }

    public void setEstadoActividad(String estadoActividad) {
        this.estadoActividad = estadoActividad;
    }

    public String getPuntoEncuentro() {
        return puntoEncuentro;
    }

    public void setPuntoEncuentro(String puntoEncuentro) {
        this.puntoEncuentro = puntoEncuentro;
    }

    public Integer getMinimoPersonas() {
        return minimoPersonas;
    }

    public void setMinimoPersonas(Integer minimoPersonas) {
        this.minimoPersonas = minimoPersonas;
    }

    public Integer getMaximoPersonas() {
        return maximoPersonas;
    }

    public void setMaximoPersonas(Integer maximoPersonas) {
        this.maximoPersonas = maximoPersonas;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getFechaLimiteInscripcion() {
        return fechaLimiteInscripcion;
    }

    public void setFechaLimiteInscripcion(LocalDate fechaLimiteInscripcion) {
        this.fechaLimiteInscripcion = fechaLimiteInscripcion;
    }

    public LocalDate getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(LocalDate fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getPoliticaCancelacion() {
        return politicaCancelacion;
    }

    public void setPoliticaCancelacion(String politicaCancelacion) {
        this.politicaCancelacion = politicaCancelacion;
    }

    public String getMaterialNecesario() {
        return materialNecesario;
    }

    public void setMaterialNecesario(String materialNecesario) {
        this.materialNecesario = materialNecesario;
    }

    public Long getIdOfertante() {
        return idOfertante;
    }

    public void setIdOfertante(Long idOfertante) {
        this.idOfertante = idOfertante;
    }

    @Override
    public String toString() {
        return "ActividadDTO{" +
                "idActividad=" + idActividad +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", tipoPago='" + tipoPago + '\'' +
                ", tipoActividad='" + tipoActividad + '\'' +
                ", edadRequerida=" + edadRequerida +
                ", estadoActividad='" + estadoActividad + '\'' +
                ", puntoEncuentro='" + puntoEncuentro + '\'' +
                ", minimoPersonas=" + minimoPersonas +
                ", maximoPersonas=" + maximoPersonas +
                ", fechaPublicacion=" + fechaPublicacion +
                ", fechaLimiteInscripcion=" + fechaLimiteInscripcion +
                ", fechaRealizacion=" + fechaRealizacion +
                ", duracion='" + duracion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", dificultad='" + dificultad + '\'' +
                ", politicaCancelacion='" + politicaCancelacion + '\'' +
                ", materialNecesario='" + materialNecesario + '\'' +
                ", idOfertante=" + idOfertante +
                '}';
    }
}
