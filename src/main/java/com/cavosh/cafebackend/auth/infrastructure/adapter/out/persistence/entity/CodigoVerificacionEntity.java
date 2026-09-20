package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "codigos_verificacion")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder    
public class CodigoVerificacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "codigo", length = 4, nullable = false)
    private String codigo;

    @Column(name = "intentos_fallidos")
    private Integer intentosFallidos;

    @Column(name = "max_intentos")
    private Integer maxIntentos;

    @Column(name = "expira_en", nullable = false)
    private LocalDateTime expiraEn;

    @Column(name = "usado")
    private Boolean usado;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    
}
