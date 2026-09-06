package com.cavosh.cafebackend.cupones.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.cupones.domain.ports.in.ValidateCuponUseCase;

import java.math.BigDecimal;

public record CuponValidadoResponse(
        String codigo,
        BigDecimal descuento,
        BigDecimal subtotal,
        BigDecimal totalConDescuento,
        String mensaje
) {
    public static CuponValidadoResponse from(ValidateCuponUseCase.ValidateCuponResult r) {
        return new CuponValidadoResponse(
                r.codigo(),
                r.descuento(),
                r.subtotalOriginal(),
                r.totalConDescuento(),
                r.mensaje()
        );
    }
}