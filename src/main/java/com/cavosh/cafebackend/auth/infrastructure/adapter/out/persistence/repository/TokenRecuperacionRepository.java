package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.TokenRecuperacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRecuperacionRepository extends JpaRepository<TokenRecuperacionEntity, Integer> {

    Optional<TokenRecuperacionEntity> findByToken(String token);

    /**
     * Invalida todos los tokens de recuperación activos para un usuario específico.
     * @param usuarioId - Id del usuario
     */
    @Modifying
    @Query("UPDATE TokenRecuperacionEntity t SET t.usado = true WHERE t.usuarioId = :usuarioId AND t.usado = false")
    void invalidarTokensActivos(Integer usuarioId);
}