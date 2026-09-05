package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.productos.domain.model.Categoria;
import com.cavosh.cafebackend.productos.domain.model.Producto;
import com.cavosh.cafebackend.productos.domain.ports.out.ProductoRepositoryPort;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.EscalaEntity;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.GrupoPersonalizacionEntity;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.CategoriaRepository;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.EscalaRepository;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.GrupoPersonalizacionRepository;
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
    private final EscalaRepository escalaRepository;
    private final GrupoPersonalizacionRepository grupoPersonalizacionRepository;
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
        return productoRepository.findByNuevoTrueAndActivoTrue()
                .stream()
                .map(productoMapper::toDomain)
                .toList();
    }

    /**
     * Obtener una lista de todos los productos frecuentes ordenados
     * @return la lista de frecuentes ordenados
     */
    public List<Producto> findFrecuenteOrdenado() {
        return productoRepository.findByFrecuenteTrueAndActivoTrue()
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

    /**
     *
     * @param categoriaId
     * @return
     */
    public boolean existsCategoriaById(Integer categoriaId) {
        return categoriaRepository.existsById(categoriaId);
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean existsById(Integer id) {
        return productoRepository.existsById(id);
    }

    /**
     *
     * @param producto
     * @param escalaIds
     * @param grupoPersonalizacionIds
     * @return
     */
    public Producto saveProducto(Producto producto, List<Integer> escalaIds, List<Integer> grupoPersonalizacionIds) {
        ProductoEntity entity = productoMapper.toEntity(producto);

        // Carga y asociación por lotes
        if (escalaIds != null && !escalaIds.isEmpty()) {
            List<EscalaEntity> escalas = escalaRepository.findAllByIdIn(escalaIds);
            entity.setEscalas(escalas);
        }

        if (grupoPersonalizacionIds != null && !grupoPersonalizacionIds.isEmpty()) {
            List<GrupoPersonalizacionEntity> grupos = grupoPersonalizacionRepository.findAllByIdIn(grupoPersonalizacionIds);
            entity.setGruposPersonalizacion(grupos);
        }

        ProductoEntity guardado = productoRepository.save(entity);
        return productoMapper.toDomain(guardado);
    }

    /**
     *
     * @param id
     * @param activo
     */
    public void changeState(Integer id, boolean activo) {
        int filasAfectadas = productoRepository.updateActivoById(id, activo);
        if (filasAfectadas == 0)
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);

    }
}