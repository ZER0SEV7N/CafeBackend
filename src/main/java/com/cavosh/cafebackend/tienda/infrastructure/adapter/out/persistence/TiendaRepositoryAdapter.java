package com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;
import com.cavosh.cafebackend.tienda.domain.port.out.TiendaRepositoryPort;
import com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.mapper.TiendaMapper;
import com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.repository.TiendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TiendaRepositoryAdapter implements TiendaRepositoryPort {

    private final TiendaRepository tiendaRepository;
    private final TiendaMapper mapper;

    public Optional<Cafeteria> findById(Integer id) {
        return tiendaRepository.findById(id).map(mapper::toDomain);
    }

    public List<Cafeteria> findAllActive() {
        return tiendaRepository.findByActivoTrue().stream().map(mapper::toDomain).toList();
    }

    public List<Cafeteria> findByCiudad(String city) {
        return tiendaRepository.findByCiudadIgnoreCaseAndActivoTrue(city).stream().map(mapper::toDomain).toList();
    }

    public List<Cafeteria> findFrequentlyChosen() {
        return tiendaRepository.findByFrecuenteTrueAndActivoTrue().stream().map(mapper::toDomain).toList();
    }
}
