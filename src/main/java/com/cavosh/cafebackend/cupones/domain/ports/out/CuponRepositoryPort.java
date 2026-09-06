package com.cavosh.cafebackend.cupones.domain.ports.out;

import com.cavosh.cafebackend.cupones.domain.model.Cupon;

import java.util.Optional;

public interface CuponRepositoryPort {
    Optional<Cupon> findByCodigoIgnoreCase(String codigo);
    boolean existsByCodigoIgnoreCase(String codigo);
    Cupon save(Cupon cupon);
}