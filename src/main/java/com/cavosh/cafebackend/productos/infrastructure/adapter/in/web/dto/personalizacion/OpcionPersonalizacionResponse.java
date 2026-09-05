package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.personalizacion;

import com.cavosh.cafebackend.productos.domain.model.OpcionPersonalizacion;
import java.math.BigDecimal;

public record OpcionPersonalizacionResponse(
        Integer id,
        String nombre,
        BigDecimal recargoPrecio,
        boolean porDefecto
) {
    public static OpcionPersonalizacionResponse from(OpcionPersonalizacion op) {
        return new OpcionPersonalizacionResponse(op.id(), op.nombre(), op.recargoPrecio(), op.porDefecto());
    }
}