package com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido;
import com.cavosh.cafebackend.pedidos.domain.model.Pedido;

import java.math.BigDecimal;
import java.time.Instant;

public record PedidoResumenResponse(
        Integer id,
        String codigoOrden,
        EstadoPedido estado,
        BigDecimal total,
        int cantidadItems,
        Instant createdAt
) {
    public static PedidoResumenResponse from(Pedido p) {
        int totalItems = 0;
        if (p.detalles() != null) {
            for (var detalle : p.detalles()) {
                Integer cantidad = detalle.cantidad();
                if (cantidad == null) 
                        cantidad = 1;
                
                totalItems += cantidad;
            }
        }

        return new PedidoResumenResponse(
                p.id(),
                p.codigoOrden(),
                p.estado(),
                p.total(),
                totalItems,
                p.createdAt()
        );
    }
}