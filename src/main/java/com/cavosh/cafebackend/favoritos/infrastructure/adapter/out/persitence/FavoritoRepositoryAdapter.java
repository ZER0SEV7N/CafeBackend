package com.cavosh.cafebackend.favoritos.infrastructure.adapter.out.persitence;

import com.cavosh.cafebackend.favoritos.domain.ports.out.FavoritoRepositoryPort;
import com.cavosh.cafebackend.favoritos.infrastructure.adapter.out.persitence.entity.FavoritoEntity;
import com.cavosh.cafebackend.favoritos.infrastructure.adapter.out.persitence.entity.FavoritoId;
import com.cavosh.cafebackend.favoritos.infrastructure.adapter.out.persitence.repository.FavoritoRepository;
import com.cavosh.cafebackend.productos.domain.model.Producto;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoritoRepositoryAdapter implements FavoritoRepositoryPort {

    private final FavoritoRepository favoritoRepository;
    private final ProductoRepository productoRepository;
    private final ProductoMapper mapper;

    public void addFavorito(Integer usuarioId, Integer productoId) {
        ProductoEntity productoRef = productoRepository.getReferenceById(productoId);

        favoritoRepository.save(FavoritoEntity.builder()
                .id(new FavoritoId(usuarioId, productoId))
                .producto(productoRef)
                .createdAt(Instant.now())
            .build());
    }

    public void deleteFavorito(Integer usuarioId, Integer productoId){
        favoritoRepository.deleteByUsuarioIdAndProductoId(usuarioId, productoId);
    }

    public boolean existsByUsuarioIdAndProductoId(Integer usuarioId, Integer productoId) {
        return favoritoRepository.existsByIdUsuarioIdAndIdProductoId(usuarioId, productoId);
    }

    public List<Producto> findProductosFavoritosByUsuarioId(Integer usuarioId){
        return favoritoRepository.findProductosFavoritosByUsuarioId(usuarioId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
