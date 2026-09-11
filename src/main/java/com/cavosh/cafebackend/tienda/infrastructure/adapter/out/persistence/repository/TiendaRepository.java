package com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.entity.TiendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/** 
 * Repositorio de la entidad TiendaEntity que extiende JpaRepository para proporcionar operaciones CRUD y consultas personalizadas.
 */
public interface TiendaRepository extends JpaRepository<TiendaEntity, Integer> {
    List<TiendaEntity> findByActivoTrue();
    List<TiendaEntity> findByCiudadIgnoreCaseAndActivoTrue(String ciudad);

    @Query(value = "SELECT * FROM sp_obtener_tiendas_frecuentes(:usuarioId, 30, 3, 3)", nativeQuery = true)
    List<TiendaEntity> findFrecuentesPorUsuario(@Param("usuarioId") Integer usuarioId);
}
