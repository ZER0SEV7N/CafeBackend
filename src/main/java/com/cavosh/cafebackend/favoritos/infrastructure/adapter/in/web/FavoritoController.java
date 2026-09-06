package com.cavosh.cafebackend.favoritos.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.favoritos.domain.ports.in.FavoritoUseCase;
import com.cavosh.cafebackend.favoritos.infrastructure.adapter.in.web.doc.FavoritoDoc;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.productos.ProductoResumenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController implements FavoritoDoc {

    private final FavoritoUseCase favoritoUseCase;

    @Override
    @GetMapping
    public ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> listarFavoritos(
            @AuthenticationPrincipal Usuario usuarioAuth
    ) {
        List<ProductoResumenResponse> response = favoritoUseCase.listFavoritos(usuarioAuth.id())
                .stream()
                .map(ProductoResumenResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Favoritos obtenidos con éxito"));
    }

    @Override
    @GetMapping("/{productoId}/check")
    public ResponseEntity<ResponseGlobal<Boolean>> verificarFavorito(
            @AuthenticationPrincipal Usuario usuarioAuth,
            @PathVariable Integer productoId
    ) {
        boolean esFavorito = favoritoUseCase.isFavorito(usuarioAuth.id(), productoId);
        return ResponseEntity.ok(ResponseGlobal.success(esFavorito, "Consulta de favorito realizada"));
    }

    @Override
    @PostMapping("/{productoId}")
    public ResponseEntity<ResponseGlobal<Void>> agregarFavorito(
            @AuthenticationPrincipal Usuario usuarioAuth,
            @PathVariable Integer productoId
    ) {
        favoritoUseCase.addFavorito(usuarioAuth.id(), productoId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseGlobal.success(HttpStatus.CREATED.value(), null, "Producto agregado a favoritos"));
    }

    @Override
    @DeleteMapping("/{productoId}")
    public ResponseEntity<ResponseGlobal<Void>> eliminarFavorito(
            @AuthenticationPrincipal Usuario usuarioAuth,
            @PathVariable Integer productoId
    ) {
        favoritoUseCase.deleteFavorito(usuarioAuth.id(), productoId);
        return ResponseEntity.ok(ResponseGlobal.success(null, "Producto removido de favoritos"));
    }
}