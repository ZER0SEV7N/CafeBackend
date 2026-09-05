package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.domain.model.GrupoPersonalizacion;
import com.cavosh.cafebackend.productos.domain.ports.in.ManageGrupoPersonalizacionUseCase;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.doc.GrupoPersonalizacionDoc;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.personalizacion.CrearGrupoPersonalizacionRequest;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.personalizacion.GrupoPersonalizacionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos-personalizacion")
@RequiredArgsConstructor
public class GrupoPersonalizacionController implements GrupoPersonalizacionDoc {

    private final ManageGrupoPersonalizacionUseCase grupoUseCase;

    @Override
    @GetMapping
    public ResponseEntity<ResponseGlobal<List<GrupoPersonalizacionResponse>>> getAllGrupos() {
        List<GrupoPersonalizacionResponse> response = grupoUseCase.getAllGrupos()
                .stream()
                .map(GrupoPersonalizacionResponse::from)
                .toList();
        return ResponseEntity.ok(ResponseGlobal.success(response, "Grupos obtenidos con éxito"));
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseGlobal<GrupoPersonalizacionResponse>> crearGrupo(@Valid @RequestBody CrearGrupoPersonalizacionRequest request) {
        var opcionesCommand = request.opciones().stream()
                .map(op -> new ManageGrupoPersonalizacionUseCase.OpcionCommand(op.nombre(), op.recargoPrecio(), op.porDefecto()))
                .toList();

        var command = new ManageGrupoPersonalizacionUseCase.CrearGrupoCommand(
                request.nombreGrupo(),
                request.seleccionMultiple(),
                request.obligatorio(),
                opcionesCommand
        );

        GrupoPersonalizacion creado = grupoUseCase.saveGrupo(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseGlobal.success(HttpStatus.CREATED.value(), GrupoPersonalizacionResponse.from(creado), "Grupo creado con éxito"));
    }
}