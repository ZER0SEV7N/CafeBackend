package com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;
import com.cavosh.cafebackend.tienda.domain.port.out.TiendaRepositoryPort;
import com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.mapper.TiendaMapper;
import com.cavosh.cafebackend.tienda.infrastructure.adapter.out.persistence.repository.TiendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TiendaRepositoryAdapter implements TiendaRepositoryPort {

    private final TiendaRepository tiendaRepository;
    private final TiendaMapper mapper;

    /**
     * Busca una cafetería por su ID.
     *
     * @param id El ID de la cafetería a buscar.
     * @return Un Optional que contiene la cafetería si se encuentra, o vacío si no se encuentra.
     */
    public Optional<Cafeteria> findById(Integer id) { return tiendaRepository.findById(id).map(mapper::toDomain); }


    /**
     * Busca todas las cafeterías activas.
     *
     * @return Una lista de cafeterías activas.
     */
    public List<Cafeteria> findAllActive() { return tiendaRepository.findByActivoTrue().stream().map(mapper::toDomain).toList(); }

    /**
     * Busca todas las cafeterías en una ciudad específica.
     *
     * @param city La ciudad en la que buscar las cafeterías.
     * @return Una lista de cafeterías en la ciudad especificada.
     */
    public List<Cafeteria> findByCiudad(String city) { return tiendaRepository.findByCiudadIgnoreCaseAndActivoTrue(city).stream().map(mapper::toDomain).toList(); }

    /**
     * Busca todas las cafeterías más frecuentes por un usuario específico.
     *
     * @param usuarioId El ID del usuario.
     * @return Una lista de cafeterías más frecuentes para el usuario.
     */
    public List<Cafeteria> findFrecuentesPorUsuario(Integer usuarioId) {
        return tiendaRepository.findFrecuentesPorUsuario(usuarioId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
