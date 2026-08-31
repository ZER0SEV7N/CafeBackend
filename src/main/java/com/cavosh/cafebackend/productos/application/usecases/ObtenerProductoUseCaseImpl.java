package com.cavosh.cafebackend.productos.application.usecases;

import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.productos.domain.model.Categoria;
import com.cavosh.cafebackend.productos.domain.model.Producto;
import com.cavosh.cafebackend.productos.domain.ports.in.ObtenerProductosUseCase;
import com.cavosh.cafebackend.productos.domain.ports.out.ProductoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class ObtenerProductoUseCaseImpl implements ObtenerProductosUseCase {

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
     * Obtener una lista de productos ordenados por frecuencia de pedido
     * @return Una lista de productos frecuentes
     */
    @Transactional(readOnly = true)
    public List<Producto> getFrequenciaOrdernadosProductos() {
        return productoRepository.findFrecuenteOrdenado();
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
}
