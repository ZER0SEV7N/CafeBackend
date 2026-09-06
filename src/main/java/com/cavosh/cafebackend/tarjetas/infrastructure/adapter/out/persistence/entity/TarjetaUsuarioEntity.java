package com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tarjetas_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarjetaUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(nullable = false, length = 30)
    private String marca;

    @Column(name = "ultimos_cuatro", length = 4)
    private String ultimosCuatro;

    @Column(name = "numero_encriptado", length = 255)
    private String numeroEncriptado;

    @Column(nullable = false, length = 120)
    private String titular;

    @Column(nullable = false)
    private boolean predeterminado;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
