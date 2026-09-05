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

@Service
@RequiredArgsConstructor
public class ManageGrupoPersonalizacionUseCaseImpl implements ManageGrupoPersonalizacionUseCase {

    private final GrupoPersonalizacionRepositoryPort grupoRepository;

    @Transactional(readOnly = true)
    public List<GrupoPersonalizacion> getAllGrupos() {
        return grupoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public GrupoPersonalizacion getGrupoById(Integer id) {
        return grupoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Grupo de personalización no encontrado con ID: " + id));
    }

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
