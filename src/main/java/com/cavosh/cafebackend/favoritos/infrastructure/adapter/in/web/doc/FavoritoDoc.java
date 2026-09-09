package com.cavosh.cafebackend.favoritos.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.productos.ProductoResumenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Favoritos", description = "Gestión de la lista de productos favoritos del cliente")
@SecurityRequirement(name = "bearerAuth")
public interface FavoritoDoc {

    @Operation(summary = "Listar favoritos del usuario", description = "Retorna todos los productos que el cliente autenticado ha marcado con el corazón.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de favoritos obtenida exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> listarFavoritos(
            @Parameter(hidden = true) Usuario usuarioAuth
    );

    @Operation(summary = "Verificar si un producto es favorito", description = "Retorna true o false para determinar el estado visual del corazón.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado verificado correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    ResponseEntity<ResponseGlobal<Boolean>> verificarFavorito(
            @Parameter(hidden = true) Usuario usuarioAuth,
            @Parameter(description = "ID del producto a consultar") Integer productoId
    );

    @Operation(summary = "Marcar producto como favorito", description = "Agrega el producto a la lista de favoritos del cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto agregado a favoritos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "409", description = "El producto ya estaba en favoritos")
    })
    ResponseEntity<ResponseGlobal<Void>> agregarFavorito(
            @Parameter(hidden = true) Usuario usuarioAuth,
            @Parameter(description = "ID del producto a marcar") Integer productoId
    );

    @Operation(summary = "Quitar producto de favoritos", description = "Remueve el producto de la lista del cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto removido de favoritos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "404", description = "El producto no estaba en la lista de favoritos")
    })
    ResponseEntity<ResponseGlobal<Void>> eliminarFavorito(
            @Parameter(hidden = true) Usuario usuarioAuth,
            @Parameter(description = "ID del producto a remover") Integer productoId
    );
}