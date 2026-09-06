package com.cavosh.cafebackend.favoritos.infrastructure.adapter.out.persitence.entity;

import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "favoritos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoritoEntity {

    @EmbeddedId
    private FavoritoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productoId")
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductoEntity producto;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}