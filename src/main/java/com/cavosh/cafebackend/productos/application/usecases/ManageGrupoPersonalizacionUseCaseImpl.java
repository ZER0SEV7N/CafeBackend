package com.cavosh.cafebackend.productos.application.usecases;

import com.cavosh.cafebackend.global.domain.exception.AlreadyExistsException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.productos.domain.model.GrupoPersonalizacion;
import com.cavosh.cafebackend.productos.domain.model.OpcionPersonalizacion;
import com.cavosh.cafebackend.productos.domain.ports.in.ManageGrupoPersonalizacionUseCase;
import com.cavosh.cafebackend.productos.domain.ports.out.GrupoPersonalizacionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del caso de uso para la gestión de grupos de personalización.
 * Tiene los siguientes métodos:
 * - getAllGrupos(): Obtiene todos los grupos de personalización.
 * - getGrupoById(Integer id): Obtiene un grupo de personalización por su ID.
 * - saveGrupo(CrearGrupoCommand command): Crea un nuevo grupo de personalización.
 */
@Service
@RequiredArgsConstructor
public class ManageGrupoPersonalizacionUseCaseImpl implements ManageGrupoPersonalizacionUseCase {

    private final GrupoPersonalizacionRepositoryPort grupoRepository;

    /**
     * Obtiene todos los grupos de personalización.
     * @return la lista de grupos de personalización.
     */
    @Transactional(readOnly = true)
    public List<GrupoPersonalizacion> getAllGrupos() { return grupoRepository.findAll(); }

    /**
     * Obtiene un grupo de personalización por su ID.
     * @param id el ID del grupo de personalización.
     * @return el grupo de personalización encontrado.
     */
    @Transactional(readOnly = true)
    public GrupoPersonalizacion getGrupoById(Integer id) {
        return grupoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Grupo de personalización no encontrado con ID: " + id));
    }

    /**
     * Crea un nuevo grupo de personalización.
     * @param command los datos para crear el grupo de personalización.
     * @return el grupo de personalización creado.
     */
    @Transactional
    public GrupoPersonalizacion saveGrupo(CrearGrupoCommand command){
        String nombre = command.nombreGrupo().trim();

        if(grupoRepository.existsByNombreGrupoIgnoreCase(nombre))
            throw new AlreadyExistsException("Ya existe un grupo de personalización registrado con el nombre: " + nombre);

        List<OpcionPersonalizacion> opciones = command.opciones().stream()
                .map(op -> new OpcionPersonalizacion(null, op.nombre().trim(), op.recargoPrecio(), op.porDefecto()))
                .toList();

        GrupoPersonalizacion nuevoGrupo = new GrupoPersonalizacion(
                null,
                nombre,
                command.seleccionMultiple(),
                command.obligatorio(),
                opciones
        );

        return grupoRepository.save(nuevoGrupo);
    }
}
