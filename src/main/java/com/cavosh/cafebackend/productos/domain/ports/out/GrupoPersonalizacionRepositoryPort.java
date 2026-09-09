package com.cavosh.cafebackend.productos.domain.ports.out;

import com.cavosh.cafebackend.productos.domain.model.GrupoPersonalizacion;

import java.util.List;
import java.util.Optional;

/** 
 * Interfaz que define las operaciones del repositorio de grupos de personalización.
 */
public interface GrupoPersonalizacionRepositoryPort {
    List<GrupoPersonalizacion> findAll();
    Optional<GrupoPersonalizacion> findById(Integer id);
    boolean existsByNombreGrupoIgnoreCase(String nombreGrupo);
    GrupoPersonalizacion save(GrupoPersonalizacion grupo);
}