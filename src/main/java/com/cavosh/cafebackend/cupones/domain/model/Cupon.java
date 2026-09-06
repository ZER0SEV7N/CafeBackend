package com.cavosh.cafebackend.cupones.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Cupon(
        Integer id,
        String codigo,
        BigDecimal porcentajeDescuento,
        BigDecimal montoDescuentoFijo,
        Instant fechaExpiracion,
        boolean activo
) {
    public boolean esValido() { return activo && (fechaExpiracion == null || fechaExpiracion.isAfter(Instant.now())); }

    public BigDecimal calcularDescuento(BigDecimal subtotal) {
        if (!esValido() || subtotal == null || subtotal.compareTo(BigDecimal.ZERO) <= 0)
            return BigDecimal.ZERO;

        //Si tiene descuento por monto fijo (ej. $1.20)
        if (montoDescuentoFijo != null && montoDescuentoFijo.compareTo(BigDecimal.ZERO) > 0)
            return subtotal.min(montoDescuentoFijo);

        //Si tiene descuento porcentual (ej. 15%)
        if (porcentajeDescuento != null && porcentajeDescuento.compareTo(BigDecimal.ZERO) > 0)
            return subtotal.multiply(porcentajeDescuento).divide(BigDecimal.valueOf(100));

        return BigDecimal.ZERO;
    }
}