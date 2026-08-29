package com.cavosh.cafebackend.auth.domain.port.out;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {
    Usuario save (Usuario usuario);
    Optional<Usuario> findByEmail (String email);
    Optional<Usuario> findById(Integer id);
    boolean exitsByEmail(String email);
}
