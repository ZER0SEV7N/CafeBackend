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
 * Controlador Rest para manejar las opciones de escala de los productos.
 */
@RestController
@RequestMapping("/api/escalas")
@RequiredArgsConstructor
public class EscalaController implements EscalaDoc {

    private final ManageEscalaUseCase escalaUseCase;

    @GetMapping
    public ResponseEntity<ResponseGlobal<List<EscalaResponse>>> getAllEscalas() {
        List<EscalaResponse> response = escalaUseCase.getAllEscalas()
                .stream()
                .map(EscalaResponse::from)
                .toList();
        return ResponseEntity.ok(ResponseGlobal.success(response, "Escalas obtenidas con éxito"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseGlobal<EscalaResponse>> saveEscala(@Valid @RequestBody CrearEscalaRequest request) {
        var command = new ManageEscalaUseCase.SaveEscalaCommand(request.nombre(), request.volumenMl(), request.recargoPrecio());
        Escala creada = escalaUseCase.saveEscala(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseGlobal.success(HttpStatus.CREATED.value(), EscalaResponse.from(creada), "Escala creada con éxito"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseGlobal<EscalaResponse>> updateEscala(@PathVariable Integer id, @Valid @RequestBody CrearEscalaRequest request) {
        var command = new ManageEscalaUseCase.UpdateEscalaCommand(request.nombre(), request.volumenMl(), request.recargoPrecio());
        Escala actualizada = escalaUseCase.updateEscala(id, command);
        return ResponseEntity.ok(ResponseGlobal.success(EscalaResponse.from(actualizada), "Escala actualizada con éxito"));
    }
}