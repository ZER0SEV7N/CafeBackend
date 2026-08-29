package com.cavosh.cafebackend.auth.domain.port.out;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

public interface TokenProviderPort {
    String generarToken(Usuario usuario);
    boolean validarToken(String token);
    String getEmailFromToken(String token);
}
