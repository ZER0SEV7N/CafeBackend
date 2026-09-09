package com.cavosh.cafebackend.productos.application.usecases;

import com.cavosh.cafebackend.global.domain.exception.AlreadyExistsException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.productos.domain.model.Escala;
import com.cavosh.cafebackend.productos.domain.ports.in.ManageEscalaUseCase;
import com.cavosh.cafebackend.productos.domain.ports.out.EscalaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del caso de uso para la gestión de escalas de productos.
 * Tiene los siguientes métodos:
 * - getAllEscalas(): Obtiene todas las escalas.
 * - getEscalaById(Integer id): Obtiene una escala por su ID.
 * - saveEscala(SaveEscalaCommand command): Crea una nueva escala.
 * - updateEscala(Integer id, UpdateEscalaCommand command): Actualiza una escala existente.
 */
@Service
@RequiredArgsConstructor
public class ManageEscalaUseCaseImpl implements ManageEscalaUseCase {

    private final EscalaRepositoryPort escalaRepository; 

    /**
     * Inyección de la propia clase para permitir la llamada a métodos transaccionales desde otros métodos de la misma clase
     */
    @Lazy
    private final ManageEscalaUseCase self;


    /**
     * Obtiene todas las escalas.
     * @return la lista de escalas.
     */
    @Transactional(readOnly = true)
    public List<Escala> getAllEscalas() {
        return escalaRepository.findAll();
    }

    /**
     * Obtiene una escala por su ID.
     * @param id el ID de la escala.
     * @return la escala encontrada.
     */
    @Transactional(readOnly = true)
    public Escala getEscalaById(Integer id) {
        return escalaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escala no encontrada con ID: " + id));
    }

    /**
     * Crea una nueva escala.
     * @param command los datos para crear la escala.
     * @return la escala creada.
     */
    @Transactional
    public Escala saveEscala(SaveEscalaCommand command) {
        String nombreLimpio = command.nombre().trim();

        //Validación de existencia de escala con el mismo nombre
        if (escalaRepository.existsByNombreIgnoreCase(nombreLimpio)) 
            throw new AlreadyExistsException("Ya existe una escala registrada con el nombre: " + nombreLimpio);
        

        Escala nueva = new Escala(null, nombreLimpio, command.volumenMl(), command.recargoPrecio());
        return escalaRepository.save(nueva);
    }

    /**
     * Actualiza una escala existente.
     * @param id el ID de la escala a actualizar.
     * @param command los datos para actualizar la escala.
     * @return la escala actualizada.
     */
    @Transactional
    public Escala updateEscala(Integer id, UpdateEscalaCommand command) {
        Escala actual = self.getEscalaById(id);
        String nuevoNombre = command.nombre().trim();

        //Validación de existencia de otra escala con el mismo nombre
        if (!actual.nombre().equalsIgnoreCase(nuevoNombre) && escalaRepository.existsByNombreIgnoreCase(nuevoNombre)) 
            throw new AlreadyExistsException("Ya existe otra escala registrada con el nombre: " + nuevoNombre);
        
        Escala actualizada = new Escala(actual.id(), nuevoNombre, command.volumenMl(), command.recargoPrecio());
        return escalaRepository.save(actualizada);
    }
}