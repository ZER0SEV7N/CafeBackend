package com.cavosh.cafebackend.productos.domain.ports.out;

import com.cavosh.cafebackend.productos.domain.model.Categoria;
import com.cavosh.cafebackend.productos.domain.model.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Producto.
 */
public interface ProductoRepositoryPort {
    //Metodo de lectura
    Optional<Producto> findById(Integer id);
    List<Producto> findAllActivo();
    List<Producto> findByCategoriaId(Integer categoriaId);
    List<Producto> findNuevo();
    List<Producto> searchProductos(String query);
    List<Producto> findFrecuentesPorUsuario(Integer usuarioId);
    List<Categoria> findAllCategorias();
    boolean existsCategoriaById(Integer categoriaId);
    boolean existsById(Integer id);

    //Metodos de escritura y eliminación
    Producto saveProducto(Producto producto, List<Integer> escalaIds, List<Integer> grupoPersonalizacionIds);
    void changeState(Integer id, boolean activo);
}
