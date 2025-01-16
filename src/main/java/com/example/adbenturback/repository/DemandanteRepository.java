package com.example.adbenturback.repository;

import com.example.adbenturback.model.Demandante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandanteRepository extends JpaRepository<Demandante, Long> {

    public Demandante findDemandanteByIdDemandante(Long idDemandante);

    public Demandante findByUsuario_IdUsuario(Long idUsuario);



}
