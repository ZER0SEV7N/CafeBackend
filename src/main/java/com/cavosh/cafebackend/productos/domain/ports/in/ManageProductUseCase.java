package com.cavosh.cafebackend.productos.domain.ports.in;

import com.cavosh.cafebackend.productos.domain.model.Producto;

import java.math.BigDecimal;
import java.util.List;

public interface ManageProductUseCase {
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

