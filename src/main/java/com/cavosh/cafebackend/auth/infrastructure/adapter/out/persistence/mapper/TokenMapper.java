package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.mapper;

import com.cavosh.cafebackend.auth.domain.model.TokenRecuperacion;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.TokenRecuperacionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    TokenRecuperacionEntity toEntity (TokenRecuperacion domain);
    TokenRecuperacion toDomain(TokenRecuperacionEntity entity);
}
