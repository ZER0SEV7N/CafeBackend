package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.auth.domain.model.TokenRecuperacion;
import com.cavosh.cafebackend.auth.domain.port.out.TokenRecuperacionRepositoryPort;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.TokenRecuperacionEntity;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.repository.TokenRecuperacionRepository;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.mapper.TokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenRecuperacionPersistenceAdapter implements TokenRecuperacionRepositoryPort {

    private final TokenRecuperacionRepository Repository;
    private final TokenMapper Mapper;

    /**
     * Metodo para guardar en la base de datos el token de recuperacion
     * @param token - Token a guardar
     * @return - Debe crear el token y retórnalo al dominio
     */
    public TokenRecuperacion save(TokenRecuperacion token) {
        TokenRecuperacionEntity entity = TokenRecuperacionEntity.builder()
                .id(token.id())
                .usuarioId(token.usuarioId())
                .token(token.token())
                .expiracion(token.expiracion())
                .usado(token.usado())
                .createdAt(token.createdAt())
                .build();

        TokenRecuperacionEntity saved = Repository.save(entity);
        return Mapper.toDomain(saved);
    }

    /**
     * Metodo para obtener el token de recuperacion mediante el token
     * @param token - Token a buscar
     * @return - Token de recuperacion encontrado o vacio
     */
    public Optional<TokenRecuperacion> findByToken(String token) {
        return Repository.findByToken(token).map(Mapper::toDomain);
    }


    /**
     * Metodo para invalidar todos los tokens de recuperación activos para un usuario específico.
     * @param usuarioId - id del usuario a buscar
     */
    public void invalidatePreviousTokens(Integer usuarioId) {
        Repository.invalidarTokensActivos(usuarioId);
    }
}