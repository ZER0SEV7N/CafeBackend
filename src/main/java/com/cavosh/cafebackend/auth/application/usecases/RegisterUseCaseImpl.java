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

@Service
@RequiredArgsConstructor
public class RegisterUseCaseImpl implements RegisterUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;

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
