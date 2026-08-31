package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad de las opciones de personalizacion del producto.
 */
@Entity
@Table(name = "opciones_personalizacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpcionPersonalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "grupo_id", nullable = false)
    private Integer grupoId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "recargo_precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal recargoPrecio;

    @Column(name = "por_defecto", nullable = false)
    private boolean porDefecto;
}