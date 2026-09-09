package com.cavosh.cafebackend.productos.domain.ports.in;

import com.cavosh.cafebackend.productos.domain.model.Categoria;
import com.cavosh.cafebackend.productos.domain.model.Producto;

import java.util.List;

/** 
 * Interfaz que define las operaciones de obtención de productos.
 */
public interface GetProductosUseCase {
    List<Producto> getAllActivoProductos();
    List<Producto> getProductosByCategoria(Integer categoryId);
    List<Producto> getNuevoInProductos();
    List<Producto> getFrequenciaOrdernadosProductos();
    Producto getProductoById(Integer id);
    List<Categoria> getCategorias();
}
