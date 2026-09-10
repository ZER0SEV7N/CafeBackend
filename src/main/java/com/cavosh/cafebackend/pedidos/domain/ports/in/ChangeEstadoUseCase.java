package com.cavosh.cafebackend.pedidos.domain.ports.in;

import com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido;

public interface ChangeEstadoUseCase {
    void changeEstadoPedido(Integer id, EstadoPedido nuevoEstado);
}
