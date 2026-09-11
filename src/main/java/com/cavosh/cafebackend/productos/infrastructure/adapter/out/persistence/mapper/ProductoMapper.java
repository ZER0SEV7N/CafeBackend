package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.mapper;
import com.cavosh.cafebackend.productos.domain.model.*;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper del producto con todos los elementos que están relacionados con este
 */
@Mapper(componentModel = "spring")
public interface ProductoMapper {

    //Categoria
    Categoria toDomain(CategoriaEntity entity);
    CategoriaEntity toEntity(Categoria domain);

    //Escala
    Escala toDomain(EscalaEntity entity);
    EscalaEntity toEntity(Escala domain);

    //OpcionPersonalizacion
    OpcionPersonalizacion toDomain(OpcionPersonalizacionEntity entity);
    @Mapping(target = "grupo", ignore = true)
    OpcionPersonalizacionEntity toEntity(OpcionPersonalizacion domain);

    //GrupoPersonalizacion
    GrupoPersonalizacion toDomain(GrupoPersonalizacionEntity entity);
    GrupoPersonalizacionEntity toEntity(GrupoPersonalizacion domain);

    //Producto
    Producto toDomain(ProductoEntity entity);
    ProductoEntity toEntity(Producto domain);
}