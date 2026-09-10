package com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.entity.PedidoEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Integer>{
    
    List<PedidoEntity> findByUsuarioIdOrderByCreatedAtDesc(Integer usuarioId);

    @Query("SELECT p FROM PedidoEntity p WHERE p.codigoOrden = :codigoOrden")
    PedidoEntity findByCodigoOrden(@Param("codigoOrden") String codigoOrden);

    @Modifying
    @Query("UPDATE PedidoEntity p SET p.estado = :nuevoEstado WHERE p.id = :id")
    void changeEstado(@Param("id") Integer id, @Param("nuevoEstado") EstadoPedido nuevoEstado);
}
