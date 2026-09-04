package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.auth.domain.model.Rol;
import com.cavosh.cafebackend.auth.domain.model.Usuario;

public record UserProfileResponse(
        Integer id,
        String fullName,
        String email,
        Rol rol,
        Integer rewardPoints
) {
    public static UserProfileResponse from(Usuario u) {
        return new UserProfileResponse(u.id(), u.fullName(), u.email(), u.rol(), u.puntosRecompensa());
    }
}