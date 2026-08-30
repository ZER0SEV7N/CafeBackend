package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.mapper;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioEntity toEntity (Usuario domain);
    Usuario toDomain(UsuarioEntity entity);
}
