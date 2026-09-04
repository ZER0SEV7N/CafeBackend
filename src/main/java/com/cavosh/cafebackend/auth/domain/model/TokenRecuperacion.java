package com.cavosh.cafebackend.auth.domain.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public record TokenRecuperacion(
        Integer id,
        Integer usuarioId,
        String token,
        Instant expiracion,
        boolean usado,
        Instant createdAt
) {
    public static TokenRecuperacion crear(Integer usuarioId, long minutosValidez) {
        Instant ahora = Instant.now();
        return new TokenRecuperacion(
                null,
                usuarioId,
                UUID.randomUUID().toString(),
                ahora.plus(minutosValidez, ChronoUnit.MINUTES),
                false,
                ahora
        );
    }

    public boolean esValido() { return !usado && Instant.now().isBefore(expiracion); }
}