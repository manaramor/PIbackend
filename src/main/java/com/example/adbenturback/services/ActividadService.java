package com.example.adbenturback.services;

import com.example.adbenturback.dto.ActividadDTO;
import com.example.adbenturback.model.Actividad;

import java.util.List;

public interface ActividadService {
    public List<Actividad> getAllActividades();

    public Actividad findActividadById(Long idActividad);

    public Actividad getActividadByName(String nombre);

    public Actividad insertarActividad(ActividadDTO actividadDTO);

    public Actividad actualizarActividad(Actividad actividad);

    public void borrarActividad(Actividad actividad);

    List<ActividadDTO> buscarActividadesPorTitulo(String titulo);

}
