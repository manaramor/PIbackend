package com.example.adbenturback.repository;

import com.example.adbenturback.model.ActividadDemandante;
import com.example.adbenturback.model.ActividadDemandanteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadDemandanteRepository extends JpaRepository<ActividadDemandante, ActividadDemandanteId> {

    @Query("SELECT ad FROM ActividadDemandante ad JOIN FETCH ad.actividad WHERE ad.demandante.id = :idDemandante")
    List<ActividadDemandante> findByDemandanteIdDemandante(@Param("idDemandante") Long idDemandante);


    boolean existsByActividadIdActividadAndDemandanteIdDemandante(Long idActividad, Long idDemandante);

    List<ActividadDemandante> findByActividadIdActividad(Long idActividad);

    boolean existsById(ActividadDemandanteId id);

}
