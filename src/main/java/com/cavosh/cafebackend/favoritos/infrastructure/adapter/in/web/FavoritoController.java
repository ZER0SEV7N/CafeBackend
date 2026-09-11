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

/**
 * Controlador REST para la gestión de productos favoritos del usuario.
 * Contiene los siguientes endpoints:
 * - GET /api/favoritos: Lista los productos favoritos del usuario autenticado.
 * - GET /api/favoritos/{productoId}/check: Verifica si un producto
 * está en la lista de favoritos del usuario autenticado.
 * - POST /api/favoritos/{productoId}: Agrega un producto a la lista de favoritos del usuario autenticado.
 * - DELETE /api/favoritos/{productoId}: Elimina un producto de la lista de favoritos del usuario autenticado.
 */
@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController implements FavoritoDoc {

    private final FavoritoUseCase favoritoUseCase;

    /**
     * Obtiene la lista de productos favoritos del usuario autenticado.
     * @param usuarioAuth el usuario autenticado.
     * @return la lista de productos favoritos.
     */
    @GetMapping
    public ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> listarFavoritos(@AuthenticationPrincipal Usuario usuarioAuth) {
        List<ProductoResumenResponse> response = favoritoUseCase.listFavoritos(usuarioAuth.id())
                .stream()
                .map(ProductoResumenResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Favoritos obtenidos con éxito"));
    }

    /**
     * Verifica si un producto está en la lista de favoritos del usuario autenticado.
     * @param usuarioAuth el usuario autenticado.
     * @param productoId el ID del producto a verificar.
     * @return true si el producto es favorito, false en caso contrario.
     */
    @GetMapping("/{productoId}/check")
    public ResponseEntity<ResponseGlobal<Boolean>> verificarFavorito(@AuthenticationPrincipal Usuario usuarioAuth, @PathVariable("productoId") Integer productoId) {
        boolean esFavorito = favoritoUseCase.isFavorito(usuarioAuth.id(), productoId);
        return ResponseEntity.ok(ResponseGlobal.success(esFavorito, "Consulta de favorito realizada"));
    }

    /**
     * Agrega un producto a la lista de favoritos del usuario autenticado.
     * @param usuarioAuth el usuario autenticado.
     * @param productoId el ID del producto a agregar.
     * @return una respuesta con el resultado de la operación.
     */
    @PostMapping("/{productoId}")
    public ResponseEntity<ResponseGlobal<Void>> agregarFavorito( @AuthenticationPrincipal Usuario usuarioAuth, @PathVariable ("productoId") Integer productoId) {
        favoritoUseCase.addFavorito(usuarioAuth.id(), productoId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseGlobal.success(HttpStatus.CREATED.value(), null, "Producto agregado a favoritos"));
    }

    /**
     * Elimina un producto de la lista de favoritos del usuario autenticado.
     * @param usuarioAuth el usuario autenticado.
     * @param productoId el ID del producto a eliminar.
     * @return una respuesta con el resultado de la operación.
     */
    @DeleteMapping("/{productoId}")
    public ResponseEntity<ResponseGlobal<Void>> eliminarFavorito( @AuthenticationPrincipal Usuario usuarioAuth, @PathVariable ("productoId") Integer productoId ) {
        favoritoUseCase.deleteFavorito(usuarioAuth.id(), productoId);
        return ResponseEntity.ok(ResponseGlobal.success(null, "Producto removido de favoritos"));
    }
}