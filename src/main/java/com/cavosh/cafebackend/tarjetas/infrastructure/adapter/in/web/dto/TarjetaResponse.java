package com.cavosh.cafebackend.tarjetas.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.tarjetas.domain.model.TarjetaUsuario;

public record TarjetaResponse(
        Integer id,
        String marca,
        String ultimosCuatro,
        String numeroEnmascarado,
        String titular,
        boolean predeterminado
) {
    public static TarjetaResponse from(TarjetaUsuario t) {
        return new TarjetaResponse(
                t.id(),
                t.marca(),
                t.ultimosCuatro(),
                t.getNumeroEnmascarado(),
                t.titular(),
                t.predeterminado()
        );
    }
}