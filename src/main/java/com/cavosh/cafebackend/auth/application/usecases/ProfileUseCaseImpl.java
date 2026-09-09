package com.cavosh.cafebackend.auth.application.usecases;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.ProfileUseCase;
import com.cavosh.cafebackend.auth.domain.port.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Implementacion del caso de uso relacionado con obtener el perfil y actualizarlo
 */
@Service
@RequiredArgsConstructor
public class ProfileUseCaseImpl implements ProfileUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final ObjectProvider<ProfileUseCase> profileUseCase;

    /**
     * Metodo para obtener el perfil de un usuario por su id
     * @param usuarioId - id del usuario
     * @return Retorna el propio usuario o un error de no encontrado
     */
    @Transactional(readOnly = true)
    public Usuario getProfile(Integer usuarioId){
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    /**
     * Metodo para actualizar el perfil de usuario
     * @param command - Comando con los datos a actualizar
     *                Requieres: id de usuario (INT)
     *                           nomber de usuario (STRING)
     * @return - Retorna la actualizacion de usuario
     */
    @Transactional
    public Usuario updateMyProfile(UpdateProfileCommand command) {
        Usuario usuario = profileUseCase.getObject().getProfile(command.usuarioId());

        Usuario usuarioActualizado = new Usuario(
                usuario.id(),
                command.fullName().trim(),
                usuario.email(),
                usuario.password(),
                usuario.rol(),
                usuario.proveedor(),
                usuario.puntosRecompensa(),
                usuario.activo(),
                usuario.createdAt(),
                Instant.now()
        );

        return usuarioRepository.save(usuarioActualizado);
    }
}
