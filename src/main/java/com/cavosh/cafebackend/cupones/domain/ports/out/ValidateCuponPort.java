package com.cavosh.cafebackend.cupones.domain.ports.out;

import java.math.BigDecimal;

public interface ValidateCuponPort {
    BigDecimal calculateDiscount(String codigoCupon, BigDecimal subtotal);
}
