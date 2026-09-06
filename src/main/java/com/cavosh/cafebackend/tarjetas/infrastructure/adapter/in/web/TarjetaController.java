package com.cavosh.cafebackend.tarjetas.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.tarjetas.domain.model.TarjetaUsuario;
import com.cavosh.cafebackend.tarjetas.domain.ports.in.TarjetaUseCase;
import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.in.web.doc.TarjetaDoc;
import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.in.web.dto.RegistrarTarjetaRequest;
import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.in.web.dto.TarjetaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarjetas")
@RequiredArgsConstructor
public class TarjetaController implements TarjetaDoc {

    private final TarjetaUseCase tarjetaUseCase;

    @GetMapping
    public ResponseEntity<ResponseGlobal<List<TarjetaResponse>>> listTarjetas(@AuthenticationPrincipal Usuario usuarioAuth) {
        List<TarjetaResponse> response = tarjetaUseCase.listTarjetas(usuarioAuth.id())
                .stream()
                .map(TarjetaResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Tarjetas obtenidas exitosamente"));
    }

    @PostMapping
    public ResponseEntity<ResponseGlobal<TarjetaResponse>> registerTarjeta(@AuthenticationPrincipal Usuario usuarioAuth, @Valid @RequestBody RegistrarTarjetaRequest request) {
        var command = new TarjetaUseCase.RegisterTarjetaCommand(
                usuarioAuth.id(),
                request.numeroTarjeta(),
                request.titular(),
                request.marca(),
                request.predeterminado()
        );

        TarjetaUsuario registrada = tarjetaUseCase.registerNewTarjeta(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseGlobal.success(HttpStatus.CREATED.value(), TarjetaResponse.from(registrada), "Tarjeta registrada exitosamente"));
    }

    @PatchMapping("/{id}/predeterminada")
    public ResponseEntity<ResponseGlobal<Void>> checkDefaults(@AuthenticationPrincipal Usuario usuarioAuth, @PathVariable Integer id) {
        tarjetaUseCase.checkDefaults(usuarioAuth.id(), id);
        return ResponseEntity.ok(ResponseGlobal.success(null, "Tarjeta seleccionada como predeterminada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGlobal<Void>> deleteTarjeta(@AuthenticationPrincipal Usuario usuarioAuth, @PathVariable Integer id) {
        tarjetaUseCase.deleteTarjeta(usuarioAuth.id(), id);
        return ResponseEntity.ok(ResponseGlobal.success(null, "Tarjeta eliminada exitosamente"));
    }
}