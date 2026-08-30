package com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.mapper;

import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;
import com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.entity.TiendaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TiendaMapper {
    TiendaEntity toEntity(Cafeteria domain);
    Cafeteria toDomain(TiendaEntity entity);
}
