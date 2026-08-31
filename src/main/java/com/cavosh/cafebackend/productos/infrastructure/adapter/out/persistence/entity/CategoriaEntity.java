package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad de la categoria de un producto.
 */
@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(name = "icono_url", length = 255)
    private String iconoUrl;

    @Column(name = "orden_visual", nullable = false)
    private Integer ordenVisual;

    @Column(nullable = false)
    private boolean activa;
}
