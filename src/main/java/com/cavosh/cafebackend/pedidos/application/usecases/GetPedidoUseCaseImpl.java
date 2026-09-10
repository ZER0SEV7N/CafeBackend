package com.cavosh.cafebackend.pedidos.application.usecases;

import com.cavosh.cafebackend.global.domain.exception.BusinessRuleException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.pedidos.domain.model.Pedido;
import com.cavosh.cafebackend.pedidos.domain.ports.in.GetPedidoUseCase;
import com.cavosh.cafebackend.pedidos.domain.ports.out.PedidoRepositoryPort;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del caso de uso para la gestión de pedidos.
 * Tiene los siguientes métodos:
 *  - getPedidoById(Integer id, Integer usuarioId, boolean isStaff): Obtiene un pedido por su ID.
 *  - listPedidosByUsuarioId(Integer usuarioId): Lista todos los pedidos de un usuario
 */
@Service
@RequiredArgsConstructor
public class GetPedidoUseCaseImpl implements GetPedidoUseCase {
    
    private final PedidoRepositoryPort pedidoRepository;

    /** 
     * Obtiene un pedido por su ID.
     * @param id El ID del pedido.
     * @param usuarioId El ID del usuario.
     * @param isStaff Indica si el usuario es staff.
     * @return El pedido encontrado.
     */
    @Transactional(readOnly = true)
    public Pedido getPedidoById(Integer id, Integer usuarioId, boolean isStaff) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));

        //Validar que el usuario tenga permiso para ver el pedido
        if(!isStaff && !pedido.usuarioId().equals(usuarioId)) 
            throw new BusinessRuleException("No tiene permiso para ver este pedido.");

        return pedido;
    }

    /** 
     * Lista todos los pedidos de un usuario.
     * @param usuarioId El ID del usuario.
     * @return La lista de pedidos del usuario.
     */
    @Transactional(readOnly = true)
    public List<Pedido> listPedidosByUsuarioId(Integer usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId);
    }    

}
