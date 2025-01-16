package com.example.adbenturback.services;

import com.example.adbenturback.model.Ofertante;
import com.example.adbenturback.repository.OfertanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertanteServiceImpl implements OfertanteService {

    @Autowired
    private OfertanteRepository ofertanteRepo;

    @Override
    public Ofertante agregarOfertante(Ofertante ofertante) {
        return ofertanteRepo.save(ofertante);
    }

    @Override
    public Ofertante findByUsuarioId(Long idUsuario) {
        return ofertanteRepo.findOfertanteByIdOfertante(idUsuario);
    }
}
