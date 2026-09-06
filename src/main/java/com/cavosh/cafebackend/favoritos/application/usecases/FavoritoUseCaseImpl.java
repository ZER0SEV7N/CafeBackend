package com.cavosh.cafebackend.favoritos.application.usecases;

import com.cavosh.cafebackend.favoritos.domain.ports.in.FavoritoUseCase;
import com.cavosh.cafebackend.favoritos.domain.ports.out.FavoritoRepositoryPort;
import com.cavosh.cafebackend.global.domain.exception.AlreadyExistsException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import com.cavosh.cafebackend.productos.domain.model.Producto;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritoUseCaseImpl implements FavoritoUseCase {

    private final FavoritoRepositoryPort favoritoRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public void addFavorito(Integer usuarioId, Integer productoId) {
        if(!productoRepository.existsById(productoId))
            throw new ResourceNotFoundException("El producto indicado no existe con ID: " + productoId);

        if(favoritoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId))
            throw new AlreadyExistsException("El producto ya se encuentra en la lista de favoritos");

        favoritoRepository.addFavorito(usuarioId, productoId);
    }

    @Transactional
    public void deleteFavorito(Integer usuarioId, Integer productoId) {
        if(!favoritoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId))
            throw new ResourceNotFoundException("El producto no se encuentra en la lista de favoritos");

        favoritoRepository.deleteFavorito(usuarioId, productoId);
    }

    @Transactional(readOnly = true)
    public List<Producto> listFavoritos(Integer usuarioId) {
        return favoritoRepository.findProductosFavoritosByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public boolean isFavorito(Integer usuarioId, Integer productoId) {
        return favoritoRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId);
        }
}
