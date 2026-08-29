package com.cavosh.cafebackend.auth.application.usecases;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.RegisterUseCase;
import com.cavosh.cafebackend.auth.domain.port.out.PasswordEncoderPort;
import com.cavosh.cafebackend.auth.domain.port.out.UsuarioRepositoryPort;
import com.cavosh.cafebackend.global.domain.exception.BusinessRuleException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class registerUseCaseImpl implements RegisterUseCase {

    private final UsuarioRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;

    @Transactional
    public Usuario registrar(RegistrarCommand command) {
        //Validar la contraseñas
        if(!command.password().equals(command.confirmPassword()))
            throw new BusinessRuleException()
    }
}
