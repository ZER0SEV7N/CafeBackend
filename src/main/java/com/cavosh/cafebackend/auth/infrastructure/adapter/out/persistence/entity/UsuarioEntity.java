package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity;

import com.cavosh.cafebackend.auth.domain.model.AuthProveedor;
import com.cavosh.cafebackend.auth.domain.model.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "usuarios", indexes = {
    @Index(name = "idx_usuario_email", columnList = "correo", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/**
 * Entidad de usuario
 * para el manejo de las columnas en postgresql
 */
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fullname", nullable = false, length = 120)
    private String fullName;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Rol rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AuthProveedor proveedor;

    @Column(name ="puntos_recompensa", nullable = false)
    private Integer puntosRecompensa;

    @Column(nullable = true)
    private boolean activo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updateAt;
}
