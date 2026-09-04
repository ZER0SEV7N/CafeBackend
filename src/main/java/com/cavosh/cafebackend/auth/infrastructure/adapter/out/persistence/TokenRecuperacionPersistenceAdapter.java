package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.auth.domain.model.TokenRecuperacion;
import com.cavosh.cafebackend.auth.domain.port.out.TokenRecuperacionRepositoryPort;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.TokenRecuperacionEntity;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.repository.TokenRecuperacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenRecuperacionPersistenceAdapter implements TokenRecuperacionRepositoryPort {

    private final TokenRecuperacionRepository Repository;

    @Override
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
        return toDomain(saved);
    }

    public Optional<TokenRecuperacion> findByToken(String token) {
        return Repository.findByToken(token).map(this::toDomain);
    }


    public void invalidatePreviousTokens(Integer usuarioId) {
        Repository.invalidarTokensActivos(usuarioId);
    }

    private TokenRecuperacion toDomain(TokenRecuperacionEntity entity) {
        return new TokenRecuperacion(
                entity.getId(),
                entity.getUsuarioId(),
                entity.getToken(),
                entity.getExpiracion(),
                entity.isUsado(),
                entity.getCreatedAt()
        );
    }
}