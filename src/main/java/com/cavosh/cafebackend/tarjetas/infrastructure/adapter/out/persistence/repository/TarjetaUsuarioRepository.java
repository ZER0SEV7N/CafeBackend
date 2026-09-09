package com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence.entity.TarjetaUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de tarjetas de usuario:
 * - Listar todas las tarjetas de un usuario ordenadas por predeterminada y fecha de creación
 * - Buscar una tarjeta por su id y el id del usuario
 * - Contar la cantidad de tarjetas de un usuario
 * - Desmarcar todas las tarjetas como predeterminadas para un usuario
 * - Eliminar una tarjeta por su id y el id del usuario
 */
public interface TarjetaUsuarioRepository extends JpaRepository<TarjetaUsuarioEntity, Integer> {

    List<TarjetaUsuarioEntity> findByUsuarioIdOrderByPredeterminadoDescCreatedAtDesc(Integer usuarioId);

    Optional<TarjetaUsuarioEntity> findByIdAndUsuarioId(Integer id, Integer usuarioId);

    long countByUsuarioId(Integer usuarioId);

    @Modifying
    @Query("UPDATE TarjetaUsuarioEntity t SET t.predeterminado = false WHERE t.usuarioId = :usuarioId")
    void desmarcarPredeterminadas(@Param("usuarioId") Integer usuarioId);

    @Modifying
    @Query("DELETE FROM TarjetaUsuarioEntity t WHERE t.id = :id AND t.usuarioId = :usuarioId")
    void deleteByIdAndUsuarioId(@Param("id") Integer id, @Param("usuarioId") Integer usuarioId);
}