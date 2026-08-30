package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.auth.domain.model.Rol;
import com.cavosh.cafebackend.auth.domain.model.Usuario;

public record AuthResponse (
        String token,
        Integer id,
        String fullName,
        String email,
        Rol role,
        Integer rewardPoints
) {
    public static AuthResponse from(String token, Usuario usuario) {
        return new AuthResponse(
                token,
                usuario.id(),
                usuario.fullName(),
                usuario.email(),
                usuario.rol(),
                usuario.puntosRecompensa()
        );
    }
}