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

/** 
 * Controlador REST para la gestión de tarjetas de usuario.
 * Contiene los siguientes endpoints:
 * - GET /api/tarjetas: Lista las tarjetas guardadas del usuario autenticado.
 * - POST /api/tarjetas: Registra una nueva tarjeta para el usuario autenticado.
 * - PATCH /api/tarjetas/{id}/predeterminada: Establece una tarjeta como predeterminada para el usuario autenticado.
 * - DELETE /api/tarjetas/{id}: Elimina una tarjeta del usuario autenticado
 * 
 */
@RestController
@RequestMapping("/api/tarjetas")
@RequiredArgsConstructor
public class TarjetaController implements TarjetaDoc {

    private final TarjetaUseCase tarjetaUseCase;

    /** 
     * Lista las tarjetas guardadas del usuario autenticado.
     * @param usuarioAuth El usuario autenticado.
     * @return Una lista de tarjetas del usuario.
     */
    @GetMapping
    public ResponseEntity<ResponseGlobal<List<TarjetaResponse>>> listTarjetas(@AuthenticationPrincipal Usuario usuarioAuth) {
        List<TarjetaResponse> response = tarjetaUseCase.listTarjetas(usuarioAuth.id())
                .stream()
                .map(TarjetaResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Tarjetas obtenidas exitosamente"));
    }

    /** 
     * Registra una nueva tarjeta para el usuario autenticado.
     * @param usuarioAuth El usuario autenticado.
     * @param request {
     *    *     "numeroTarjeta": "string",
     *    *     "titular": "string",
     *    *     "marca": "string",
     *    *     "predeterminado": true
     *    }
     * @return La tarjeta registrada.
     */
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

    /** 
     * Establece una tarjeta como predeterminada para el usuario autenticado.
     * @param usuarioAuth El usuario autenticado.
     * @param id El ID de la tarjeta.
     * @return Un mensaje indicando que la tarjeta ha sido seleccionada como predeterminada.
     */
    @PatchMapping("/{id}/predeterminada")
    public ResponseEntity<ResponseGlobal<Void>> checkDefaults(@AuthenticationPrincipal Usuario usuarioAuth, @PathVariable Integer id) {
        tarjetaUseCase.checkDefaults(usuarioAuth.id(), id);
        return ResponseEntity.ok(ResponseGlobal.success(null, "Tarjeta seleccionada como predeterminada"));
    }

    /** 
     * Elimina una tarjeta del usuario autenticado.
     * @param usuarioAuth El usuario autenticado.
     * @param id El ID de la tarjeta.
     * @return Un mensaje indicando que la tarjeta ha sido eliminada.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseGlobal<Void>> deleteTarjeta(@AuthenticationPrincipal Usuario usuarioAuth, @PathVariable Integer id) {
        tarjetaUseCase.deleteTarjeta(usuarioAuth.id(), id);
        return ResponseEntity.ok(ResponseGlobal.success(null, "Tarjeta eliminada exitosamente"));
    }
}