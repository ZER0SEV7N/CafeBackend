package com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.mapper;

import com.cavosh.cafebackend.pedidos.domain.model.DetalleOpcionSeleccionada;
import com.cavosh.cafebackend.pedidos.domain.model.DetallePedido;
import com.cavosh.cafebackend.pedidos.domain.model.Pedido;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.entity.DetalleOpcionSeleccionadaEntity;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.entity.DetallePedidoEntity;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.entity.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** 
 * Mapeador de entidades de persistencia a modelos de dominio y viceversa para la entidad Pedido y sus detalles.
 * Utiliza MapStruct para generar automáticamente la implementación de los métodos de mapeo.
 */
@Mapper(componentModel = "spring")
public interface PedidoMapper {

    DetalleOpcionSeleccionada toDomain(DetalleOpcionSeleccionadaEntity entity);
    DetallePedido toDomain(DetallePedidoEntity entity);
    Pedido toDomain(PedidoEntity entity);

    @Mapping(target = "detallePedido", ignore = true)
    DetalleOpcionSeleccionadaEntity toEntity(DetalleOpcionSeleccionada domain);
    
    @Mapping(target = "pedido", ignore = true)
    DetallePedidoEntity toEntity(DetallePedido domain);
    PedidoEntity toEntity(Pedido domain);
}