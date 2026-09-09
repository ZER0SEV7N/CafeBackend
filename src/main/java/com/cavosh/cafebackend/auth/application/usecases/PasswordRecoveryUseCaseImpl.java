package com.cavosh.cafebackend.auth.application.usecases;

import com.cavosh.cafebackend.auth.domain.model.TokenRecuperacion;
import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.PasswordRecoveryUseCase;
import com.cavosh.cafebackend.auth.domain.port.out.EmailSenderPort;
import com.cavosh.cafebackend.auth.domain.port.out.PasswordEncoderPort;
import com.cavosh.cafebackend.auth.domain.port.out.TokenRecuperacionRepositoryPort;
import com.cavosh.cafebackend.auth.domain.port.out.UsuarioRepositoryPort;
import com.cavosh.cafebackend.global.domain.exception.BusinessRuleException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * PasswordRecoveryUseCaseImpl: Clase que implementa la interfaz PasswordRecoveryUseCase y proporciona la funcionalidad de recuperación de contraseña.
 * Esta clase se encarga de manejar la solicitud de recuperación de contraseña, generar tokens de recuperación, 
 * enviar correos electrónicos y restablecer la contraseña del usuario.    
 */
@Service
@RequiredArgsConstructor
public class PasswordRecoveryUseCaseImpl implements PasswordRecoveryUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final TokenRecuperacionRepositoryPort tokenRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final EmailSenderPort emailSender;

    private static final long MINUTOS_EXPIRACION = 15;

    /**
     * Metodo para solicitar la recuperación de contraseña de un usuario.
     * Manda un correo al usuario con un token de recuperación si el email existe en la base de datos.
     * @param command - Requiere del Email
     */
    @Transactional
    public void solicitarRecuperacion(SolicitarRecuperacionCommand command) {
        String email = command.email().trim().toLowerCase();

        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            if(usuario.password() == null)
                throw new BusinessRuleException("Las cuentas asociadas a redes sociales no utilizan contraseña.");

            tokenRepository.invalidatePreviousTokens(usuario.id());

            TokenRecuperacion nuevoToken = TokenRecuperacion.crear(usuario.id(), MINUTOS_EXPIRACION);
            TokenRecuperacion guardado = tokenRepository.save(nuevoToken);

            emailSender.sendCorreoRecuperacion(usuario.email(), usuario.fullName(), guardado.token());
        });
    }

    /**
     * Metodo para restablecer la contraseña
     * Válida que las contraseñas coincidan, que el token sea válido y que el usuario exista.
     * Actualiza la contraseña del usuario y marca el token como usado.
     * @param command - Requiere del token, la nueva contraseña y la confirmación de la nueva contraseña
     */
    @Transactional
    public void restablecerPassword(RestablecerPasswordCommand command) {
        if (!command.nuevaPassword().equals(command.confirmarPassword()))
            throw new BusinessRuleException("Las contraseñas no coinciden");

        //Buscar el token de recuperación en la base de datos
        TokenRecuperacion token = tokenRepository.findByToken(command.token()).orElseThrow(() -> new ResourceNotFoundException("El token de recuperación no es válido o no existe"));

        if (!token.esValido())
            throw new BusinessRuleException("El token de recuperación ha expirado o ya fue utilizado");

        //Buscar el usuario asociado al token
        Usuario usuario = usuarioRepository.findById(token.usuarioId()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        String hash = passwordEncoder.encode(command.nuevaPassword());

        Usuario usuarioActualizado = new Usuario(
                usuario.id(),
                usuario.fullName(),
                usuario.email(),
                hash,
                usuario.rol(),
                usuario.proveedor(),
                usuario.puntosRecompensa(),
                usuario.activo(),
                usuario.createdAt(),
                Instant.now()
        );

        usuarioRepository.save(usuarioActualizado);

        TokenRecuperacion tokenUsado = new TokenRecuperacion(
                token.id(),
                token.usuarioId(),
                token.token(),
                token.expiracion(),
                true,
                token.createdAt()
        );
        tokenRepository.save(tokenUsado);
    }
}
