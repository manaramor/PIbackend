package com.example.adbenturback.security;

import com.example.adbenturback.services.JPAUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtTokenUtil jwtTokenUtil;
    private final JPAUserDetailsService userDetailsService;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, JPAUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        logger.debug("JwtRequestFilter instanciado");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        logger.debug("Procesando filtro JWT para la solicitud: {}", requestURI);

        if (requestURI.equals("/api/registrar") || requestURI.equals("/api/login")) {
            logger.debug("Solicitud excluida del filtro JWT: {}", requestURI);
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = extractToken(request);
        logger.debug("Token JWT extraído: {}", jwtToken);

        if (jwtToken != null && jwtTokenUtil.validateToken(jwtToken)) {
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            logger.debug("Usuario extraído del token JWT: {}", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                logger.debug("Detalles del usuario cargados: {}", userDetails);

                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) { // Usar el método con UserDetails
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("Autenticación establecida para el usuario: {}", username);
                } else {
                    logger.warn("Token JWT inválido para el usuario: {}", username);
                }
            }
        } else {
            if (jwtToken == null) {
                logger.debug("No se encontró token JWT en la solicitud");
            } else {
                logger.warn("Token JWT no válido o expirado");
            }
        }

        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        logger.debug("Encabezado Authorization: {}", authHeader);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); 
            logger.debug("Token JWT extraído: {}", token);
            return token;
        }
        logger.debug("No se encontró el prefijo Bearer en el encabezado Authorization");
        return null;
    }
}
