package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.productos.domain.model.Categoria;

public record CategoriaResponse(
        Integer id,
        String nombre,
        String iconoUrl,
        Integer ordenVisual
) {
    public static CategoriaResponse from(Categoria c) {
        return new CategoriaResponse(c.id(), c.nombre(), c.iconoUrl(), c.ordenVisual());
    }
}