package com.cavosh.cafebackend.favoritos.infrastructure.adapter.out.persitence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoritoId implements Serializable {

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "producto_id")
    private Integer productoId;

}
