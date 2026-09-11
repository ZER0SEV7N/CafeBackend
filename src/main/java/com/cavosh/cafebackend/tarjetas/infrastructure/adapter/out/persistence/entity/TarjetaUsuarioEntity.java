package com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/** 
 * Entidad que representa una tarjeta de usuario en la base de datos.
 */
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

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "ultimos_cuatro", length = 4, nullable = false)
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
