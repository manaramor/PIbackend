package com.example.adbenturback.services;

import com.example.adbenturback.model.Demandante;

public interface DemandanteService {
    Demandante agregarDemandante(Demandante demandante);
    Demandante findByUsuarioId(Long idUsuario);
    Long obtenerIdDemandantePorIdUsuario(Long idUsuario);
}
