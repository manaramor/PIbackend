package com.example.adbenturback.services;

import com.example.adbenturback.model.Demandante;
import com.example.adbenturback.repository.DemandanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemandanteServiceImpl implements DemandanteService {

    @Autowired
    private DemandanteRepository demandanteRepo;

    @Override
    public Demandante agregarDemandante(Demandante demandante) {
        return demandanteRepo.save(demandante);
    }

    @Override
    public Demandante findByUsuarioId(Long idUsuario) {
        return demandanteRepo.findByUsuario_IdUsuario(idUsuario); 
    }

    @Override
    public Long obtenerIdDemandantePorIdUsuario(Long idUsuario) {
        Demandante demandante = demandanteRepo.findByUsuario_IdUsuario(idUsuario);
        if (demandante == null) {
            throw new RuntimeException("No se encontró un demandante para el usuario con ID: " + idUsuario);
        }
        return demandante.getIdDemandante();
    }
}
