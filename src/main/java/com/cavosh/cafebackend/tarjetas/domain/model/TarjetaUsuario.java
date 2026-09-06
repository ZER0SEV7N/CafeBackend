package com.cavosh.cafebackend.tarjetas.domain.model;

import java.time.Instant;

public record TarjetaUsuario(
        Integer id,
        Integer usuarioId,
        String marca,
        String ultimosCuatro,
        String numeroEncriptado,
        String titular,
        boolean predeterminado,
        Instant createdAt
) {
    public String getNumeroEnmascarado() {
        return "**** " + this.ultimosCuatro;
    }
}