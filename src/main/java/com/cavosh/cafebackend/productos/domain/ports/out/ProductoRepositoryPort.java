package com.cavosh.cafebackend.productos.domain.ports.out;

import com.cavosh.cafebackend.productos.domain.model.Categoria;
import com.cavosh.cafebackend.productos.domain.model.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Producto.
 */
public interface ProductoRepositoryPort {
    Optional<Producto> findById(Integer id);
    List<Producto> findAllActivo();
    List<Producto> findByCategoriaId(Integer categoriaId);
    List<Producto> findNuevo();
    List<Producto> findFrecuenteOrdenado();
    List<Categoria> findAllCategorias();
}
