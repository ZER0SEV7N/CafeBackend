package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tokens_recuperacion", indexes = {
        @Index(name = "idx_tokens_recuperacion_token", columnList = "token", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 * Entidad para los tokens de recuperacion
 * Tiene un índice asignado para una busqueda más rapida
 */
public class TokenRecuperacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(nullable = false, unique = true, length = 100)
    private String token;

    @Column(nullable = false)
    private Instant expiracion;

    @Column(nullable = false)
    private boolean usado;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private Instant createdAt;
}
