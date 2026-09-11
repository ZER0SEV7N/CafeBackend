package com.cavosh.cafebackend.tienda.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.tienda.domain.port.in.GetTiendasUseCase;
import com.cavosh.cafebackend.tienda.infrastructure.adapter.in.web.dto.TiendaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints para la gestión de tiendas (cafeterías) en la aplicación.
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
        public ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getTiendasByCiudad(@PathVariable("ciudad") String ciudad) {
        List<TiendaResponse> tiendas = getTiendasUseCase.getTiendasByCiudad(ciudad).stream()
                .map(TiendaResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(tiendas, "Cafetería obtenidas con éxito"));
    }

    /**
     * Endpoint para obtener las tiendas frecuentes por el usuario.
     * @GET /api/tiendas/frecuentes
     * @param usuarioAuth el usuario autenticado.
     * @return lista con las tiendas más frecuentes del usuario.
     */
    @GetMapping("/frecuentes")
    public ResponseEntity<ResponseGlobal<List<TiendaResponse>>> getTiendasFrecuentes(@AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null) 
            return ResponseEntity.ok(ResponseGlobal.success(List.of(), "Sin tiendas frecuentes"));
        

        List<TiendaResponse> response = getTiendasUseCase.getTiendasFrecuentes(usuarioAuth.id())
                .stream()
                .map(TiendaResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Tiendas frecuentes del usuario"));
    }
}
