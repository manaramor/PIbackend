package com.example.adbenturback.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private final String secretKey = "EstaCadenaDeberiaTenerMasDe64BytesParaSerSeguraConHS5121234567890ABCDE";

    public String generateToken(String username) {
        logger.info("Generando token JWT para el usuario: {}", username);
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key, SignatureAlgorithm.HS512) 
                .compact();

        logger.debug("Token JWT generado: {}", token);
        return token;
    }

    public String getUsernameFromToken(String token) {
        logger.debug("Extrayendo username del token JWT");
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token) {
        logger.debug("Extrayendo roles del token JWT");
        return extractAllClaims(token).get("roles", List.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        logger.debug("Extrayendo una reclamación específica del token JWT");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            logger.debug("Claims extraídos del token JWT: {}", claims);
            return claims;
        } catch (Exception e) {
            logger.error("Error al extraer claims del token JWT: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isTokenExpired(String token) {
        logger.debug("Verificando si el token JWT ha expirado");
        Date expiration = extractExpiration(token);
        boolean expired = expiration.before(new Date());
        logger.debug("Token expirado: {}", expired);
        return expired;
    }

    public Date extractExpiration(String token) {
        logger.debug("Extrayendo fecha de expiración del token JWT");
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        logger.debug("Validando token JWT para el usuario: {}", userDetails.getUsername());
        final String username = getUsernameFromToken(token);
        logger.debug("Username extraído del token: {}", username);
        boolean isValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        logger.debug("Token válido: {}", isValid);
        return isValid;
    }

    public boolean validateToken(String token) {
        logger.debug("Validando token JWT sin detalles de usuario");
        boolean isValid = !isTokenExpired(token);
        logger.debug("Token válido (sin usuario): {}", isValid);
        return isValid;
    }
}
