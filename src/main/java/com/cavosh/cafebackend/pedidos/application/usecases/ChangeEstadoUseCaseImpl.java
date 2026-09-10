package com.cavosh.cafebackend.pedidos.application.usecases;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido;
import com.cavosh.cafebackend.pedidos.domain.ports.in.ChangeEstadoUseCase;
import com.cavosh.cafebackend.pedidos.domain.ports.out.PedidoRepositoryPort;

import lombok.RequiredArgsConstructor;

/** 
 * Implementación del caso de uso para cambiar el estado de un pedido.
 * Tiene el siguiente método:
 * - changeEstadoPedido(Integer id, EstadoPedido nuevoEstado): Cambia el estado de un pedido existente.
 */
@Service 
@RequiredArgsConstructor 
public class ChangeEstadoUseCaseImpl implements ChangeEstadoUseCase {
    private final PedidoRepositoryPort pedidoRepository;


    /**
     * Cambia el estado de un pedido existente.
     * @param id - El ID del pedido cuyo estado se desea cambiar.
     * @param nuevoEstado - El nuevo estado que se desea asignar al pedido.
     * @throws ResourceNotFoundException si el pedido con el ID proporcionado no existe.
     */
    @Transactional
    public void changeEstadoPedido(Integer id, EstadoPedido nuevoEstado) {
        if (pedidoRepository.findById(id).isEmpty()) 
            throw new ResourceNotFoundException("Pedido no encontrado con ID: " + id);
        
        pedidoRepository.updateEstado(id, nuevoEstado);
    }
    
}
