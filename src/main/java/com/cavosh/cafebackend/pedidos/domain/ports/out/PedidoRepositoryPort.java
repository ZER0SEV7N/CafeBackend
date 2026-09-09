package com.cavosh.cafebackend.pedidos.domain.ports.out;

import com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido;
import com.cavosh.cafebackend.pedidos.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepositoryPort {
    Pedido save(Pedido pedido);
    Optional<Pedido> findById(Integer id);
    Optional<Pedido> findByCodigoOrden(String codigoOrden);
    List<Pedido> findByUsuarioIdOrderByCreatedAtDesc(Integer usuarioId);
    void actualizarEstado(Integer pedidoId, EstadoPedido nuevoEstado);
}