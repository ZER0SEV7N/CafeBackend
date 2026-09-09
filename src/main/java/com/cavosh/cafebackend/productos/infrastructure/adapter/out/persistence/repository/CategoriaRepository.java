package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de categorias:
 * - Listar todas las categorias activa por el orden visual
 */
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {
    List<CategoriaEntity> findByActivaTrueOrderByOrdenVisualAsc();
}