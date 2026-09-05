package com.cavosh.cafebackend.favoritos.domain.ports.in;

import com.cavosh.cafebackend.productos.domain.model.Producto;

import java.util.List;

public interface FavoritoUseCase {
    void addFavorito(Integer usuarioId, Integer productoId);
    void deleteFavorito(Integer usuarioId, Integer productoId);
    boolean isFavorito(Integer usuarioId, Integer productoId);
    List<Producto> listFavoritos(Integer usuarioId);
}
