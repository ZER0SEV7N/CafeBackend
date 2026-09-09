package com.cavosh.cafebackend.productos.domain.ports.out;

import com.cavosh.cafebackend.productos.domain.model.Escala;

import java.util.List;
import java.util.Optional;

/** 
 * Interfaz que define las operaciones del repositorio de escalas.
 */
public interface EscalaRepositoryPort {
    List<Escala> findAll();
    Optional<Escala> findById(Integer id);
    boolean existsByNombreIgnoreCase(String nombre);
    Escala save(Escala escala);
}