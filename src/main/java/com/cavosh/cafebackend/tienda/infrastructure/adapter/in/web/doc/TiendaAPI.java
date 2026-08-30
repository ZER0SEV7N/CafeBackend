package com.cavosh.cafebackend.tienda.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.tienda.infrastructure.adapter.in.web.dto.TiendaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Stores", description = "Endpoints para consulta y selección de cafeterías / sedes")
public interface TiendaAPI {

    @Operation(summary = "Obtener todas las cafeterías activas", description = "Retorna la lista de todas las sedes operativas.")
    @ApiResponse(responseCode = "200", description = "Lista de cafeterías obtenida correctamente")
    ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getAllStores();

    @Operation(summary = "Buscar cafeterías por ciudad", description = "Permite filtrar las sedes ingresando el nombre de una ciudad (ej. Wroclaw).")
    @ApiResponse(responseCode = "200", description = "Lista de cafeterías filtrada")
    ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getStoresByCity(
            @Parameter(description = "Nombre de la ciudad", example = "Wroclaw") String city
    );

    @Operation(summary = "Obtener cafeterías frecuentemente elegidas", description = "Retorna las sedes marcadas como populares/frecuentes.")
    @ApiResponse(responseCode = "200", description = "Lista de cafeterías frecuentes")
    ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getFrequentlyChosenStores();
}