package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence;
import com.cavosh.cafebackend.productos.domain.model.Escala;
import com.cavosh.cafebackend.productos.domain.ports.out.EscalaRepositoryPort;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.EscalaEntity;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.EscalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EscalaRepositoryAdapter implements EscalaRepositoryPort {

    private final EscalaRepository escalaRepository;
    private final ProductoMapper mapper;

    public List<Escala> findAll() {
        return escalaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }


    public Optional<Escala> findById(Integer id) { return escalaRepository.findById(id).map(mapper::toDomain); }


    public boolean existsByNombreIgnoreCase(String nombre) { return escalaRepository.existsByNombreIgnoreCase(nombre); }

    public Escala save(Escala escala) {
        EscalaEntity entity = mapper.toEntity(escala);
        EscalaEntity guardado = escalaRepository.save(entity);
        return mapper.toDomain(guardado);
    }
}