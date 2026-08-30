package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.out.UsuarioRepositoryPort;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.mapper.UsuarioMapper;
import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioAdapter implements UsuarioRepositoryPort {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;

    //Funcion para obtener el usuario mediante su ID
    public Optional<Usuario> findById(Integer id){
        return usuarioRepository.findById(id).map(mapper::toDomain);
    }

    //Funcion para obtener el usuario mediante su Email
    public Optional<Usuario> findByEmail(String correo){
        return usuarioRepository.findByEmailIgnoreCase(correo).map(mapper::toDomain);
    }

    //Funcion para comprobar que el email realmente exista
    public boolean exitsByEmail(String correo){
        return usuarioRepository.existsByEmailIgnoreCase(correo);
    }

    //Funcion para guardar a los usuarios
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        UsuarioEntity guardado = usuarioRepository.save(entity);
        return mapper.toDomain(guardado);
    }

}
