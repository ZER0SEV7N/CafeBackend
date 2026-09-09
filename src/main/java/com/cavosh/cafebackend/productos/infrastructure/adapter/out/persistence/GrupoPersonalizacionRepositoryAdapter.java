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

/**
 * Adaptador para el repositorio de grupos de personalización.
 * Implementa las operaciones definidas en el puerto de repositorio de grupos de personalización.
 * 
 */
@Component
@RequiredArgsConstructor
public class GrupoPersonalizacionRepositoryAdapter implements GrupoPersonalizacionRepositoryPort {

    private final GrupoPersonalizacionRepository grupoRepository;
    private final ProductoMapper mapper;

    /** 
     * Busca todos los grupos de personalización en la base de datos y los convierte a objetos de dominio.
     * @return Lista de grupos de personalización en el dominio.
     */
    public List<GrupoPersonalizacion> findAll() {
        return grupoRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    /** 
     * Busca un grupo de personalización por su ID en la base de datos y lo convierte a un objeto de dominio.
     * @param id El ID del grupo de personalización a buscar.
     * @return El grupo de personalización encontrado, o Optional.empty() si no se encuentra.
     */
    public Optional<GrupoPersonalizacion> findById(Integer id) {
        return grupoRepository.findById(id).map(mapper::toDomain);
    }

    /** 
     * Verifica si existe un grupo de personalización con el nombre dado en la base de datos.
     * @param nombreGrupo El nombre del grupo de personalización a verificar.
     * @return true si existe un grupo de personalización con el nombre dado, false en caso contrario.
     */
    public boolean existsByNombreGrupoIgnoreCase(String nombreGrupo) {
        return grupoRepository.existsByNombreGrupoIgnoreCase(nombreGrupo);
    }

    /**
     * Guarda un grupo de personalización en la base de datos y lo convierte a un objeto de dominio.
     * @param grupo El grupo de personalización a guardar.
     * @return El grupo de personalización guardado en el dominio.
     */
    public GrupoPersonalizacion save(GrupoPersonalizacion grupo) {
        GrupoPersonalizacionEntity entity = mapper.toEntity(grupo);

        //Mantiene la consistencia de llaves foráneas para las opciones secundarias
        if (entity.getOpciones() != null && entity.getId() != null)
            entity.getOpciones().forEach(opcion -> opcion.setGrupoId(entity.getId()));


        GrupoPersonalizacionEntity guardado = grupoRepository.save(entity);
        return mapper.toDomain(guardado);
    }
}