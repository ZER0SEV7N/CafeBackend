package com.cavosh.cafebackend.productos.application.usecases;

import com.cavosh.cafebackend.global.domain.exception.AlreadyExistsException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.productos.domain.model.Escala;
import com.cavosh.cafebackend.productos.domain.ports.in.ManageEscalaUseCase;
import com.cavosh.cafebackend.productos.domain.ports.out.EscalaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageEscalaUseCaseImpl implements ManageEscalaUseCase {

    private final EscalaRepositoryPort escalaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Escala> getAllEscalas() {
        return escalaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Escala getEscalaById(Integer id) {
        return escalaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escala no encontrada con ID: " + id));
    }

    @Transactional
    public Escala saveEscala(SaveEscalaCommand command) {
        String nombreLimpio = command.nombre().trim();

        // Uso obligatorio de la validación contra duplicados
        if (escalaRepository.existsByNombreIgnoreCase(nombreLimpio)) {
            throw new AlreadyExistsException("Ya existe una escala registrada con el nombre: " + nombreLimpio);
        }

        Escala nueva = new Escala(null, nombreLimpio, command.volumenMl(), command.recargoPrecio());
        return escalaRepository.save(nueva);
    }

    @Transactional
    public Escala updateEscala(Integer id, UpdateEscalaCommand command) {
        Escala actual = getEscalaById(id);
        String nuevoNombre = command.nombre().trim();

        if (!actual.nombre().equalsIgnoreCase(nuevoNombre) && escalaRepository.existsByNombreIgnoreCase(nuevoNombre)) {
            throw new AlreadyExistsException("Ya existe otra escala registrada con el nombre: " + nuevoNombre);
        }

        Escala actualizada = new Escala(actual.id(), nuevoNombre, command.volumenMl(), command.recargoPrecio());
        return escalaRepository.save(actualizada);
    }
}