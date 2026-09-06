package com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.tarjetas.domain.model.TarjetaUsuario;
import com.cavosh.cafebackend.tarjetas.domain.ports.out.TarjetaRepositoryPort;
import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence.entity.TarjetaUsuarioEntity;
import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence.mapper.TarjetaUsuarioMapper;
import com.cavosh.cafebackend.tarjetas.infrastructure.adapter.out.persistence.repository.TarjetaUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TarjetaRepositoryAdapter implements TarjetaRepositoryPort {

    private final TarjetaUsuarioRepository repository;
    private final TarjetaUsuarioMapper mapper;


    public TarjetaUsuario save(TarjetaUsuario tarjeta) {
        TarjetaUsuarioEntity entity = mapper.toEntity(tarjeta);
        TarjetaUsuarioEntity guardado = repository.save(entity);
        return mapper.toDomain(guardado);
    }

    @Override
    public Optional<TarjetaUsuario> findByIdAndUsuarioId(Integer id, Integer usuarioId) {
        return repository.findByIdAndUsuarioId(id, usuarioId).map(mapper::toDomain);
    }


    public List<TarjetaUsuario> findByUsuarioId(Integer usuarioId) {
        return repository.findByUsuarioIdOrderByPredeterminadoDescCreatedAtDesc(usuarioId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    public void uncheckDefaults(Integer usuarioId) {
        repository.desmarcarPredeterminadas(usuarioId);
    }


    public void deleteByIdAndUsuarioId(Integer id, Integer usuarioId) {
        repository.deleteByIdAndUsuarioId(id, usuarioId);
    }


    public long countByUsuarioId(Integer usuarioId) {
        return repository.countByUsuarioId(usuarioId);
    }
}