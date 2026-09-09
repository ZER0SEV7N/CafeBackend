package com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence;
import com.cavosh.cafebackend.productos.domain.model.Escala;
import com.cavosh.cafebackend.productos.domain.ports.out.EscalaRepositoryPort;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.entity.EscalaEntity;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import com.cavosh.cafebackend.productos.infrastructure.adapter.out.persistence.repository.EscalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EscalaRepositoryAdapter implements EscalaRepositoryPort {

    private final EscalaRepository escalaRepository;
    private final ProductoMapper mapper;

    /**
     * Busca todas las escalas en la base de datos y las convierte a objetos de dominio.
     * @return Lista de escalas en el dominio.
     */
    public List<Escala> findAll() {
        return escalaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    /**
     * Busca una escala por su ID en la base de datos y la convierte a un objeto de dominio.
     * @param id El ID de la escala a buscar.
     * @return La escala encontrada, o Optional.empty() si no se encuentra.
     */
    public Optional<Escala> findById(Integer id) { return escalaRepository.findById(id).map(mapper::toDomain); }

    /**
     * Verifica si existe una escala con el nombre dado en la base de datos.
     * @param nombre El nombre de la escala a verificar.
     * @return true si existe una escala con el nombre dado, false en caso contrario.
     */
    public boolean existsByNombreIgnoreCase(String nombre) { return escalaRepository.existsByNombreIgnoreCase(nombre); }

    /**
     * Guarda una escala en la base de datos y la convierte a un objeto de dominio.
     * @param escala La escala a guardar.
     * @return La escala guardada en el dominio.
     */
    public Escala save(Escala escala) {
        EscalaEntity entity = mapper.toEntity(escala);
        EscalaEntity guardado = escalaRepository.save(entity);
        return mapper.toDomain(guardado);
    }
}