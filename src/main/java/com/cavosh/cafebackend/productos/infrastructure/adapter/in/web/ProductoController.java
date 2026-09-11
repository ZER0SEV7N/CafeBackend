package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.domain.model.Producto;
import com.cavosh.cafebackend.productos.domain.ports.in.GetProductosUseCase;
import com.cavosh.cafebackend.productos.domain.ports.in.ManageProductUseCase;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.doc.ProductoDoc;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.*;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.productos.ActualizarProductoRequest;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.productos.CrearProductoRequest;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.productos.ProductoDetalleResponse;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.productos.ProductoResumenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para manejar las solicitudes relacionadas con los productos y categorías.
 * Endpoints de consultas:
 * - GET /api/productos/categorias: Obtiene todas las categorías activas.
 * - GET /api/productos: Lista todos los productos activos.
 * - GET /api/productos/categoria/{categoriaId}: Filtra productos por categoría.
 * - GET /api/productos/nuevos: Obtiene productos nuevos (New in).
 * - GET /api/productos/frecuentes: Obtiene productos frecuentes (Frequently ordered).
 * - GET /api/productos/{id}: Obtiene el detalle completo de un producto por su ID.
 * Endpoints administrativos:
 * - POST /api/productos: Crea un nuevo producto.
 * - PUT /api/productos/{id}: Actualiza un producto existente.
 * - PATCH /api/productos/{id}/estado: Cambia el estado de activo/inactivo de un producto.
 */
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController implements ProductoDoc {

    private final GetProductosUseCase getProductosUseCase;
    private final ManageProductUseCase manageProductUseCase;

    // --- Consultas Públicas ---

    /**
     * Endpoint para obtener las categorias
     * @GET /api/productos/categorias
     * @return ResponseEntity con la lista de categorías activas
     */
    @GetMapping("/categorias")
    public ResponseEntity<ResponseGlobal<List<CategoriaResponse>>> getCategorias() {
        List<CategoriaResponse> response = getProductosUseCase.getCategorias()
                .stream()
                .map(CategoriaResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Categorías obtenidas con éxito"));
    }

    /**
     * Endpoint para listar productos activos o buscar por coincidencia.
     *  - Carga normal: GET /api/productos
     *  - Barra de búsqueda: GET /api/productos?q=caramel
     * 
     * @param q Término opcional para filtrar por nombre o descripción
     * @return Lista de productos activos (filtrados o completos)
     */
    @GetMapping
    public ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getProductos(@RequestParam(name = "q", required = false) String q) {
        //Si no hay término de búsqueda, se listan todos los productos activos
        List<ProductoResumenResponse> response = getProductosUseCase.searchProductos(q)
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
        List<ProductoResumenResponse> response = getProductosUseCase.getProductosByCategoria(categoriaId)
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
        List<ProductoResumenResponse> response = getProductosUseCase.getNuevoInProductos()
                .stream()
                .map(ProductoResumenResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Productos nuevos obtenidos con éxito"));
    }

    /**
     * Endpoint para obtener los productos frecuentes de un usuario
     * @GET /api/productos/frecuentes
     * @param usuarioAuth - Usuario autenticado (obtenido del token)
     * @return ResponseEntity con la lista de productos frecuentes del usuario
     */
    @GetMapping("/frecuentes")
    public ResponseEntity<ResponseGlobal<List<ProductoResumenResponse>>> getProductosFrecuentes(@AuthenticationPrincipal Usuario usuarioAuth) {
        if (usuarioAuth == null) 
            return ResponseEntity.ok(ResponseGlobal.success(List.of(), "Sin productos frecuentes"));

        List<ProductoResumenResponse> response = getProductosUseCase.getProductosFrecuentes(usuarioAuth.id())
                .stream()
                .map(ProductoResumenResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Productos frecuentes del usuario"));
    }

    /**
     * Endpoint para obtener un producto específico por su ID
     * @GET /api/productos/{id}
     * @param id - Id del producto
     * @return ResponseEntity con el detalle del producto
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGlobal<ProductoDetalleResponse>> getProductoPorId(@PathVariable Integer id) {
        Producto producto = getProductosUseCase.getProductoById(id);
        ProductoDetalleResponse response = ProductoDetalleResponse.from(producto);

        return ResponseEntity.ok(ResponseGlobal.success(response, "Detalle del producto obtenido con éxito"));
    }

    // --- Endpoints Administrativos Protegidos ---

    /**
     * Endpoint para crear un nuevo producto
     * @POST /api/productos
     * @param request - Datos del producto a crear
     * @dto : {
     *     - "categoriaId": "Número entero que representa el ID de la categoría a la que pertenece el producto",
     *     - "nombre": "Nombre del producto",
     *     - "descripcion": "Descripción del producto",
     *     - "imagenUrl": "URL de la imagen del producto",
     *     - "precioBase": "Precio base del producto",
     *     - "nuevo": "Indica si el producto es nuevo",
     *     - "escalaIds": "Lista de IDs de las escalas asociadas al producto",
     *     - "grupoPersonalizacionIds": "Lista de IDs de los grupos de personalización asociados al producto"
     * }
     * @return ResponseEntity con el detalle del producto creado
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseGlobal<ProductoDetalleResponse>> saveProducto(@Valid @RequestBody CrearProductoRequest request) {
        var command = new ManageProductUseCase.SaveProductoCommand(
                request.categoriaId(),
                request.nombre(),
                request.descripcion(),
                request.imagenUrl(),
                request.precioBase(),
                request.nuevo(),
                request.escalaIds(),
                request.grupoPersonalizacionIds()
        );
        Producto creado = manageProductUseCase.saveProducto(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseGlobal.success(HttpStatus.CREATED.value(), ProductoDetalleResponse.from(creado), "Producto creado con éxito"));
    }

    /**
     * Endpoint para actualizar un producto existente
     * @PUT /api/productos/{id}
     * @param id - Id del producto
     * @param request - Datos del producto a actualizar
     * @return ResponseEntity con el detalle del producto actualizado
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseGlobal<ProductoDetalleResponse>> updateProducto(@PathVariable Integer id, @Valid @RequestBody ActualizarProductoRequest request) {
        var command = new ManageProductUseCase.UpdateProductoCommand(
                request.categoriaId(),
                request.nombre(),
                request.descripcion(),
                request.imagenUrl(),
                request.precioBase(),
                request.nuevo(),
                request.activo(),
                request.escalaIds(),
                request.grupoPersonalizacionIds()
        );
        Producto actualizado = manageProductUseCase.updateProducto(id, command);
        return ResponseEntity.ok(ResponseGlobal.success(ProductoDetalleResponse.from(actualizado), "Producto actualizado con éxito"));
    }

    /**
     * Endpoint para cambiar el estado de un producto
     * @PATCH /api/productos/{id}/estado
     * @param id - Id del producto
     * @param activo - Nuevo estado del producto
     * @return ResponseEntity con el resultado de la operación
     */
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseGlobal<Void>> changeState(@PathVariable Integer id, @RequestParam boolean activo) {
        manageProductUseCase.changeState(id, activo);
        return ResponseEntity.ok(ResponseGlobal.success(null, "Estado del producto modificado con éxito"));
    }
}