package com.cavosh.cafebackend.favoritos.infrastructure.adapter.out.persitence.repository;

import com.cavosh.cafebackend.favoritos.infrastructure.adapter.out.persitence.entity.FavoritoEntity;
import com.cavosh.cafebackend.favoritos.infrastructure.adapter.out.persitence.entity.FavoritoId;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FavoritoRepository extends JpaRepository<FavoritoEntity, FavoritoId> {

    boolean existsByIdUsuarioIdAndIdProductoId(Integer usuarioId, Integer productoId);

    @Modifying
    @Query("DELETE FROM FavoritoEntity f WHERE f.id.usuarioId = :usuarioId AND f.id.productoId = :productoId")
    void deleteByUsuarioIdAndProductoId(@Param("usuarioId") Integer usuarioId, @Param("productoId") Integer productoId);

    @Query("SELECT f.producto FROM FavoritoEntity f WHERE f.id.usuarioId = :usuarioId AND f.producto.activo = true ORDER BY f.createdAt DESC")
    List<ProductoEntity> findProductosFavoritosByUsuarioId(@Param("usuarioId") Integer usuarioId);
}
