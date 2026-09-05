package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.escala;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CrearEscalaRequest(
        @NotBlank(message = "El nombre de la escala es obligatorio (ej. Small, Medium)")
        String nombre,

        @NotNull(message = "El volumen en ml es obligatorio")
        @Positive(message = "El volumen debe ser mayor a 0")
        Integer volumenMl,

        @NotNull(message = "El recargo es obligatorio")
        @DecimalMin(value = "0.00", message = "El recargo no puede ser negativo")
        BigDecimal recargoPrecio
) {}