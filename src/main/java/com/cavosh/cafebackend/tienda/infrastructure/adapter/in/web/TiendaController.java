package com.cavosh.cafebackend.tienda.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.tienda.domain.port.in.GetTiendasUseCase;
import com.cavosh.cafebackend.tienda.infrastructure.adapter.in.web.dto.TiendaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoint para todo lo relacionado con las tiendas / sedes de la aplicación.
 * Endpoints:
 *  - GET /api/tiendas: Obtiene todas las tiendas activas.
 *  - GET /api/tiendas/buscar/{ciudad}: Obtiene las tiendas activas filtradas por ciudad.
 *  - GET /api/tiendas/frecuentes: Obtiene las tiendas activas que son frecuentemente elegidas por los usuarios.
 *  -
 */
@RestController
@RequestMapping("/api/tiendas")
@RequiredArgsConstructor
public class TiendaController {

    private final GetTiendasUseCase getTiendasUseCase;

    /**
     * Endpoint para obtener todas las tiendas activas.
     * @GET /api/tiendas
     * @return lista con las sedes operativas.
     */
    @GetMapping()
    public ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getAllTiendas() {
        List<TiendaResponse> tiendas = getTiendasUseCase.getAllActiveTiendas().stream()
                .map(TiendaResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(tiendas, "Cafeterías obtenidas con éxito"));
    }

    /**
     * Endpoint para buscar las tiendas por su ciudad.
     * @GET /api/tiendas/buscar/{ciudad}
     * @param ciudad la ciudad por la que filtrar las tiendas.
     * @return lista con las tiendas de la ciudad especificada.
     */
        @GetMapping("/buscar/{ciudad}")
        public ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getTiendasByCiudad(@PathVariable String ciudad) {
        List<TiendaResponse> tiendas = getTiendasUseCase.getTiendasByCiudad(ciudad).stream()
                .map(TiendaResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(tiendas, "Cafetería obtenidas con éxito"));
    }

    /**
     * Endpoint para obtener las tiendas que son frecuentemente elegidas por los usuarios.
     * @GET /api/tiendas/frecuentes
     * @return Retorna una lista de tiendas frecuentes.
     */
    @GetMapping("/frecuentes")
    public ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getFrequentlyChosenTiendas() {
        List<TiendaResponse> tiendas = getTiendasUseCase.getFrequentlyChosenTiendas().stream()
                .map(TiendaResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(tiendas, "Cafeterías obtenidas con éxito"));
    }
}
