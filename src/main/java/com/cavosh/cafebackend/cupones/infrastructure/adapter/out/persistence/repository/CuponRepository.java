package com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence.entity.CuponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuponRepository extends JpaRepository<CuponEntity, Integer> {
    Optional<CuponEntity> findByCodigoIgnoreCase(String codigo);
    boolean existsByCodigoIgnoreCase(String codigo);
}
