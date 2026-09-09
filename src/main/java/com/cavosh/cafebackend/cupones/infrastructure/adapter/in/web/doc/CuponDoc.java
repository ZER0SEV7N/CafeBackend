package com.cavosh.cafebackend.cupones.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.cupones.infrastructure.adapter.in.web.dto.CuponValidadoResponse;
import com.cavosh.cafebackend.cupones.infrastructure.adapter.in.web.dto.ValidarCuponRequest;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cupones", description = "Validación y aplicación de cupones de descuento para el carrito de compras")
public interface CuponDoc {

    @Operation(
            summary = "Validar cupón promocional (Botón Apply)",
            description = "Valida si un cupón existe y está vigente, calculando el importe del descuento y el total resultante sobre el subtotal del carrito."
    )
    @ApiResponses( value ={
            @ApiResponse(responseCode = "200", description = "Cupón válido y aplicado"),
            @ApiResponse(responseCode = "400", description = "Cupón inactivo, expirado o subtotal incorrecto"),
            @ApiResponse(responseCode = "404", description = "El código de cupón no existe")
    })
    ResponseEntity<ResponseGlobal<CuponValidadoResponse>> validarCupon(ValidarCuponRequest request);
}