package com.cavosh.cafebackend.tienda.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.productos.ProductoResumenResponse;
import com.cavosh.cafebackend.tienda.infrastructure.adapter.in.web.dto.TiendaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Stores", description = "Endpoints para consulta y selección de cafeterías / sedes")
public interface TiendaDoc {

    @Operation(summary = "Obtener todas las cafeterías activas", description = "Retorna la lista de todas las sedes operativas.")
    @ApiResponse(responseCode = "200", description = "Lista de cafeterías obtenida correctamente")
    ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getAllStores();

    @Operation(summary = "Buscar cafeterías por ciudad", description = "Permite filtrar las sedes ingresando el nombre de una ciudad (ej. Wroclaw).")
    @ApiResponse(responseCode = "200", description = "Lista de cafeterías filtrada")
    ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getStoresByCity(@Parameter(description = "Nombre de la ciudad", example = "Wroclaw") String city);

    @Operation(summary = "Obtener la cafeterias mas frecuentadas por el usuario", 
            description = "Retorna la lista de las sedes más visitadas por el usuario autenticado (últimos 30 días, máximo 3).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cafeterías frecuentes obtenida correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getProductosFrecuentes(@Parameter(hidden = true) Usuario usuarioAuth);
}