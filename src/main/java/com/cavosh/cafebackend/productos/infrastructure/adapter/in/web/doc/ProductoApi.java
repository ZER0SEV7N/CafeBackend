package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Productos", description = "Catálogo de productos, categorías y gestión administrativa")
public interface ProductoApi {

    // --- Consultas públicas ---

    @Operation(summary = "Obtener todas las categorías", description = "Retorna las categorías activas para el menú superior.")
    @ApiResponse(responseCode = "200", description = "Categorías obtenidas correctamente")
    ResponseEntity<ResponseGlobal<List<CategoriaResponse>>> getCategorias();

    @Operation(summary = "Listar todos los productos", description = "Retorna el catálogo completo de productos activos.")
    @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente")
    ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getAllProductos();

    @Operation(summary = "Filtrar productos por categoría", description = "Retorna los productos asignados a una categoría específica.")
    @ApiResponse(responseCode = "200", description = "Productos filtrados correctamente")
    ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getProductosPorCategoria(
            @Parameter(description = "ID de la categoría", example = "1") Integer categoriaId
    );

    @Operation(summary = "Obtener novedades (New in)", description = "Retorna los productos para la sección New in de la pantalla Home.")
    @ApiResponse(responseCode = "200", description = "Novedades obtenidas correctamente")
    ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getProductosNuevos();

    @Operation(summary = "Obtener frecuentes (Frequently ordered)", description = "Retorna los productos para la sección de pedidos recurrentes.")
    @ApiResponse(responseCode = "200", description = "Productos frecuentes obtenidos correctamente")
    ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getProductosFrecuentes();

    @Operation(summary = "Obtener detalle completo de un producto", description = "Retorna escalas, recargos y opciones de personalización del producto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle del producto obtenido"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    ResponseEntity<ResponseGlobal<ProductoDetalleResponse>> getProductoPorId(
            @Parameter(description = "ID del producto", example = "1") Integer id
    );

    // --- Operaciones Administrativas (Solo ADMIN) ---

    @Operation(summary = "Crear nuevo producto", description = "Requiere rol ADMIN. Asocia el producto a categorías, tamaños y personalizaciones.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (Requiere ADMIN)")
    })
    ResponseEntity<ResponseGlobal<ProductoDetalleResponse>> saveProducto(CrearProductoRequest request);

    @Operation(summary = "Actualizar producto existente", description = "Requiere rol ADMIN. Modifica información, precios o asociaciones del producto.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (Requiere ADMIN)"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    ResponseEntity<ResponseGlobal<ProductoDetalleResponse>> updateProducto(
            @Parameter(description = "ID del producto", example = "1") Integer id,
            ActualizarProductoRequest request
    );

    @Operation(summary = "Cambiar estado de un producto", description = "Requiere rol ADMIN. Activa o desactiva la visibilidad de un producto.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (Requiere ADMIN)"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    ResponseEntity<ResponseGlobal<Void>> changeState(
            @Parameter(description = "ID del producto", example = "1") Integer id,
            @RequestParam boolean activo
    );
}