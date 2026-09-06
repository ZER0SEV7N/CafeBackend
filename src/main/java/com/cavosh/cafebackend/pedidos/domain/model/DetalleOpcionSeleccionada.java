package com.cavosh.cafebackend.pedidos.domain.model;

import java.math.BigDecimal;

public record DetalleOpcionSeleccionada(
        Integer id,
        String nombreGrupo,
        String nombreOpcion,
        BigDecimal recargo
) {}