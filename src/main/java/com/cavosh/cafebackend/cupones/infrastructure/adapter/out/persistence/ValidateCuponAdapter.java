package com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.cavosh.cafebackend.cupones.domain.ports.in.ValidateCuponUseCase;
import com.cavosh.cafebackend.cupones.domain.ports.out.ValidateCuponPort;

import lombok.RequiredArgsConstructor;

/**
 * Adapter que implementa la interfaz ValidateCuponPort para validar y aplicar cupones de descuento.
 */
@Component
@RequiredArgsConstructor
public class ValidateCuponAdapter implements ValidateCuponPort {
    
    private final ValidateCuponUseCase validateCuponUseCase;
    
    /** 
     * Calcula el descuento aplicado a un subtotal utilizando un código de cupón.
     * @param codigoCupon El código del cupón a validar y aplicar.
     * @param subtotal El monto total de la compra.
     * @return El monto del descuento aplicado, o BigDecimal.ZERO si no se aplica ningún descuento.
     */
    public BigDecimal calculateDiscount(String codigoCupon, BigDecimal subtotal){
        if (codigoCupon == null || codigoCupon.trim().isBlank()) 
            return BigDecimal.ZERO;
        
        var resultado = validateCuponUseCase.validateAndAplicate(codigoCupon.trim(), subtotal);
        return resultado.descuento();
    }
}
