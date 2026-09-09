package com.cavosh.cafebackend.tarjetas.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.in.web.dto.RegistrarTarjetaRequest;
import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.in.web.dto.TarjetaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Tarjetas", description = "Gestión de tarjetas bancarias asociadas al cliente")
@SecurityRequirement(name = "bearerAuth")
public interface TarjetaDoc {

    @Operation(summary = "Listar tarjetas guardadas", description = "Retorna los métodos de pago enmascarados del usuario autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjetas obtenidas exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    ResponseEntity<ResponseGlobal<List<TarjetaResponse>>> listTarjetas(
            @Parameter(hidden = true) Usuario usuarioAuth
    );

    @Operation(summary = "Registrar nueva tarjeta", description = "Cifra el número con AES-256-GCM y almacena la tarjeta para el checkout.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarjeta registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de tarjeta no válidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    ResponseEntity<ResponseGlobal<TarjetaResponse>> registerTarjeta(
            @Parameter(hidden = true) Usuario usuarioAuth,
            RegistrarTarjetaRequest request
    );

    @Operation(summary = "Establecer tarjeta predeterminada", description = "Marca la tarjeta seleccionada como principal para cobros automáticos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjeta marcada como predeterminada"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada")
    })
    ResponseEntity<ResponseGlobal<Void>> checkDefaults(
            @Parameter(hidden = true) Usuario usuarioAuth,
            @Parameter(description = "ID de la tarjeta") Integer id
    );

    @Operation(summary = "Eliminar tarjeta", description = "Remueve la tarjeta bancaria de la cuenta del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjeta eliminada exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada")
    })
    ResponseEntity<ResponseGlobal<Void>> deleteTarjeta(
            @Parameter(hidden = true) Usuario usuarioAuth,
            @Parameter(description = "ID de la tarjeta") Integer id
    );
}