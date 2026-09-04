package com.cavosh.cafebackend.auth.domain.port.out;

import com.cavosh.cafebackend.auth.domain.model.TokenRecuperacion;

import java.util.Optional;

public interface TokenRecuperacionRepositoryPort {
    TokenRecuperacion save(TokenRecuperacion tokenRecuperacion);
    Optional<TokenRecuperacion> findByToken(String token);
    void invalidatePreviousTokens(Integer usuarioId);
}
