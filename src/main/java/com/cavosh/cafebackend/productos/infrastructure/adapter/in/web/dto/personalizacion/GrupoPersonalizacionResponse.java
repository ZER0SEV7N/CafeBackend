package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.personalizacion;

import com.cavosh.cafebackend.productos.domain.model.GrupoPersonalizacion;

import java.util.List;

public record GrupoPersonalizacionResponse(
        Integer id,
        String nombreGrupo,
        boolean seleccionMultiple,
        boolean obligatorio,
        List<OpcionPersonalizacionResponse> opciones
) {
    public static GrupoPersonalizacionResponse from(GrupoPersonalizacion g) {
        List<OpcionPersonalizacionResponse> opciones = (g.opciones() != null)
                ? g.opciones().stream().map(OpcionPersonalizacionResponse::from).toList()
                : List.of();

        return new GrupoPersonalizacionResponse(
                g.id(),
                g.nombreGrupo(),
                g.seleccionMultiple(),
                g.obligatorio(),
                opciones
        );
    }
}