package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * Entidad de producto
 */
@Entity
@Table(name = "producto", indexes = {
        @Index(name = "idx_producto_categoria", columnList = "categoria_id"),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "categoria_id", nullable = false)
    private Integer categoriaId;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    @Column(name = "precio_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioBase;

    @Column(name = "nuevo", nullable = false)
    private boolean nuevo;

    @Column(name = "frecuente", nullable = false)
    private boolean frecuente;

    @Column(nullable = false)
    private boolean activo;

    //Relacion con M:M con las escalas
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_escalas",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "escala_id")
    )
    @Builder.Default
    private List<EscalaEntity> escalas = new ArrayList<>();

    //Relacion M:M con los grupos de personalizacion
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_grupos",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    @Builder.Default
    private List<GrupoPersonalizacionEntity> gruposPersonalizacion = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}