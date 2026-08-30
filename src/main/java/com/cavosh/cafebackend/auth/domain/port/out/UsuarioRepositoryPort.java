package com.cavosh.cafebackend.auth.domain.port.out;

import com.cavosh.cafebackend.auth.domain.model.Usuario;

import java.util.Optional;

/**
 * Interfaz del puerto del repositorio de usuario:
 * Tiene todos los metodos necesarios para interactuar con la base de datos
 */
public interface UsuarioRepositoryPort {
    Usuario save (Usuario usuario);
    Optional<Usuario> findByEmail (String email);
    Optional<Usuario> findById(Integer id);
    boolean exitsByEmail(String email);
}
