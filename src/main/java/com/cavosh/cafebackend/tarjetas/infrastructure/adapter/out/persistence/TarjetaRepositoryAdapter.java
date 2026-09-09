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

/** 
 * Adaptador del repositorio de tarjetas de usuario.
 */
@Component
@RequiredArgsConstructor
public class TarjetaRepositoryAdapter implements TarjetaRepositoryPort {

    private final TarjetaUsuarioRepository repository;
    private final TarjetaUsuarioMapper mapper;

    /** 
     * Guarda una tarjeta de usuario.
     * @param tarjeta La tarjeta de usuario a guardar.
     * @return La tarjeta de usuario guardada.
     */
    public TarjetaUsuario save(TarjetaUsuario tarjeta) {
        TarjetaUsuarioEntity entity = mapper.toEntity(tarjeta);
        TarjetaUsuarioEntity guardado = repository.save(entity);
        return mapper.toDomain(guardado);
    }

    /** 
     * Busca una tarjeta de usuario por su id y el id del usuario.
     * @param id El id de la tarjeta.
     * @param usuarioId El id del usuario.
     * @return La tarjeta de usuario encontrada, si existe.
     */
    public Optional<TarjetaUsuario> findByIdAndUsuarioId(Integer id, Integer usuarioId) {
        return repository.findByIdAndUsuarioId(id, usuarioId).map(mapper::toDomain);
    }

    /** 
     * Busca todas las tarjetas de un usuario ordenadas por predeterminada y fecha de creación.
     * @param usuarioId El id del usuario.
     * @return La lista de tarjetas de usuario encontradas.
     */
    public List<TarjetaUsuario> findByUsuarioId(Integer usuarioId) {
        return repository.findByUsuarioIdOrderByPredeterminadoDescCreatedAtDesc(usuarioId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    /** 
     * Desmarca todas las tarjetas de un usuario como no predeterminadas.
     * @param usuarioId El id del usuario.
     */
    public void uncheckDefaults(Integer usuarioId) { repository.desmarcarPredeterminadas(usuarioId); }

    /** 
     * Elimina una tarjeta de usuario por su id y el id del usuario.
     * @param id El id de la tarjeta.
     * @param usuarioId El id del usuario.
     */
    public void deleteByIdAndUsuarioId(Integer id, Integer usuarioId) { repository.deleteByIdAndUsuarioId(id, usuarioId); }

    /** 
     * Cuenta la cantidad de tarjetas de un usuario.
     * @param usuarioId El id del usuario.
     * @return La cantidad de tarjetas del usuario.
     */
    public long countByUsuarioId(Integer usuarioId) { return repository.countByUsuarioId(usuarioId); }
}