package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.GrupoPersonalizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoPersonalizacionRepository extends JpaRepository<GrupoPersonalizacionEntity, Integer> {

    /**
     * Recupera los grupos de personalización junto con sus opciones en lote
     */
    List<GrupoPersonalizacionEntity> findAllByIdIn(List<Integer> ids);

    /**
     * Comprueba si existe un grupo con el mismo nombre
     */
    boolean existsByNombreGrupoIgnoreCase(String nombreGrupo);
}
