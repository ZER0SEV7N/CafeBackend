package com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cupones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CuponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "porcentaje_descuento", precision = 5, scale = 2)
    private BigDecimal porcentajeDescuento;

    @Column(name = "monto_descuento_fijo", precision = 10, scale = 2)
    private BigDecimal montoDescuentoFijo;

    @Column(name = "fecha_expiracion")
    private Instant fechaExpiracion;

    @Column(nullable = false)
    private boolean activo;
}
