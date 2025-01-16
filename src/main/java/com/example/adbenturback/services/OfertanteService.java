package com.example.adbenturback.services;

import com.example.adbenturback.model.Ofertante;

public interface OfertanteService {
    Ofertante agregarOfertante(Ofertante ofertante);
    Ofertante findByUsuarioId(Long idUsuario);
}
