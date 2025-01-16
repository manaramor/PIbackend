package com.example.adbenturback.services;

import com.example.adbenturback.dto.ActividadDTO;
import com.example.adbenturback.model.Actividad;
import com.example.adbenturback.model.Ofertante;
import com.example.adbenturback.repository.ActividadRepository;
import com.example.adbenturback.repository.OfertanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadServiceImpl implements ActividadService {

    private static final Logger logger = LoggerFactory.getLogger(ActividadServiceImpl.class);

    @Autowired
    private ActividadRepository actividadRepo;

    @Autowired
    private OfertanteRepository ofertanteRepo;

    @Override
    public List<Actividad> getAllActividades() {
        logger.info("Obteniendo todas las actividades desde el repositorio");
        List<Actividad> actividades = actividadRepo.findAll();
        logger.debug("Número de actividades obtenidas: {}", actividades.size());
        return actividades;
    }

    @Override
    public Actividad findActividadById(Long idActividad) {
        logger.info("Buscando actividad por ID: {}", idActividad);
        Actividad actividad = actividadRepo.findActividadByIdActividad(idActividad);
        if (actividad != null) {
            logger.debug("Actividad encontrada: {}", actividad);
        } else {
            logger.warn("No se encontró actividad con ID: {}", idActividad);
        }
        return actividad;
    }

    @Override
    public Actividad getActividadByName(String nombre) {
        logger.info("Buscando actividad por nombre: {}", nombre);
        logger.debug("Método getActividadByName no implementado. Retornando null");
        return null;
    }

    @Override
    public Actividad insertarActividad(ActividadDTO actividadDTO) {
        logger.info("Insertando nueva actividad con ActividadDTO: {}", actividadDTO);
    
        if (actividadDTO == null) {
            logger.error("El DTO de actividad es nulo.");
            throw new IllegalArgumentException("El DTO de actividad no puede ser nulo.");
        }
    
        if (getActividadByName(actividadDTO.getTitulo()) != null) {
            logger.warn("Ya existe una actividad con el título: {}", actividadDTO.getTitulo());
            throw new IllegalArgumentException("Ya existe una actividad con este título.");
        }
    
        Long idOfertante = actividadDTO.getIdOfertante();
        if (idOfertante == null) {
            logger.error("El ID del ofertante es nulo.");
            throw new IllegalArgumentException("El ID del ofertante no puede ser nulo.");
        }
    
        Ofertante ofertante = ofertanteRepo.findById(idOfertante)
                .orElseThrow(() -> {
                    logger.error("Ofertante con ID {} no encontrado.", idOfertante);
                    return new IllegalArgumentException("Ofertante con ID " + idOfertante + " no encontrado.");
                });
    
        logger.debug("Ofertante encontrado: {}", ofertante);
    
        Actividad actividad = new Actividad();
        actividad.setTitulo(actividadDTO.getTitulo());
        actividad.setDescripcion(actividadDTO.getDescripcion());
        actividad.setPrecio(actividadDTO.getPrecio());
        actividad.setTipoPago(actividadDTO.getTipoPago());
        actividad.setTipoActividad(actividadDTO.getTipoActividad());
        actividad.setEdadRequerida(actividadDTO.getEdadRequerida());
        actividad.setEstadoActividad(actividadDTO.getEstadoActividad());
        actividad.setPuntoEncuentro(actividadDTO.getPuntoEncuentro());
        actividad.setMinimoPersonas(actividadDTO.getMinimoPersonas());
        actividad.setMaximoPersonas(actividadDTO.getMaximoPersonas());
        actividad.setFechaPublicacion(actividadDTO.getFechaPublicacion());
        actividad.setFechaLimiteInscripcion(actividadDTO.getFechaLimiteInscripcion());
        actividad.setFechaRealizacion(actividadDTO.getFechaRealizacion());
        actividad.setDuracion(actividadDTO.getDuracion());
        actividad.setUbicacion(actividadDTO.getUbicacion());
        actividad.setDificultad(actividadDTO.getDificultad());
        actividad.setPoliticaCancelacion(actividadDTO.getPoliticaCancelacion());
        actividad.setMaterialNecesario(actividadDTO.getMaterialNecesario());
        actividad.setOfertante(ofertante);
    
        logger.debug("Actividad creada: {}", actividad);
    
        Actividad nuevaActividad = actividadRepo.save(actividad);
        logger.info("Actividad insertada con éxito: {}", nuevaActividad);
    
        return nuevaActividad;
    }
    

    @Override
    public Actividad actualizarActividad(Actividad actividad) {
        logger.info("Actualizando actividad: {}", actividad);
        Actividad actividadUpdated = null;
        if (actividad != null && actividad.getIdActividad() != null) {
            actividadUpdated = actividadRepo.save(actividad);
            logger.info("Actividad actualizada con éxito: {}", actividadUpdated);
        } else {
            logger.warn("Actividad no válida para actualizar: {}", actividad);
        }
        return actividadUpdated;
    }

    @Override
    public void borrarActividad(Actividad actividad) {
        logger.info("Borrando actividad: {}", actividad);
        if (actividad != null && actividad.getIdActividad() != null) {
            actividadRepo.delete(actividad);
            logger.info("Actividad borrada con éxito: {}", actividad);
        } else {
            logger.warn("Actividad no válida para borrar: {}", actividad);
        }
    }

    @Override
    public List<ActividadDTO> buscarActividadesPorTitulo(String titulo) {
        //Busca las actividades filtradas por titulo
        List<Actividad> actividades = actividadRepo.findByTituloContainingIgnoreCase(titulo);
    
        //Convierte el objeto actividad en un dto
        return actividades.stream().map(actividad -> {
            ActividadDTO dto = new ActividadDTO();
            dto.setIdActividad(actividad.getIdActividad());
            dto.setTitulo(actividad.getTitulo());
            dto.setDescripcion(actividad.getDescripcion());
            dto.setPrecio(actividad.getPrecio());
            dto.setTipoPago(actividad.getTipoPago());
            dto.setTipoActividad(actividad.getTipoActividad());
            dto.setEdadRequerida(actividad.getEdadRequerida());
            dto.setEstadoActividad(actividad.getEstadoActividad());
            dto.setPuntoEncuentro(actividad.getPuntoEncuentro());
            dto.setMinimoPersonas(actividad.getMinimoPersonas());
            dto.setMaximoPersonas(actividad.getMaximoPersonas());
            dto.setFechaPublicacion(actividad.getFechaPublicacion());
            dto.setFechaLimiteInscripcion(actividad.getFechaLimiteInscripcion());
            dto.setFechaRealizacion(actividad.getFechaRealizacion());
            dto.setDuracion(actividad.getDuracion());
            dto.setUbicacion(actividad.getUbicacion());
            dto.setDificultad(actividad.getDificultad());
            dto.setPoliticaCancelacion(actividad.getPoliticaCancelacion());
            dto.setMaterialNecesario(actividad.getMaterialNecesario());
    
            //Asgina el id del ofertante
            if (actividad.getOfertante() != null) {
                dto.setIdOfertante(actividad.getOfertante().getIdOfertante());
            }
    
            return dto;
        }).collect(Collectors.toList());
    }
    
    


}
