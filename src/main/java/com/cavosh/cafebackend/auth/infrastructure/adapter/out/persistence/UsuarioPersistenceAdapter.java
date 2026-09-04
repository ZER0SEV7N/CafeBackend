package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.out.UsuarioRepositoryPort;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.mapper.UsuarioMapper;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Componente que adapta la interfaz UsuarioRepositoryPort para interactuar con la base de datos utilizando JPA.
 * Utiliza un objeto UsuarioRepository para realizar las operaciones de persistencia y un objeto UsuarioMapper
 * para mapear entre las entidades de la base de datos y los objetos de dominio.
 */
@Component
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;

    /**
     * Metodo para obtener el usuario mediante su ID.
     * @param id - ID del usuario
     * @return El usuario si existe, Optional.empty() en caso contrario.
     */
    public Optional<Usuario> findById(Integer id){
        return usuarioRepository.findById(id).map(mapper::toDomain);
    }

    /**
     * Metodo para obtener el usuario mediante su Email.
     * @param correo - Email del usuario
     * @return El usuario si existe, Optional.empty() en caso contrario.
     */
    public Optional<Usuario> findByEmail(String correo){
        return usuarioRepository.findByEmailIgnoreCase(correo).map(mapper::toDomain);
    }
    /**
     * Metodo para comprobar que el email realmente exista.
     * @param correo - Email del usuario
     * @return true si el email existe, false en caso contrario.
     */
    public boolean exitsByEmail(String correo){
        return usuarioRepository.existsByEmailIgnoreCase(correo);
    }

    /**
     * Metodo para guardar a los usuarios.
     * @param usuario - Objeto de tipo Usuario
     * @return El usuario guardado.
     */
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        UsuarioEntity guardado = usuarioRepository.save(entity);
        return mapper.toDomain(guardado);
    }
}
