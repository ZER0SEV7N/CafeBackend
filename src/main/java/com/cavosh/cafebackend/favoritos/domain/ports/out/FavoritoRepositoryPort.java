package com.cavosh.cafebackend.favoritos.domain.ports.out;

import com.cavosh.cafebackend.productos.domain.model.Producto;

import java.util.List;

public interface FavoritoRepositoryPort {
    void addFavorito(Integer usuarioId, Integer productoId);
    void deleteFavorito(Integer usuarioId, Integer productoId);
    boolean existsByUsuarioIdAndProductoId(Integer usuarioId, Integer productoId);
    List<Producto> findProductosFavoritosByUsuarioId(Integer usuarioId);
}
