package com.cavosh.cafebackend.auth.domain.port.out;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

public interface TokenProviderPort {
    String generateToken(Usuario usuario);
    boolean validateToken(String token);
    String extractEmail(String token);
}
