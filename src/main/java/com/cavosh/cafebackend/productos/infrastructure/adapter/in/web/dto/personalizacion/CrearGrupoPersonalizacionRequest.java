package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.personalizacion;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CrearGrupoPersonalizacionRequest(
        @NotBlank(message = "El nombre del grupo es obligatorio (ej. Milk, Caffeine)")
        String nombreGrupo,

        boolean seleccionMultiple,
        boolean obligatorio,

        @NotEmpty(message = "Debe incluir al menos una opción para este grupo")
        @Valid
        List<OpcionItemRequest> opciones
) {
    public record OpcionItemRequest(
            @NotBlank(message = "El nombre de la opción es obligatorio (ej. Oat milk)")
            String nombre,

            @NotNull(message = "El recargo de la opción es obligatorio")
            @DecimalMin(value = "0.00", message = "El recargo no puede ser negativo")
            BigDecimal recargoPrecio,

            boolean porDefecto
    ) {}
}