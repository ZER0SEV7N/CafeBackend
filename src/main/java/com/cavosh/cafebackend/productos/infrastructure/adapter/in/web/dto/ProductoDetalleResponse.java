package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.productos.domain.model.Producto;
import java.math.BigDecimal;
import java.util.List;

public record ProductoDetalleResponse(
        Integer id,
        Integer categoriaId,
        String nombre,
        String descripcion,
        String imagenUrl,
        BigDecimal precioBase,
        boolean esNuevo,
        boolean esFrecuente,
        List<EscalaResponse> escalas,
        List<GrupoPersonalizacionResponse> gruposPersonalizacion
) {
    public static ProductoDetalleResponse from(Producto p) {
        List<EscalaResponse> escalas = (p.escalas() != null)
                ? p.escalas().stream().map(EscalaResponse::from).toList()
                : List.of();

        List<GrupoPersonalizacionResponse> grupos = (p.gruposPersonalizacion() != null)
                ? p.gruposPersonalizacion().stream().map(GrupoPersonalizacionResponse::from).toList()
                : List.of();

        return new ProductoDetalleResponse(
                p.id(),
                p.categoriaId(),
                p.nombre(),
                p.descripcion(),
                p.imagenUrl(),
                p.precioBase(),
                p.esNuevo(),
                p.esFrecuente(),
                escalas,
                grupos
        );
    }
}