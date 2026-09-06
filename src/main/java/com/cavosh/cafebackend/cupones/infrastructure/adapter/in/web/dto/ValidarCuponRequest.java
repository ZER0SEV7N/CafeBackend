package com.cavosh.cafebackend.cupones.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ValidarCuponRequest(
        @NotBlank(message = "El código de cupón es obligatorio")
        String codigo,

        @NotNull(message = "El subtotal es obligatorio")
        @DecimalMin(value = "0.01", message = "El subtotal debe ser mayor a 0")
        BigDecimal subtotal
) {}