package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entidad de la escala de tamaño de un producto
 */
@Entity
@Table(name = "escala")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscalaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "volumen_ml", nullable = false)
    private Integer volumenMl;

    @Column(name = "recargo_precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal recargoPrecio;
}
