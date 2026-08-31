package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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
    List<ProductoEntity> findByEsNuevoTrueAndActivoTrue();
    List<ProductoEntity> findByEsFrecuenteTrueAndActivoTrue();
}