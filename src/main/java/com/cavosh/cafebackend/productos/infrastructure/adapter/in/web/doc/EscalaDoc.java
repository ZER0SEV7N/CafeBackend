package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.escala.CrearEscalaRequest;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.escala.EscalaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Escalas (Tamaños)", description = "Catálogo maestro de tamaños de bebidas y recargos")
public interface EscalaDoc {

    @Operation(summary = "Listar todas las escalas", description = "Retorna los tamaños disponibles para personalizar productos.")
    ResponseEntity<ResponseGlobal<List<EscalaResponse>>> getAllEscalas();

    @Operation(summary = "Crear nueva escala", description = "Requiere rol ADMIN. Registra un tamaño base (ej. Small, Medium, Large).")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Escala creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (Requiere ADMIN)"),
            @ApiResponse(responseCode = "409", description = "La escala ya existe")
    })
    ResponseEntity<ResponseGlobal<EscalaResponse>> saveEscala(CrearEscalaRequest request);

    @Operation(summary = "Actualizar escala", description = "Requiere rol ADMIN. Modifica volumen o recargo.")
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<ResponseGlobal<EscalaResponse>> updateEscala(Integer id, CrearEscalaRequest request);
}