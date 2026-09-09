package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.domain.model.Escala;
import com.cavosh.cafebackend.productos.domain.ports.in.ManageEscalaUseCase;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.doc.EscalaDoc;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.escala.CrearEscalaRequest;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.escala.EscalaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de escalas de productos.
 * Tiene los siguientes endpoints:
 * - GET /api/escalas: Obtiene todas las escalas.
 * - POST /api/escalas: Crea una nueva escala (requiere rol ADMIN).
 * - PUT /api/escalas/{id}: Actualiza una escala existente (requiere rol ADMIN).
 */
@RestController
@RequestMapping("/api/escalas")
@RequiredArgsConstructor
public class EscalaController implements EscalaDoc {

    private final ManageEscalaUseCase escalaUseCase;

    /** 
     * Endpoint para obtener todas las escalas de productos.
     * @get : /api/escalas
     * @return la lista de escalas en formato JSON, envuelta en un ResponseGlobal.
     */
    @GetMapping
    public ResponseEntity<ResponseGlobal<List<EscalaResponse>>> getAllEscalas() {
        List<EscalaResponse> response = escalaUseCase.getAllEscalas()
                .stream()
                .map(EscalaResponse::from)
                .toList();
        return ResponseEntity.ok(ResponseGlobal.success(response, "Escalas obtenidas con éxito"));
    }

    /** 
     * Endpoint para crear una nueva escala de productos.
     * @post : /api/escalas
     * @param request - {
     *    *     "nombre": "Nombre de la escala",
     *    *     "volumenMl": 250,
     *    *     "recargoPrecio": 1.5
     * }
     * @return la escala creada en formato JSON, envuelta en un ResponseGlobal.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseGlobal<EscalaResponse>> saveEscala(@Valid @RequestBody CrearEscalaRequest request) {
        var command = new ManageEscalaUseCase.SaveEscalaCommand(request.nombre(), request.volumenMl(), request.recargoPrecio());
        Escala creada = escalaUseCase.saveEscala(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseGlobal.success(HttpStatus.CREATED.value(), EscalaResponse.from(creada), "Escala creada con éxito"));
    }

    /** 
     * Endpoint para actualizar una escala de productos existente.
     * @put : /api/escalas/{id}
     * @param id - ID de la escala a actualizar
     * @param request - {
     *    *     "nombre": "Nombre actualizado de la escala",
     *    *     "volumenMl": 300,
     *    *     "recargoPrecio": 2.0
     * }
     * @return la escala actualizada en formato JSON, envuelta en un ResponseGlobal.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseGlobal<EscalaResponse>> updateEscala(@PathVariable Integer id, @Valid @RequestBody CrearEscalaRequest request) {
        var command = new ManageEscalaUseCase.UpdateEscalaCommand(request.nombre(), request.volumenMl(), request.recargoPrecio());
        Escala actualizada = escalaUseCase.updateEscala(id, command);
        return ResponseEntity.ok(ResponseGlobal.success(EscalaResponse.from(actualizada), "Escala actualizada con éxito"));
    }
    
}