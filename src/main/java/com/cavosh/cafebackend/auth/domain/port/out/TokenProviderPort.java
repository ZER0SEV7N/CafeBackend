package com.cavosh.cafebackend.auth.domain.port.out;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

/**
 * Interfaz del puerto para generar y validar tokens JWT
 * Utiliza un objeto Usuario para generar el token y String para recibir el token a validar
 * Devuelve un String con el email extraído del token
 */
public interface TokenProviderPort {
    String generateToken(Usuario usuario);
    boolean validateToken(String token);
    String extractEmail(String token);
}
