package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de productos:
 * - Listar todos los productos activos
 * - Listar todos los productos de una categoría específica y activos
 * - Listar todos los productos nuevos y activos
 * - Listar todos los productos frecuentes y activos
 */
@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {
    List<ProductoEntity> findByActivoTrue();
    List<ProductoEntity> findByCategoriaIdAndActivoTrue(Integer categoriaId);
    List<ProductoEntity> findByNuevoTrueAndActivoTrue();
    List<ProductoEntity> findByFrecuenteTrueAndActivoTrue();
    List<ProductoEntity> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
    @Modifying
    @Query("UPDATE ProductoEntity p SET p.activo = :activo, p.updatedAt = CURRENT_TIMESTAMP WHERE p.id = :id")
    int updateActivoById(Integer id, boolean activo);
}