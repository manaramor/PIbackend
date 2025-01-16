package com.example.adbenturback.repository;

import com.example.adbenturback.model.Actividad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    public Actividad findActividadByIdActividad(Long idActividad);

    public List<Actividad> findByTituloContainingIgnoreCase(String titulo);
}
