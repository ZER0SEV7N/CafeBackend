package com.cavosh.cafebackend.cupones.application.usecases;

import com.cavosh.cafebackend.cupones.domain.model.Cupon;
import com.cavosh.cafebackend.cupones.domain.ports.in.ValidateCuponUseCase;
import com.cavosh.cafebackend.cupones.domain.ports.out.CuponRepositoryPort;
import com.cavosh.cafebackend.global.domain.exception.BusinessRuleException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ValidateCuponUseCaseImpl implements ValidateCuponUseCase {

    private final CuponRepositoryPort cuponRepository;

    @Transactional(readOnly = true)
    public ValidateCuponResult validateAndAplicate(String codigo, BigDecimal subtotal) {
        if (codigo == null || codigo.trim().isBlank())
            throw new BusinessRuleException("Debe proporcionar un código de cupón");


        if (subtotal == null || subtotal.compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessRuleException("El subtotal del carrito debe ser mayor a 0 para aplicar un descuento");


        String codigoLimpio = codigo.trim().toUpperCase();

        Cupon cupon = cuponRepository.findByCodigoIgnoreCase(codigoLimpio)
                .orElseThrow(() -> new ResourceNotFoundException("El cupón '" + codigoLimpio + "' no existe"));

        if (!cupon.activo())
            throw new BusinessRuleException("El cupón ingresado no se encuentra activo");


        if (cupon.fechaExpiracion() != null && cupon.fechaExpiracion().isBefore(java.time.Instant.now()))
            throw new BusinessRuleException("El cupón ha expirado");


        BigDecimal montoDescuento = cupon.calcularDescuento(subtotal);
        BigDecimal totalConDescuento = subtotal.subtract(montoDescuento).max(BigDecimal.ZERO);

        return new ValidateCuponResult(
                cupon.codigo(),
                montoDescuento,
                subtotal,
                totalConDescuento,
                "Cupón aplicado correctamente"
        );
    }
}