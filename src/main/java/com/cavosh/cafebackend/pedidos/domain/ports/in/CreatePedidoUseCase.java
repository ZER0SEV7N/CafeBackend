package com.cavosh.cafebackend.pedidos.domain.ports.in;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cavosh.cafebackend.pedidos.domain.model.MetodoPago;
import com.cavosh.cafebackend.pedidos.domain.model.Pedido;
import com.cavosh.cafebackend.pedidos.domain.model.TipoEntrega;

/**
 * Puerto de entrada para la creación de pedidos.
 * Define la operacione que deben implementarse para crear un nuevo pedido en el sistema.
 */
public interface CreatePedidoUseCase {
    
    record ItemPedidoCommand(
            Integer productoId,
            Integer escalaId,
            List<Integer> opcionIds,
            Integer cantidad
        ) {}


        record CreatePedidoCommand(
            Integer usuarioId,
            Integer cafeteriaId,
            TipoEntrega tipoEntrega,
            LocalDate fechaRecojo,
            LocalTime horaRecojo,
            MetodoPago metodoPago,
            String referenciaPago,
            String codigoCupon,
            List<ItemPedidoCommand> items
    ) {}
    
     Pedido savePedido(CreatePedidoCommand command);

}
