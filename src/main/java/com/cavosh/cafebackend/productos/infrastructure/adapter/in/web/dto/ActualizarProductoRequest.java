package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ActualizarProductoRequest(
        @NotNull(message = "El id de la categoría es obligatorio")
        Integer categoriaId,

        @NotBlank(message = "El nombre del producto es obligatorio")
        @Size(max = 120, message = "El nombre no puede exceder 120 caracteres")
        String nombre,

        String descripcion,
        String imagenUrl,

        @NotNull(message = "El precio base es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precioBase,

        boolean nuevo,
        boolean frecuente,
        boolean activo,

        List<Integer> escalaIds,
        List<Integer> grupoPersonalizacionIds
) {}