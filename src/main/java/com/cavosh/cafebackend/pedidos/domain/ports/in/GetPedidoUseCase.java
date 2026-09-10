package com.cavosh.cafebackend.pedidos.domain.ports.in;

import java.util.List;

import com.cavosh.cafebackend.pedidos.domain.model.Pedido;

/** 
 * Puerto de entrada para la gestión de pedidos.
 * Define las operaciones que deben implementarse para interactuar con el sistema de pedidos.
 */
public interface GetPedidoUseCase {
    Pedido getPedidoById(Integer id, Integer usuarioId, boolean isStaff);
    List<Pedido> listPedidosByUsuarioId(Integer usuarioId);
}
