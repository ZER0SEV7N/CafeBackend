package com.cavosh.cafebackend.pedidos.domain.ports.out;

import com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido;
import com.cavosh.cafebackend.pedidos.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

/** 
 * Puerto de salida para la persistencia de pedidos.
 * Define las operaciones que deben implementarse para interactuar con la base de datos u otros sistemas de almacenamiento de pedidos.
 * Metodos incluidos: 
 * - save(Pedido pedido): Guarda un nuevo pedido en el sistema.
 * - findById(Integer id): Busca un pedido por su ID.
 * - findByCodigoOrden(String codigoOrden): Busca un pedido por su código de orden.
 * - findByUsuarioIdOrderByCreatedAtDesc(Integer usuarioId): Obtiene todos los
 * - updateEstado(Integer pedidoId, EstadoPedido nuevoEstado): Actualiza el estado de un pedido existente.
 */
public interface PedidoRepositoryPort {
    Pedido save(Pedido pedido);
    Optional<Pedido> findById(Integer id);
    Optional<Pedido> findByCodigoOrden(String codigoOrden);
    List<Pedido> findByUsuarioIdOrderByCreatedAtDesc(Integer usuarioId);
    void updateEstado(Integer pedidoId, EstadoPedido nuevoEstado);
}