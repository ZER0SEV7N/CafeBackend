package com.cavosh.cafebackend.productos.domain.model;

import java.math.BigDecimal;

/**
 * Representa una opción de personalización para un producto.
 * Contiene información sobre el nombre de la opción, el recargo de precio asociado y si es la opción por defecto.
 */
public record OpcionPersonalizacion (
    Integer id,
    String nombre,
    BigDecimal recargoPrecio,
    boolean porDefecto
) { }
