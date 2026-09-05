package com.cavosh.cafebackend.favoritos.domain.model;

import java.time.Instant;

public record Favorito(
        Integer usuarioId,
        Integer productoId,
        Instant createdAt
) { }
