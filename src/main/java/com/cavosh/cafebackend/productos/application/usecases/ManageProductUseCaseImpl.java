package com.cavosh.cafebackend.productos.application.usecases;

import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.productos.domain.model.Producto;
import com.cavosh.cafebackend.productos.domain.ports.in.ManageProductUseCase;
import com.cavosh.cafebackend.productos.domain.ports.out.ProductoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageProductUseCaseImpl implements ManageProductUseCase {

    private final ProductoRepositoryPort productoRepository;

    @Transactional
    public Producto saveProducto(SaveProductoCommand command) {
        if(productoRepository.existsCategoriaById(command.categoriaId()))
            throw new ResourceNotFoundException("Categoria no encontrada con id: " + command.categoriaId());

        Instant now = Instant.now();
        Producto nuevo = new Producto(
                null,
                command.categoriaId(),
                command.nombre().trim(),
                command.descripcion(),
                command.imagenUrl(),
                command.precioBase(),
                command.nuevo(),
                command.frecuente(),
                true,
                List.of(),
                List.of(),
                now,
                now
        );

        return productoRepository.saveProducto(nuevo, command.escalaIds(), command.grupoPersonalizacionIds());
    }

    @Transactional
    public Producto updateProducto(Integer id, UpdateProductoCommand command) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        if(productoRepository.existsCategoriaById(command.categoriaId()))
            throw new ResourceNotFoundException("Categoria no encontrada con id: " + command.categoriaId());

        Producto actualizado = new Producto(
                existente.id(),
                command.categoriaId(),
                command.nombre().trim(),
                command.descripcion(),
                command.imagenUrl(),
                command.precioBase(),
                command.nuevo(),
                command.frecuente(),
                command.activo(),
                existente.escalas(),
                existente.gruposPersonalizacion(),
                existente.createdAt(),
                Instant.now()
        );

        return productoRepository.saveProducto(actualizado, command.escalaIds(), command.grupoPersonalizacionIds());
    }

    @Transactional
    public void changeState(Integer id, boolean active) {
        if (!productoRepository.existsById(id))
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);

        productoRepository.changeState(id, active);
    }


}
