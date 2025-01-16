package com.example.adbenturback.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.adbenturback.dto.ActividadDTO;
import com.example.adbenturback.model.Actividad;
import com.example.adbenturback.model.Ofertante;
import com.example.adbenturback.repository.OfertanteRepository;
import com.example.adbenturback.services.ActividadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class ActividadController {

    private static final Logger logger = LoggerFactory.getLogger(ActividadController.class);

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private OfertanteRepository ofertanteRepository;


    @GetMapping("/actividades")
    public List<ActividadDTO> obtenerActividades() {
        logger.info("Recibida solicitud para obtener todas las actividades");

        List<Actividad> actividades = actividadService.getAllActividades();
        logger.debug("Número de actividades obtenidas: {}", actividades.size());

        List<ActividadDTO> actividadesDTO = actividades.stream()
                .map(actividad -> {
                    ActividadDTO actividadDTO = new ActividadDTO();

                    actividadDTO.setIdActividad(actividad.getIdActividad());
                    actividadDTO.setTitulo(actividad.getTitulo());
                    actividadDTO.setDescripcion(actividad.getDescripcion());
                    actividadDTO.setPrecio(actividad.getPrecio());
                    actividadDTO.setTipoPago(actividad.getTipoPago());
                    actividadDTO.setTipoActividad(actividad.getTipoActividad());
                    actividadDTO.setEdadRequerida(actividad.getEdadRequerida());
                    actividadDTO.setEstadoActividad(actividad.getEstadoActividad());
                    actividadDTO.setPuntoEncuentro(actividad.getPuntoEncuentro());
                    actividadDTO.setMinimoPersonas(actividad.getMinimoPersonas());
                    actividadDTO.setMaximoPersonas(actividad.getMaximoPersonas());
                    actividadDTO.setFechaPublicacion(actividad.getFechaPublicacion());
                    actividadDTO.setFechaLimiteInscripcion(actividad.getFechaLimiteInscripcion());
                    actividadDTO.setFechaRealizacion(actividad.getFechaRealizacion());
                    actividadDTO.setDuracion(actividad.getDuracion());
                    actividadDTO.setUbicacion(actividad.getUbicacion());
                    actividadDTO.setDificultad(actividad.getDificultad());
                    actividadDTO.setPoliticaCancelacion(actividad.getPoliticaCancelacion());
                    actividadDTO.setMaterialNecesario(actividad.getMaterialNecesario());
                    actividadDTO.setIdOfertante(actividad.getOfertante().getIdOfertante());

                    logger.debug("ActividadDTO creada: {}", actividadDTO);
                    return actividadDTO;
                })
                .collect(Collectors.toList());

        logger.info("Actividades encontradas: {}", actividadesDTO.size());

        return actividadesDTO;
    }

    @PostMapping("/agregarActividad")
    public ResponseEntity<Actividad> agregarActividad(@RequestBody ActividadDTO nuevaActividadDTO) {
    
        try {
            Actividad actividadCreada = actividadService.insertarActividad(nuevaActividadDTO);
            if (actividadCreada != null) {
                logger.info("Actividad creada con ID: {}", actividadCreada.getIdActividad());
                return ResponseEntity.ok(actividadCreada);
            } else {
                logger.warn("No se pudo crear la actividad: {}", nuevaActividadDTO);
                return ResponseEntity.status(400).body(null);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error al crear actividad: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        } catch (Exception e) {
            logger.error("Error inesperado al crear actividad: {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    

@PutMapping("/actualizarActividad/{id}")
public ResponseEntity<ActividadDTO> actualizarActividad(@PathVariable Long id,
        @RequestBody ActividadDTO actividadDTO) {
    logger.info("Recibida solicitud para actualizar actividad con ID: {}", id);
    logger.debug("Datos de actividad actualizada: {}", actividadDTO);

    
    if (actividadDTO.getIdOfertante() == null) {
        logger.error("El idOfertante es nulo. No se puede actualizar la actividad.");
        return ResponseEntity.badRequest().body(null);
    }

    Ofertante ofertante = ofertanteRepository.findById(actividadDTO.getIdOfertante())
            .orElse(null);
    if (ofertante == null) {
        logger.error("No se encontró el ofertante con ID: {}", actividadDTO.getIdOfertante());
        return ResponseEntity.badRequest().body(null); 
    }

    actividadDTO.setIdActividad(id);

    Actividad actividad = new Actividad();
    
    actividad.setIdActividad(actividadDTO.getIdActividad());
    actividad.setDescripcion(actividadDTO.getDescripcion());
    actividad.setDificultad(actividadDTO.getDificultad());
    actividad.setDuracion(actividadDTO.getDuracion());
    actividad.setEdadRequerida(actividadDTO.getEdadRequerida());
    actividad.setEstadoActividad(actividadDTO.getEstadoActividad());
    actividad.setFechaLimiteInscripcion(actividadDTO.getFechaLimiteInscripcion());
    actividad.setFechaPublicacion(actividadDTO.getFechaPublicacion());
    actividad.setFechaRealizacion(actividadDTO.getFechaRealizacion());
    actividad.setMaterialNecesario(actividadDTO.getMaterialNecesario());
    actividad.setMaximoPersonas(actividadDTO.getMaximoPersonas());
    actividad.setMinimoPersonas(actividadDTO.getMinimoPersonas());
    actividad.setOfertante(ofertante);
    actividad.setPoliticaCancelacion(actividadDTO.getPoliticaCancelacion());
    actividad.setPrecio(actividadDTO.getPrecio());
    actividad.setPuntoEncuentro(actividadDTO.getPuntoEncuentro());
    actividad.setTipoActividad(actividadDTO.getTipoActividad());
    actividad.setTipoPago(actividadDTO.getTipoPago());
    actividad.setTitulo(actividadDTO.getTitulo());
    actividad.setUbicacion(actividadDTO.getUbicacion());

    Actividad actividadActualizadaEntity = actividadService.actualizarActividad(actividad);
    
    if (actividadActualizadaEntity != null) {
        ActividadDTO actividadActualizadaDTO = convertirAActividadDTO(actividadActualizadaEntity);
        
        logger.info("Actividad actualizada exitosamente: {}", actividadActualizadaDTO);
        return ResponseEntity.ok(actividadActualizadaDTO);
    }

    logger.warn("Actividad con ID: {} no encontrada para actualizar", id);
    return ResponseEntity.notFound().build();
}

private ActividadDTO convertirAActividadDTO(Actividad actividad) {
    ActividadDTO actividadDTO = new ActividadDTO();
    actividadDTO.setIdActividad(actividad.getIdActividad());
    actividadDTO.setDescripcion(actividad.getDescripcion());
    actividadDTO.setDificultad(actividad.getDificultad());
    actividadDTO.setDuracion(actividad.getDuracion());
    actividadDTO.setEdadRequerida(actividad.getEdadRequerida());
    actividadDTO.setEstadoActividad(actividad.getEstadoActividad());
    actividadDTO.setFechaLimiteInscripcion(actividad.getFechaLimiteInscripcion());
    actividadDTO.setFechaPublicacion(actividad.getFechaPublicacion());
    actividadDTO.setFechaRealizacion(actividad.getFechaRealizacion());
    actividadDTO.setMaterialNecesario(actividad.getMaterialNecesario());
    actividadDTO.setMaximoPersonas(actividad.getMaximoPersonas());
    actividadDTO.setMinimoPersonas(actividad.getMinimoPersonas());
    actividadDTO.setIdOfertante(actividad.getOfertante().getIdOfertante());
    actividadDTO.setPoliticaCancelacion(actividad.getPoliticaCancelacion());
    actividadDTO.setPrecio(actividad.getPrecio());
    actividadDTO.setPuntoEncuentro(actividad.getPuntoEncuentro());
    actividadDTO.setTipoActividad(actividad.getTipoActividad());
    actividadDTO.setTipoPago(actividad.getTipoPago());
    actividadDTO.setTitulo(actividad.getTitulo());
    actividadDTO.setUbicacion(actividad.getUbicacion());
    return actividadDTO;
}



    @DeleteMapping("/borrarActividad/{id}")
    public ResponseEntity<String> borrarActividad(@PathVariable Long id) {
        logger.info("Recibida solicitud para borrar actividad con ID: {}", id);

        Actividad actividad = actividadService.findActividadById(id);
        if (actividad != null) {
            actividadService.borrarActividad(actividad);
            logger.info("Actividad borrada exitosamente: {}", actividad);
            return ResponseEntity.ok("Actividad borrada con éxito.");
        }
        logger.warn("Actividad con ID: {} no encontrada para borrar", id);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/actividadesPorTitulo")
    public ResponseEntity<List<ActividadDTO>> buscarActividadesPorTitulo(@RequestParam String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        
        List<ActividadDTO> actividades = actividadService.buscarActividadesPorTitulo(titulo);
        return ResponseEntity.ok(actividades);
    }

}
