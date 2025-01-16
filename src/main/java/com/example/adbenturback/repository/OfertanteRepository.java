package com.example.adbenturback.repository;

import com.example.adbenturback.model.Ofertante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertanteRepository extends JpaRepository<Ofertante, Long> {
    public Ofertante findOfertanteByIdOfertante(Long idOfertante);

}
