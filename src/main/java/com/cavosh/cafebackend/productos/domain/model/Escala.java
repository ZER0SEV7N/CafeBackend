package com.cavosh.cafebackend.productos.domain.model;

import java.math.BigDecimal;

/**
 * Modelo de las escalas de tamaño
 * @param id - id de la escala
 * @param nombre - nombre de la escala (pequeño, mediano, grande)
 * @param volumenMl - volumen en mililitros de la escala
 * @param recargoPrecio - Precio a aumentar al precio base del producto si se selecciona esta escala
 */
public record Escala(
    Integer id,
    String nombre,
    Integer volumenMl,
    BigDecimal recargoPrecio
) { }
