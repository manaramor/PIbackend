package com.example.adbenturback.controller;

import com.example.adbenturback.model.LoginRequest;
import com.example.adbenturback.model.LoginResponse;
import com.example.adbenturback.model.Usuario;
import com.example.adbenturback.security.JwtTokenUtil;
import com.example.adbenturback.services.JPAUserDetailsService;
import com.example.adbenturback.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JPAUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String inputPassword = loginRequest.getPassword(); 

        logger.info("Solicitud de login recibida para el usuario: {}", username);
        logger.debug("Contraseña proporcionada para el usuario {}: {}", username, inputPassword);

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails != null) {
                String storedPasswordHash = userDetails.getPassword();
                logger.info("Usuario encontrado: {}", username);
                logger.debug("Contraseña cifrada en la base de datos para el usuario {}: {}", username, storedPasswordHash);

                //Compara la contraseña con el encoder
                boolean isPasswordMatch = passwordEncoder.matches(inputPassword, storedPasswordHash);
                logger.debug("Resultado de comparación de contraseña: {}", isPasswordMatch);

                if (isPasswordMatch) {
                    logger.info("Contraseña válida para el usuario: {}", username);

                    String token = jwtTokenUtil.generateToken(username);
                    logger.info("Token JWT generado para el usuario: {}", username);

                    Usuario usuario = usuarioService.getUsuarioByUsername(username);
                    Long userId = usuario != null ? usuario.getIdUsuario() : null;
                    String role = usuario != null ? usuario.getRol() : null;

                    if (userId == null || role == null) {
                        logger.error("ID de usuario o rol no encontrado para el usuario: {}", username);
                        return ResponseEntity.status(500).body(new LoginResponse("Internal server error", token, userId, role));
                    }

                    logger.debug("ID de usuario: {}, Rol: {}", userId, role);

                    return ResponseEntity.ok(new LoginResponse("Login successful", token, userId, role));
                } else {
                    logger.warn("Contraseña incorrecta para el usuario: {}", username);
                    return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password"));
                }
            } else {
                logger.warn("Usuario no encontrado: {}", username);
                return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password"));
            }
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud de login para el usuario: {}", username, e);
            return ResponseEntity.status(500).body(new LoginResponse("Internal server error"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout(HttpSession session) {
        session.invalidate();
        logger.info("Sesión cerrada correctamente");
        return ResponseEntity.ok(new LoginResponse("Logout successful"));
    }
}
