package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad del grupo de Personalizacion
 */
@Entity
@Table(name = "grupo_personalizacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoPersonalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_grupo", nullable = false, length = 80)
    private String nombreGrupo;

    @Column(name = "seleccion_multiple", nullable = false)
    private boolean seleccionMultiple;

    @Column(nullable = false)
    private boolean obligatorio;

    //Relacion 1:M con las opciones de personalizacion
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "grupo_id")
    @Builder.Default
    private List<OpcionPersonalizacionEntity> opciones = new ArrayList<>();
}