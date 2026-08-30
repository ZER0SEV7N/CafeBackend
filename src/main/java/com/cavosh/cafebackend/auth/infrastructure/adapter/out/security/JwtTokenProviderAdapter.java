package com.cavosh.cafebackend.auth.infrastructure.adapter.out.security;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.out.TokenProviderPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

/**
 * Componente que implementa la interfaz TokenProviderPort para generar y validar tokens JWT.
 * Utiliza la biblioteca jjwt para crear y verificar tokens JWT.
 * El token se genera con el email del usuario como sujeto y
 * contiene reclamos adicionales como el ID del usuario, el nombre completo y el rol.
 * La validez del token se determina mediante la verificación de la firma y la fecha de expiración.
 * El email del usuario se puede extraer del token decodificado.
 * La clase utiliza una clave secreta para firmar y verificar los tokens, que se inyecta a través de las propiedades de configuración de la aplicación.
 */
@Component
public class JwtTokenProviderAdapter implements TokenProviderPort {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtTokenProviderAdapter(@Value("${jwt.secret}") String secret,  @Value("${jwt.expiration}") long expirationHours) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expirationHours;
    }

    /**
     * Metodo para generar un token JWT para un usuario dado.
     * @param usuario - Se proporciona un usuario
     * @return Devuelve un token JWT firmado que contiene el email del usuario como sujeto y reclamos adicionales.
     */
    public String generateToken(Usuario usuario) {
        Instant ahora = Instant.now();
        Instant expiracion = ahora.plus(expiration, ChronoUnit.HOURS);

        return Jwts.builder()
                .subject(usuario.email())
                .claims(Map.of(
                        "uid", usuario.id(),
                        "fullname", usuario.fullName(),
                        "rol", usuario.rol().name()
                )).issuedAt(Date.from(ahora)).expiration(Date.from(expiracion))
                .signWith(secretKey).compact();
    }

    /**
     * Metodo para validar un token JWT dado.
     * @param token - Token proporcionado por el bearer
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Metodo para extraer el correo de la firma del token
     * @param token - Token proporcionado por el bearer
     * @return El correo del usuario si el token es válido, null en caso contrario.
     */
    public String extractEmail(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

}
