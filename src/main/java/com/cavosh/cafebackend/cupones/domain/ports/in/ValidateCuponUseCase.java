package com.cavosh.cafebackend.cupones.domain.ports.in;

import java.math.BigDecimal;
public interface ValidateCuponUseCase {

    record ValidateCuponResult(
            String codigo,
            BigDecimal descuento,
            BigDecimal subtotalOriginal,
            BigDecimal totalConDescuento,
            String mensaje
    ) {}

    ValidateCuponResult validateAndAplicate(String codigo, BigDecimal subtotal);
}
