package com.cavosh.cafebackend.auth.application.usecases;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.RegisterUseCase;
import com.cavosh.cafebackend.auth.domain.port.out.PasswordEncoderPort;
import com.cavosh.cafebackend.auth.domain.port.out.UsuarioRepositoryPort;
import com.cavosh.cafebackend.global.domain.exception.AlreadyExistsException;
import com.cavosh.cafebackend.global.domain.exception.BusinessRuleException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * RegisterUseCaseImpl: Clase que implementa la interfaz RegisterUseCase y proporciona la funcionalidad de registro de usuarios.
 * Esta clase se encarga de validar los datos de registro, verificar la existencia de un usuario con el mismo correo electrónico y crear un nuevo usuario en el sistema.
 */
@Service
@RequiredArgsConstructor
public class RegisterUseCaseImpl implements RegisterUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;

    /**
     * Metodo para registrar un nuevo usuario.
     * @param command - Comando con los datos del usuario a registrar
     * @return - Usuario registrado
     */
    @Transactional
    public Usuario register(RegisterCommand command) {
        //Validar la contraseñas
        if(!command.password().equals(command.confirmPassword()))
            throw new BusinessRuleException("Las contraseñas ingresadas no coinciden");

        String emailLimpio = command.email().toLowerCase().trim();
        if(usuarioRepository.exitsByEmail(emailLimpio))
            throw new AlreadyExistsException("Ya existe una cuenta registrada con ese correo");

        String hash = passwordEncoder.encode(command.password());
        Usuario nuevoUsuario = Usuario.nuevoCliente(command.fullName(),emailLimpio,hash);

        return usuarioRepository.save(nuevoUsuario);
    }
}
