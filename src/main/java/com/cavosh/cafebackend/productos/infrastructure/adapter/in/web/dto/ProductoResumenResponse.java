package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.productos.domain.model.Producto;
import java.math.BigDecimal;

public record ProductoResumenResponse(
        Integer id,
        Integer categoriaId,
        String nombre,
        String imagenUrl,
        BigDecimal precioBase,
        boolean nuevo,
        boolean frecuente
) {
    public static ProductoResumenResponse from(Producto p) {
        return new ProductoResumenResponse(
                p.id(),
                p.categoriaId(),
                p.nombre(),
                p.imagenUrl(),
                p.precioBase(),
                p.nuevo(),
                p.frecuente()
        );
    }
}