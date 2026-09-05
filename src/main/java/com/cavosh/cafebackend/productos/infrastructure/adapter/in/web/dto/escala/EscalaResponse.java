package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.escala;

import com.cavosh.cafebackend.productos.domain.model.Escala;
import java.math.BigDecimal;

public record EscalaResponse(
        Integer id,
        String nombre,
        Integer volumenMl,
        BigDecimal recargoPrecio
) {
    public static EscalaResponse from(Escala e) {
        return new EscalaResponse(e.id(), e.nombre(), e.volumenMl(), e.recargoPrecio());
    }
}