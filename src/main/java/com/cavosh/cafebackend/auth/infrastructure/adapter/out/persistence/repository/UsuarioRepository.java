package com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.repository;

import com.cavosh.cafebackend.auth.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UsuarioRepository : Interfaz que define los métodos de acceso a la base de datos para la entidad UsuarioEntity.
 * Extiende JpaRepository para obtener los métodos CRUD básicos.
 */
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    /**
     * Busca un usuario por su correo electrónico, ignorando mayúsculas y minúsculas.
     * @param email - Correo electrónico del usuario
     * @return - Optional que contiene el UsuarioEntity si se encuentra, o vacío si no se encuentra.
     */
    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);

    /**
     * Verifica si existe un usuario con el correo electrónico proporcionado, ignorando mayúsculas y minúsculas.
     * @param email - Correo electrónico del usuario
     * @return - true si existe un usuario con el correo electrónico proporcionado, false en caso contrario.
     */
    boolean existsByEmailIgnoreCase(String email);
}
