package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.domain.model.Producto;
import com.cavosh.cafebackend.productos.domain.ports.in.ObtenerProductosUseCase;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.doc.ProductoApi;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.CategoriaResponse;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.ProductoDetalleResponse;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.ProductoResumenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para manejar las solicitudes relacionadas con los productos y categorías.
 * Endpoints:
 * - GET /api/productos/categorias: Obtiene todas las categorías activas.
 * - GET /api/productos: Lista todos los productos activos.
 * - GET /api/productos/categoria/{categoriaId}: Filtra productos por categoría.
 * - GET /api/productos/nuevos: Obtiene productos nuevos (New in).
 * - GET /api/productos/frecuentes: Obtiene productos frecuentes (Frequently ordered).
 * - GET /api/productos/{id}: Obtiene el detalle completo de un producto por su ID.
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController implements ProductoApi {

    private final ObtenerProductosUseCase obtenerProductosUseCase;

    /**
     * Endpoint para obtener las categorias
     * @GET /api/productos/categorias
     * @return ResponseEntity con la lista de categorías activas
     */
    @GetMapping("/categorias")
    public ResponseEntity<ResponseGlobal<List<CategoriaResponse>>> getCategorias() {
        List<CategoriaResponse> response = obtenerProductosUseCase.getCategorias()
                .stream()
                .map(CategoriaResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Categorías obtenidas con éxito"));
    }

    /**
     * Endpoint para obtener todos los productos activos
     * @GET /api/productos
     * @return ResponseEntity con la lista de productos activos
     */
    @GetMapping
    public ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getAllProductos() {
        List<ProductoResumenResponse> response = obtenerProductosUseCase.getAllActivoProductos()
                .stream()
                .map(ProductoResumenResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Productos obtenidos con éxito"));
    }

    /**
     * Endpoint para obtener una categoria específica y sus productos
     * @GET /api/productos/categoria/{categoriaId}
     * @param categoriaId - Id de categoria
     * @return ResponseEntity con la lista de productos por las categorias
     */
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getProductosPorCategoria(@PathVariable Integer categoriaId) {
        List<ProductoResumenResponse> response = obtenerProductosUseCase.getProductosByCategoria(categoriaId)
                .stream()
                .map(ProductoResumenResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Productos filtrados por categoría"));
    }

    /**
     * Endpoint para obtener los productos nuevos
     * @GET /api/productos/nuevos
     * @return ResponseEntity con la lista de productos nuevos
     */
    @GetMapping("/nuevos")
    public ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getProductosNuevos() {
        List<ProductoResumenResponse> response = obtenerProductosUseCase.getNuevoInProductos()
                .stream()
                .map(ProductoResumenResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Productos nuevos obtenidos con éxito"));
    }


    /**
     * Endpoint para obtener los productos frecuentes
     * @GET /api/productos/frecuentes
     * @return ResponseEntity con la lista de productos frecuentes
     */
    @GetMapping("/frecuentes")
    public ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getProductosFrecuentes() {
        List<ProductoResumenResponse> response = obtenerProductosUseCase.getFrequenciaOrdernadosProductos()
                .stream()
                .map(ProductoResumenResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Productos frecuentes obtenidos con éxito"));
    }

    /**
     * Endpoint para obtener un producto específico por su ID
     * @GET /api/productos/{id}
     * @param id - Id del producto
     * @return ResponseEntity con el detalle del producto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGlobal<ProductoDetalleResponse>> getProductoPorId(@PathVariable Integer id) {
        Producto producto = obtenerProductosUseCase.getProductoById(id);
        ProductoDetalleResponse response = ProductoDetalleResponse.from(producto);

        return ResponseEntity.ok(ResponseGlobal.success(response, "Detalle del producto obtenido con éxito"));
    }
}