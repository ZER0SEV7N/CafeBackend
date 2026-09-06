package com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.cupones.domain.model.Cupon;
import com.cavosh.cafebackend.cupones.domain.ports.out.CuponRepositoryPort;
import com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence.entity.CuponEntity;
import com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence.mapper.CuponMapper;
import com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence.repository.CuponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CuponRepositoryAdapter implements CuponRepositoryPort {

    private final CuponRepository cuponRepository;
    private final CuponMapper mapper;

    @Override
    public Optional<Cupon> findByCodigoIgnoreCase(String codigo) {
        return cuponRepository.findByCodigoIgnoreCase(codigo).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCodigoIgnoreCase(String codigo) {
        return cuponRepository.existsByCodigoIgnoreCase(codigo);
    }

    @Override
    public Cupon save(Cupon cupon) {
        CuponEntity entity = mapper.toEntity(cupon);
        CuponEntity guardado = cuponRepository.save(entity);
        return mapper.toDomain(guardado);
    }
}