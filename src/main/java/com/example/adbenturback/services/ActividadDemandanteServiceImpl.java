package com.example.adbenturback.services;

import com.example.adbenturback.dto.ActividadDemandanteDTO;
import com.example.adbenturback.model.Actividad;
import com.example.adbenturback.model.ActividadDemandante;
import com.example.adbenturback.model.ActividadDemandanteId;
import com.example.adbenturback.model.Demandante;
import com.example.adbenturback.repository.ActividadDemandanteRepository;
import com.example.adbenturback.repository.ActividadRepository;
import com.example.adbenturback.repository.DemandanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadDemandanteServiceImpl implements ActividadDemandanteService {

    @Autowired
    private ActividadDemandanteRepository actividadDemandanteRepo;

    @Autowired
    private ActividadRepository actividadRepo;

    @Autowired
    private DemandanteRepository demandanteRepo;

    @Override
    public void reservarActividad(ActividadDemandanteDTO actividadDemandanteDTO) throws Exception {
        System.out.println("Iniciando proceso de reserva...");
    
        Long idActividad = actividadDemandanteDTO.getIdActividad();
        Long idDemandante = actividadDemandanteDTO.getIdDemandante();
    
        System.out.println("ID Actividad recibido: " + idActividad);
        System.out.println("ID Demandante recibido: " + idDemandante);
    
        Actividad actividad = actividadRepo.findById(idActividad)
            .orElseThrow(() -> new Exception("Actividad no encontrada."));
        System.out.println("Actividad encontrada: " + actividad);
    
        Demandante demandante = demandanteRepo.findById(idDemandante)
            .orElseThrow(() -> new Exception("Demandante no encontrado."));
        System.out.println("Demandante encontrado: " + demandante);
    
        // Comprobación de reserva duplicada
        if (actividadDemandanteRepo.existsByActividadIdActividadAndDemandanteIdDemandante(idActividad, idDemandante)) {
            System.err.println("Error: Ya existe una reserva para esta actividad y demandante.");
            throw new Exception("Ya has reservado esta actividad.");
        }
    
        // Crear y guardar la reserva
        ActividadDemandante reserva = new ActividadDemandante();
        reserva.setActividad(actividad);
        reserva.setDemandante(demandante);
    
        actividadDemandanteRepo.save(reserva);
        System.out.println("Reserva guardada exitosamente.");
    }
    
    

    @Override
    public List<ActividadDemandanteDTO> obtenerReservasPorDemandante(Long idDemandante) {
        System.out.println("Buscando actividades para el demandante con ID: " + idDemandante);
        
        List<ActividadDemandante> reservas = actividadDemandanteRepo.findByDemandanteIdDemandante(idDemandante);
        System.out.println("Reservas encontradas: " + reservas);
    
        return reservas.stream()
                       .map(this::mapToDTO)
                       .collect(Collectors.toList());
    }
    
    
    //Metodo que transforma la actividadmdenadante en dto
    private ActividadDemandanteDTO mapToDTO(ActividadDemandante reserva) {

        ActividadDemandanteDTO dto = new ActividadDemandanteDTO();
    
        dto.setIdActividad(reserva.getActividad().getIdActividad());         
        dto.setIdDemandante(reserva.getDemandante().getIdDemandante());     
        dto.setFechaReserva(reserva.getFechaInscripcion());

        dto.setTituloActividad(reserva.getActividad().getTitulo());
        dto.setFechaRealizacion(reserva.getActividad().getFechaRealizacion());
        return dto;
    }

    @Override
    public void eliminarReserva(Long idActividad, Long idDemandante) throws Exception {
        System.out.println("Eliminando reserva para actividad con ID: " + idActividad + " y demandante con ID: " + idDemandante);
        
        ActividadDemandanteId actividadDemandanteId = new ActividadDemandanteId(idActividad, idDemandante);
        
        boolean existeReserva = actividadDemandanteRepo.existsById(actividadDemandanteId);
        System.out.println("¿Existe la reserva? " + existeReserva);
        
        if (!existeReserva) {
            throw new Exception("La reserva no existe.");
        }
        
        actividadDemandanteRepo.deleteById(actividadDemandanteId);
        System.out.println("Reserva eliminada exitosamente.");
    }

}
