package com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.cavosh.cafebackend.pedidos.domain.model.Pedido;
import com.cavosh.cafebackend.pedidos.domain.ports.out.PedidoRepositoryPort;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.entity.PedidoEntity;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.mapper.PedidoMapper;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

/** 
 * Adaptador de repositorio para la persistencia de pedidos.
 * Implementa el puerto de salida PedidoRepositoryPort, proporcionando la lógica para interactuar con la
 * base de datos a través de PedidoRepository y mapeando entre entidades de persistencia y modelos de dominio usando PedidoMapper.
 */
@Component 
@RequiredArgsConstructor 
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {
 
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper mapper;

    /**
     * Guarda un nuevo pedido en la base de datos.
     * @param pedido El objeto Pedido a guardar.
     * @return El objeto Pedido guardado, mapeado desde la entidad de persistencia.
     */
    public Pedido save(Pedido pedido) {
        PedidoEntity entity = mapper.toEntity(pedido);
        if(entity.getDetalles() != null) {
            entity.getDetalles().forEach(detalle -> {
                detalle.setPedido(entity);
                if(detalle.getOpciones() != null )
                    detalle.getOpciones().forEach(opcion -> opcion.setDetallePedido(detalle));
            });
        }

        PedidoEntity savedEntity = pedidoRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /** 
     * Busca un pedido por su ID en la base de datos.
     * @param id El ID del pedido a buscar.
     * @return Un Optional que contiene el pedido encontrado, mapeado desde la entidad de persistencia, o vacío si no se encuentra.
     */
    public Optional<Pedido> findById(Integer id) { return pedidoRepository.findById(id).map(mapper::toDomain); }

    /**
     * Obtiene todos los pedidos de un usuario específico, ordenados por fecha de creación descendente.
     * @param usuarioId El ID del usuario cuyos pedidos se desean obtener.
     * @return Una lista de pedidos del usuario, mapeados desde las entidades de persistencia.
     */
    public List<Pedido> findByUsuarioIdOrderByCreatedAtDesc(Integer usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }


    /**
     * Busca un pedido por su código de orden en la base de datos.
     * @param codigoOrden El código de orden del pedido a buscar.
     * @return Un Optional que contiene el pedido encontrado, mapeado desde la entidad de persistencia, o vacío si no se encuentra.
     */
    public Optional<Pedido> findByCodigoOrden(String codigoOrden) {
        PedidoEntity entity = pedidoRepository.findByCodigoOrden(codigoOrden);
        return Optional.ofNullable(entity).map(mapper::toDomain);
    }

    /**
     * Actualiza el estado de un pedido existente en la base de datos.
     * @param id El ID del pedido cuyo estado se desea actualizar. 
     * @param nuevoEstado El nuevo estado que se desea asignar al pedido.
     */
    public void updateEstado(Integer id, com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido nuevoEstado) {
        pedidoRepository.changeEstado(id, nuevoEstado);
    }
}
