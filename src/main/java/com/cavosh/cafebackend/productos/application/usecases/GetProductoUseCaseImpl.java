package com.cavosh.cafebackend.productos.application.usecases;

import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.productos.domain.model.Categoria;
import com.cavosh.cafebackend.productos.domain.model.Producto;
import com.cavosh.cafebackend.productos.domain.ports.in.GetProductosUseCase;
import com.cavosh.cafebackend.productos.domain.ports.out.ProductoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Implementación del caso de uso para obtener los productos.
 * Tiene los siguientes métodos:
 * - getProductosByCategoria(Integer categoriaId): Obtiene los productos por ID de categoría.
 * - getNuevoInProductos(): Obtiene todos los productos nuevos.
 * - getProductoById(Integer id): Obtiene un producto por su ID.
 * - getCategorias(): Obtiene todas las categorías de productos.
 * - searchProductos(String query): Busca productos por nombre o descripción.
 * - getProductosFrecuentes(Integer usuarioId): Obtiene los productos frecuentes para un usuario específico.
 */
@Service
@RequiredArgsConstructor
public class GetProductoUseCaseImpl implements GetProductosUseCase {

    private final ProductoRepositoryPort productoRepository;

    /**
     * Obtener todos los productos activos
     * @return una lista de productos
     */
    @Transactional(readOnly = true)
    public List<Producto> getAllActivoProductos() {
        return productoRepository.findAllActivo();
    }

    /**
     * Obtener productos por categoria
     * @param categoriaId - id de la categoria
     * @return una lista de productos
     */
    @Transactional(readOnly = true)
    public List<Producto> getProductosByCategoria(Integer categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    /**
     * Obtener una lista de todos los productos nuevos
     * @return la lista de nuevos
     */
    @Transactional(readOnly = true)
    public List<Producto> getNuevoInProductos() {
        return productoRepository.findNuevo();
    }

    /**
     * Obtener un producto mediante su ID
     * @param id - id del producto
     * @return un producto específico
     */
    @Transactional(readOnly = true)
    public Producto getProductoById(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el ID: " + id));
    }

    /**
     * Obtener todas las categorias de productos
     * @return lista de categorias
     */
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias() {
        return productoRepository.findAllCategorias();
    }

    /**
     * Buscar productos por nombre o descripción
     * @param query - cadena de búsqueda: Busca productos cuyo nombre o descripción contenga la cadena proporcionada, ignorando mayúsculas y minúsculas.
     * @return una lista de productos que coincidan con la búsqueda
     */
    @Transactional(readOnly = true)
    public List<Producto> searchProductos(String query) {
        if (query == null || query.trim().isBlank()) 
            return productoRepository.findAllActivo();
        
        return productoRepository.searchProductos(query.trim());
    }

    /**
     * Obtener una lista de productos frecuentes para un usuario específico
     * @param usuarioId - Id del usuario
     * @return la lista de productos frecuentes para el usuario
     */
    @Transactional(readOnly = true)
    public List<Producto> getProductosFrecuentes(Integer usuarioId) {
        if (usuarioId == null) 
            return List.of();
        
        return productoRepository.findFrecuentesPorUsuario(usuarioId);
    }
}
