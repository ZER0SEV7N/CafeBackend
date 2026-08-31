package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.productos.domain.model.Categoria;
import com.cavosh.cafebackend.productos.domain.model.Producto;
import com.cavosh.cafebackend.productos.domain.ports.out.ProductoRepositoryPort;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.CategoriaRepository;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador del repositorio de productos
 */
@Component
@RequiredArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    /**
     * Buscar un producto por su ID
     * @param id - id del producto
     * @return un objeto de producto envuelto en un Optional
     */
    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id).map(productoMapper::toDomain);
    }

    /**
     * Obtener todos los productos activos
     * @return una lista de todos los productos que se encuentren activo
     */
    public List<Producto> findAllActivo() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(productoMapper::toDomain)
                .toList();
    }

    /**
     * Obtener productos por ID de categoría
     * @param categoriaId - Id de la categoría
     * @return una lista de productos que pertenecen a la categoría y están activos
     */
    public List<Producto> findByCategoriaId(Integer categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId)
                .stream()
                .map(productoMapper::toDomain)
                .toList();
    }

    /**
     * Obtener una lista de todos los productos nuevos
     * @return la lista de nuevos
     */
    public List<Producto> findNuevo() {
        return productoRepository.findByEsNuevoTrueAndActivoTrue()
                .stream()
                .map(productoMapper::toDomain)
                .toList();
    }

    /**
     * Obtener una lista de todos los productos frecuentes ordenados
     * @return la lista de frecuentes ordenados
     */
    public List<Producto> findFrecuenteOrdenado() {
        return productoRepository.findByEsFrecuenteTrueAndActivoTrue()
                .stream()
                .map(productoMapper::toDomain)
                .toList();
    }

    /**
     * Obtener una lista de todas las categorías activas ordenadas por orden visual
     * @return la lista de categorías activas ordenadas por orden visual
     */
    public List<Categoria> findAllCategorias() {
        return categoriaRepository.findByActivaTrueOrderByOrdenVisualAsc()
                .stream()
                .map(productoMapper::toDomain)
                .toList();
    }
}