package com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.entity.TiendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** 
 * Repositorio de la entidad TiendaEntity que extiende JpaRepository para proporcionar operaciones CRUD y consultas personalizadas.
 */
public interface TiendaRepository extends JpaRepository<TiendaEntity, Integer> {
    List<TiendaEntity> findByActivoTrue();
    List<TiendaEntity> findByCiudadIgnoreCaseAndActivoTrue(String ciudad);
    List<TiendaEntity> findByFrecuenteTrueAndActivoTrue();
}
