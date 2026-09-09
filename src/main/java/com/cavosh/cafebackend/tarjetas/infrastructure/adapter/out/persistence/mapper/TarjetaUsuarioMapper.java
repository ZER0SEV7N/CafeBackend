package com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence.mapper;

import com.cavosh.cafebackend.tarjetas.domain.model.TarjetaUsuario;
import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence.entity.TarjetaUsuarioEntity;
import org.mapstruct.Mapper;

/** 
 * Mapeador entre la entidad y el modelo de dominio de tarjetas de usuario.
 */
@Mapper(componentModel = "spring")
public interface TarjetaUsuarioMapper {
    TarjetaUsuario toDomain(TarjetaUsuarioEntity entity);
    TarjetaUsuarioEntity toEntity(TarjetaUsuario domain);
}