package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.productos.domain.model.GrupoPersonalizacion;
import com.cavosh.cafebackend.productos.domain.ports.out.GrupoPersonalizacionRepositoryPort;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.GrupoPersonalizacionEntity;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.GrupoPersonalizacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GrupoPersonalizacionRepositoryAdapter implements GrupoPersonalizacionRepositoryPort {

    private final GrupoPersonalizacionRepository grupoRepository;
    private final ProductoMapper mapper;

    @Override
    public List<GrupoPersonalizacion> findAll() {
        return grupoRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<GrupoPersonalizacion> findById(Integer id) {
        return grupoRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsByNombreGrupoIgnoreCase(String nombreGrupo) {
        return grupoRepository.existsByNombreGrupoIgnoreCase(nombreGrupo);
    }

    @Override
    public GrupoPersonalizacion save(GrupoPersonalizacion grupo) {
        GrupoPersonalizacionEntity entity = mapper.toEntity(grupo);

        //Mantiene la consistencia de llaves foráneas para las opciones secundarias
        if (entity.getOpciones() != null && entity.getId() != null)
            entity.getOpciones().forEach(opcion -> opcion.setGrupoId(entity.getId()));


        GrupoPersonalizacionEntity guardado = grupoRepository.save(entity);
        return mapper.toDomain(guardado);
    }
}