package com.cavosh.cafebackend.productos.domain.ports.in;

import com.cavosh.cafebackend.productos.domain.model.Producto;

import java.math.BigDecimal;
import java.util.List;

/** 
 * Interfaz que define las operaciones de gestión de productos.
 */
public interface ManageProductUseCase {
    //Command para crear un producto
    record SaveProductoCommand(
            Integer categoriaId,
            String nombre,
            String descripcion,
            String imagenUrl,
            BigDecimal precioBase,
            boolean nuevo,
            boolean frecuente,
            List<Integer> escalaIds,
            List<Integer> grupoPersonalizacionIds
    ) {}

    //Command para actualizar un producto
    record UpdateProductoCommand(
            Integer categoriaId,
            String nombre,
            String descripcion,
            String imagenUrl,
            BigDecimal precioBase,
            boolean nuevo,
            boolean frecuente,
            boolean activo,
            List<Integer> escalaIds,
            List<Integer> grupoPersonalizacionIds
    ) {}

    Producto saveProducto(SaveProductoCommand command);

    Producto updateProducto(Integer id, UpdateProductoCommand command);

    void changeState(Integer id, boolean activo);
}

