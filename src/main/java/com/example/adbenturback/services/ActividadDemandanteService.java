package com.example.adbenturback.services;

import com.example.adbenturback.dto.ActividadDemandanteDTO;
import com.example.adbenturback.model.ActividadDemandante;

import java.util.List;

public interface ActividadDemandanteService {

    void reservarActividad(ActividadDemandanteDTO actividadDemandanteDTO) throws Exception;

    List<ActividadDemandanteDTO> obtenerReservasPorDemandante(Long idDemandante);

    void eliminarReserva(Long idActividad, Long idDemandante) throws Exception;
}
