package com.example.adbenturback.controller;

import com.example.adbenturback.dto.ActividadDemandanteDTO;
import com.example.adbenturback.model.Demandante;
import com.example.adbenturback.services.ActividadDemandanteService;
import com.example.adbenturback.services.DemandanteServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ranges.RangeException;

import java.util.List;

@RestController
@RequestMapping("/api/actividadDemandante")
public class ActividadDemandanteController {

    @Autowired
    private ActividadDemandanteService actividadDemandanteService;

    @Autowired
    private DemandanteServiceImpl demandanteService;

    //Metodo para reservar actividades
    @PostMapping("/reservar")
    public ResponseEntity<String> reservarActividad(@Valid @RequestBody ActividadDemandanteDTO actividadDemandanteDTO) {
        System.out.println("Recibiendo solicitud para reservar actividad...");
        System.out.println("Datos recibidos: " + actividadDemandanteDTO);
        
        try {
            if (actividadDemandanteDTO == null) {
                return ResponseEntity.badRequest().body("Los datos de la reserva son inválidos.");
            }
    
            actividadDemandanteService.reservarActividad(actividadDemandanteDTO);
            System.out.println("Reserva realizada exitosamente.");
            
            return ResponseEntity.status(HttpStatus.CREATED).body("Reserva realizada exitosamente.");
        } catch (RangeException e) {
            System.err.println("Error de rango: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al reservar actividad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al realizar la reserva.");
        }
    }
    

    //Metodo para obtener reservas por demandante
    @GetMapping("/demandante/{idDemandante}")
    public ResponseEntity<List<ActividadDemandanteDTO>> obtenerReservasPorDemandante(@PathVariable Long idDemandante) {
        try {
            List<ActividadDemandanteDTO> reservas = actividadDemandanteService.obtenerReservasPorDemandante(idDemandante);
            return ResponseEntity.ok(reservas);
        } catch (RangeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    //Metodo para obtener la id del demandante filtrada por usuario
    @GetMapping("/usuarios/{userId}/demandante")
    public ResponseEntity<Long> obtenerIdDemandantePorUsuario(@PathVariable Long userId) {
        try {
            Demandante demandante = demandanteService.findByUsuarioId(userId);

            if (demandante != null) {
                return ResponseEntity.ok(demandante.getIdDemandante());
            } else {
                return ResponseEntity.status(404).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    //Metodo para eliminar reserva
    @DeleteMapping("/{idActividad}/{idDemandante}")
    public ResponseEntity<?> eliminarReserva(@PathVariable Long idActividad, @PathVariable Long idDemandante) {
        try {
            actividadDemandanteService.eliminarReserva(idActividad, idDemandante);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    
    //Metodo para obtener una reserva por usuario
    @GetMapping("/usuario/{idUsuario}/reservas")
    public ResponseEntity<List<ActividadDemandanteDTO>> obtenerReservasPorUsuario(@PathVariable Long idUsuario) {
        try {
            Demandante demandante = demandanteService.findByUsuarioId(idUsuario);
    
            if (demandante == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
    
            Long idDemandante = demandante.getIdDemandante();
    
            List<ActividadDemandanteDTO> reservas = actividadDemandanteService.obtenerReservasPorDemandante(idDemandante);
    
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

}
