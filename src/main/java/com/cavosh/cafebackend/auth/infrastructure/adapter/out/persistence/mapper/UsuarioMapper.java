package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.mapper;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * UsuarioMapper : Interfaz que define los métodos de mapeo entre la entidad UsuarioEntity y el modelo de dominio Usuario.
 * Utiliza MapStruct para generar automáticamente la implementación de los métodos de mapeo.
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "updateAt", ignore = true)
    UsuarioEntity toEntity (Usuario domain);
    
    @Mapping(target = "updatedAt", ignore = true)
    Usuario toDomain(UsuarioEntity entity);
}
