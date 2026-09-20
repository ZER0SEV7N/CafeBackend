package com.cavosh.cafebackend.auth.application.usecases;

import com.cavosh.cafebackend.auth.domain.model.AuthProveedor;
import com.cavosh.cafebackend.auth.domain.model.Rol;
import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.RegisterUseCase;
import com.cavosh.cafebackend.auth.domain.port.out.CodigoVerificacionRepositoryPort;
import com.cavosh.cafebackend.auth.domain.port.out.EmailSenderPort;
import com.cavosh.cafebackend.auth.domain.port.out.PasswordEncoderPort;
import com.cavosh.cafebackend.auth.domain.port.out.UsuarioRepositoryPort;
import com.cavosh.cafebackend.global.domain.exception.AlreadyExistsException;
import com.cavosh.cafebackend.global.domain.exception.BusinessRuleException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.security.SecureRandom;
import java.time.Instant;

import org.springframework.stereotype.Service;

/**
 * RegisterUseCaseImpl: Clase que implementa la interfaz RegisterUseCase y proporciona la funcionalidad de registro de usuarios.
 * Esta clase se encarga de validar los datos de registro, verificar la existencia de un usuario con el mismo correo electrónico y crear un nuevo usuario en el sistema.
 */
@Service
@RequiredArgsConstructor
public class RegisterUseCaseImpl implements RegisterUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final CodigoVerificacionRepositoryPort codigoVerificacionPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final EmailSenderPort emailSenderPort;
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Metodo para registrar un nuevo usuario.
     * @param command - Comando con los datos del usuario a registrar
     * @return - Usuario registrado
     */
    @Transactional
    public void register(RegisterCommand command) {
        if (usuarioRepository.exitsByEmail(command.email())) 
            throw new AlreadyExistsException("El correo ya se encuentra registrado");
        

        Usuario nuevoUsuario = new Usuario(
                null,
                command.fullname(),
                command.email(),
                passwordEncoderPort.encode(command.password()),
                Rol.CLIENTE,
                AuthProveedor.LOCAL,
                0,
                false, //Inactivo hasta que verifique en la app
                Instant.now(),
                Instant.now()
        );

        usuarioRepository.save(nuevoUsuario);

        String codigo = generarCodigoOtp();
        codigoVerificacionPort.registrarNuevoCodigo(command.email(), codigo);
        emailSenderPort.sendVerificationCodeEmail(command.email(), codigo);
    }

    @Override
    @Transactional
    public Usuario verifyCode(String email, String codigo) {
        var resultado = codigoVerificacionPort.verificarCodigo(email, codigo);

        if (resultado.codigoResultado() != 0)  throw new BusinessRuleException(resultado.mensaje());
        
        return usuarioRepository.findById(resultado.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    @Transactional
    public void resendCode(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ese correo"));

        if (Boolean.TRUE.equals(usuario.activo())) throw new BusinessRuleException("La cuenta ya se encuentra activada");
        
        String nuevoCodigo = generarCodigoOtp();
        codigoVerificacionPort.registrarNuevoCodigo(email, nuevoCodigo);
        emailSenderPort.sendVerificationCodeEmail(email, nuevoCodigo);
    }

    private String generarCodigoOtp() {
        return String.valueOf(1000 + secureRandom.nextInt(9000));
    }
}
