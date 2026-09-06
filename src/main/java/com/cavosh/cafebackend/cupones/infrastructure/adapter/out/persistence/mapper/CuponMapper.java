package com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence.mapper;

import com.cavosh.cafebackend.cupones.domain.model.Cupon;
import com.cavosh.cafebackend.cupones.infrastructure.adapter.out.persistence.entity.CuponEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuponMapper {
    Cupon toDomain(CuponEntity entity);
    CuponEntity toEntity(Cupon domain);
}