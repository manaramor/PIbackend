package com.example.adbenturback.controller;

import com.example.adbenturback.dto.DemandanteDTO;
import com.example.adbenturback.dto.OfertanteDTO;
import com.example.adbenturback.dto.RegistroDTO;
import com.example.adbenturback.dto.UsuarioDTO;
import com.example.adbenturback.model.Demandante;
import com.example.adbenturback.model.Ofertante;
import com.example.adbenturback.model.Usuario;
import com.example.adbenturback.security.JwtTokenUtil;
import com.example.adbenturback.services.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Obtiene el usuario por id
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        logger.info("Recibida solicitud para obtener usuario con ID: {}", id);
        Usuario usuario = usuarioService.getUsuarioById(id);

        if (usuario != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setIdUsuario(usuario.getIdUsuario());
            usuarioDTO.setNombre(usuario.getNombre());
            usuarioDTO.setApellido1(usuario.getApellido1());
            usuarioDTO.setApellido2(usuario.getApellido2());
            usuarioDTO.setEmail(usuario.getEmail());
            usuarioDTO.setTelefono(usuario.getTelefono());
            usuarioDTO.setUsername(usuario.getUsername());
            usuarioDTO.setBiografia(usuario.getBiografia());
            usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
            usuarioDTO.setIdiomasHablados(usuario.getIdiomasHablados());
            usuarioDTO.setRol(usuario.getRol());
            if (usuario.getOfertante() != null) {
                usuarioDTO.setIdOfertante(usuario.getOfertante().getIdOfertante());
            }
            logger.debug("UsuarioDTO creado: {}", usuarioDTO);
            return ResponseEntity.ok(usuarioDTO);
        }

        logger.warn("Usuario no encontrado con ID: {}", id);
        return ResponseEntity.notFound().build();
    }

    //Metodo para registrar usuarios
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody RegistroDTO registroDTO) {
        try {
            UsuarioDTO usuarioDTO = registroDTO.getUsuario();
            logger.debug("Recibiendo solicitud de registro: {}", usuarioDTO);

            if (usuarioDTO.getNombre() == null || usuarioDTO.getApellido1() == null ||
                usuarioDTO.getEmail() == null || usuarioDTO.getUsername() == null ||
                usuarioDTO.getPassword() == null || usuarioDTO.getRol() == null) {
                logger.warn("Faltan campos obligatorios en la solicitud de registro");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faltan campos obligatorios");
            }

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(usuarioDTO.getNombre());
            nuevoUsuario.setApellido1(usuarioDTO.getApellido1());
            nuevoUsuario.setApellido2(usuarioDTO.getApellido2());
            nuevoUsuario.setEmail(usuarioDTO.getEmail());
            nuevoUsuario.setTelefono(usuarioDTO.getTelefono());
            nuevoUsuario.setUsername(usuarioDTO.getUsername());
            nuevoUsuario.setBiografia(usuarioDTO.getBiografia());
            nuevoUsuario.setFechaRegistro(new Date());
            nuevoUsuario.setIdiomasHablados(usuarioDTO.getIdiomasHablados());
            nuevoUsuario.setRol(usuarioDTO.getRol());

            String passwordEncriptada = passwordEncoder.encode(usuarioDTO.getPassword());
            nuevoUsuario.setPassword(passwordEncriptada);

            logger.debug("Usuario convertido y contraseña encriptada: {}", nuevoUsuario);

            if (registroDTO.getOfertante() != null) {
                OfertanteDTO ofertanteDTO = registroDTO.getOfertante();
                Ofertante ofertante = new Ofertante();
                ofertante.setExperiencia(ofertanteDTO.getExperiencia());
                ofertante.setCertificacion(ofertanteDTO.getCertificacion());
                ofertante.setValoracion(ofertanteDTO.getValoracion());
                ofertante.setUsuario(nuevoUsuario);
                nuevoUsuario.setOfertante(ofertante);
            }

            if (registroDTO.getDemandante() != null) {
                DemandanteDTO demandanteDTO = registroDTO.getDemandante();
                Demandante demandante = new Demandante();
                demandante.setTelefonoEmergencia(demandanteDTO.getTelefonoEmergencia());
                demandante.setEdad(demandanteDTO.getEdad());
                demandante.setSaldo(demandanteDTO.getSaldo());
                demandante.setUsuario(nuevoUsuario);
                nuevoUsuario.setDemandante(demandante);
            }

            Usuario usuarioCreado = usuarioService.agregarUsuario(nuevoUsuario);
            logger.debug("Usuario creado en la base de datos: {}", usuarioCreado);

            UsuarioDTO usuarioCreadoDTO = new UsuarioDTO();
            usuarioCreadoDTO.setIdUsuario(usuarioCreado.getIdUsuario());
            usuarioCreadoDTO.setNombre(usuarioCreado.getNombre());
            usuarioCreadoDTO.setApellido1(usuarioCreado.getApellido1());
            usuarioCreadoDTO.setApellido2(usuarioCreado.getApellido2());
            usuarioCreadoDTO.setEmail(usuarioCreado.getEmail());
            usuarioCreadoDTO.setTelefono(usuarioCreado.getTelefono());
            usuarioCreadoDTO.setUsername(usuarioCreado.getUsername());
            usuarioCreadoDTO.setBiografia(usuarioCreado.getBiografia());
            usuarioCreadoDTO.setFechaRegistro(usuarioCreado.getFechaRegistro());
            usuarioCreadoDTO.setIdiomasHablados(usuarioCreado.getIdiomasHablados());
            usuarioCreadoDTO.setRol(usuarioCreado.getRol());

            logger.debug("UsuarioDTO creado para la respuesta: {}", usuarioCreadoDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreadoDTO);

        } catch (ResponseStatusException e) {
            logger.error("Error al registrar el usuario: {}", e.getReason());
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado al registrar el usuario: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al registrar el usuario", e);
        }
    }

    //Actualizar usuario
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Long id, 
            @RequestBody UsuarioDTO usuarioActualizadoDTO, 
            @RequestHeader("Authorization") String authorization) {
        
        logger.debug("Token recibido en la solicitud: {}", authorization);
    
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.warn("Token no presente o incorrecto en la solicitud");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    
        String token = authorization.substring(7); 
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
        logger.debug("Usuario extraído del token: {}", usernameFromToken);
    
        Usuario usuarioExistente = usuarioService.getUsuarioById(id);
        if (usuarioExistente == null) {
            logger.warn("Usuario con ID {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    
        if (!usuarioExistente.getUsername().equals(usernameFromToken)) {
            logger.warn("El usuario autenticado no tiene permiso para editar este perfil");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    
        usuarioExistente.setNombre(usuarioActualizadoDTO.getNombre());
        usuarioExistente.setApellido1(usuarioActualizadoDTO.getApellido1());
        usuarioExistente.setApellido2(usuarioActualizadoDTO.getApellido2());
        usuarioExistente.setEmail(usuarioActualizadoDTO.getEmail());
        usuarioExistente.setBiografia(usuarioActualizadoDTO.getBiografia());
        usuarioExistente.setIdiomasHablados(usuarioActualizadoDTO.getIdiomasHablados());
        usuarioExistente.setTelefono(usuarioActualizadoDTO.getTelefono());
    
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuarioExistente);
    
        logger.info("Usuario con ID {} actualizado con éxito", id);
        return ResponseEntity.ok(usuarioActualizado);
    }
    
    
    //Metodo para borrar un usuario
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> borrarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario != null) {
            usuarioService.borrarUsuario(usuario);
            return ResponseEntity.ok("Usuario borrado con éxito.");
        }
        return ResponseEntity.notFound().build();
    }
}
