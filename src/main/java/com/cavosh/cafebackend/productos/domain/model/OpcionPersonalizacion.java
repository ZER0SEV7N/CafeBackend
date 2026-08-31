package com.cavosh.cafebackend.productos.domain.model;

import java.math.BigDecimal;

public record OpcionPersonalizacion (
    Integer id,
    String nombre,
    BigDecimal recargoPrecio,
    boolean porDefecto
) { }
