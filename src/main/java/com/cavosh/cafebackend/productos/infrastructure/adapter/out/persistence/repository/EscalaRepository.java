package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.EscalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscalaRepository extends JpaRepository<EscalaEntity, Integer> {

    /**
     * Recupera las entidades de escala según los ID enviados desde el request
     */
    List<EscalaEntity> findAllByIdIn(List<Integer> ids);

    /**
     * Comprueba si una escala existe por nombre (evita duplicados)
     */
    boolean existsByNombreIgnoreCase(String nombre);

    List<EscalaEntity> findAllByOrderByVolumenMlAsc();
}
