package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.LoginUseCase;
import com.cavosh.cafebackend.auth.domain.port.in.PasswordRecoveryUseCase;
import com.cavosh.cafebackend.auth.domain.port.in.RegisterUseCase;
import com.cavosh.cafebackend.auth.domain.port.out.TokenProviderPort;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.doc.AuthDoc;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.ForgotPasswordRequest;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.ResetPasswordRequest;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.VerificarCodigoRequest;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.AuthResponse;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.LoginRequest;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.ReenviarCodigoRequest;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.RegisterRequest;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de Auth.
 * Endpoints:
 *  - POST /api/auth/register: Registra un nuevo usuario.
 *  - POST /api/auth/login: Inicia sesión y devuelve un token JWT.
 *  - POST /api/auth/forgot-password: Solicita recuperación de contraseña.
 *  - POST /api/auth/reset-password: Restablece la contraseña con un token.
 *  - POST /api/auth/verificar-codigo: Verifica el código OTP enviado al correo del usuario.
 *  - POST /api/auth/reenviar-codigo: Reenvía el código OTP al correo del usuario.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthDoc {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final PasswordRecoveryUseCase passwordRecoveryUseCase;
    private final TokenProviderPort tokenProviderPort;

    /**
     * EndPoint para registrar un nuevo cliente
     * @Post /api/auth/register
     * - Recibe un objeto RegisterRequest con los datos del usuario a registrar
     * @param request : dto. con los datos del usuario a registrar
     * @return ResponseEntity con el estado de la operación y el objeto AuthResponse con los datos del usuario registrado y el token JWT
     */
    @PostMapping("/registrar")
    public ResponseEntity<ResponseGlobal<Void>> register(@Valid @RequestBody RegisterRequest request) {
        var command = new RegisterUseCase.RegisterCommand(
                request.fullName(),
                request.email(),
                request.password()
        );

        registerUseCase.register(command);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseGlobal.success(
                        HttpStatus.CREATED.value(), 
                        null, 
                        "Usuario registrado. Te hemos enviado un código de verificación a tu correo"
                ));
    }

    /**
     * EndPoint para iniciar sesión
     * @Post /api/auth/login
     * - Recibe un objeto LoginRequest con los datos del usuario para iniciar sesión
     * @param request : dto. con los datos del usuario para iniciar sesión
     * @return ResponseEntity con el estado de la operación y el objeto AuthResponse con los datos del usuario autenticado y el token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseGlobal<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        var command = new LoginUseCase.LoginCommand(request.email(), request.password());
        var loginResult = loginUseCase.login(command);

        AuthResponse authResponse = AuthResponse.from(loginResult.token(), loginResult.usuario());

        return ResponseEntity.ok(ResponseGlobal.success(authResponse, "Inicio de sesión exitoso"));
    }

    /**
     * Endpoint para enviar la solicitud de recuperacion de contraseña
     * @Post /api/auth/forgot-password
     * @param request - dto. con el email del usuario que solicita la recuperación de contraseña
     * @return ResponseEntity con el estado de la operación
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseGlobal<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        passwordRecoveryUseCase.solicitarRecuperacion(
                new PasswordRecoveryUseCase.SolicitarRecuperacionCommand(request.email())
        );
        return ResponseEntity.ok(ResponseGlobal.success(null, "Si el correo existe, se ha enviado un token de recuperación."));
    }

    /**
     * Endpoint para restablecer la contraseña
     * @Post /api/auth/reset-password
     * @param request - dto. con el token y la nueva contraseña
     * @return ResponseEntity con el estado de la operación
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseGlobal<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordRecoveryUseCase.restablecerPassword(
                new PasswordRecoveryUseCase.RestablecerPasswordCommand(
                        request.token(),
                        request.newPassword(),
                        request.confirmPassword()
                )
        );
        return ResponseEntity.ok(ResponseGlobal.success(null, "Contraseña actualizada exitosa"));
    }

    /**
     * Endpoint para verificar el código OTP enviado al correo del usuario
     * @Post /api/auth/verificar-codigo
     * @param request - dto. con el email y el código OTP
     * @return ResponseEntity con el estado de la operación y el objeto AuthResponse con los datos del usuario autenticado y el token JWT
     */
    @PostMapping("/verificar-codigo")
    public ResponseEntity<ResponseGlobal<AuthResponse>> verificarCodigo(@Valid @RequestBody VerificarCodigoRequest request) {
        Usuario usuarioVerificado = registerUseCase.verifyCode(request.email(), request.codigo());
        String token = tokenProviderPort.generateToken(usuarioVerificado);

        AuthResponse authResponse = AuthResponse.from(token, usuarioVerificado);

        return ResponseEntity.ok(ResponseGlobal.success(authResponse, "Cuenta verificada con éxito"));
    }

    /**
     * Endpoint para reenviar el código OTP al correo del usuario
     * @Post /api/auth/reenviar-codigo
     * @param request - dto. con el email del usuario
     * @return ResponseEntity con el estado de la operación
     */
    @PostMapping("/reenviar-codigo")
    public ResponseEntity<ResponseGlobal<Void>> reenviarCodigo(@Valid @RequestBody ReenviarCodigoRequest request) {
        registerUseCase.resendCode(request.email());
        return ResponseEntity.ok(ResponseGlobal.success(null, "Nuevo código de verificación enviado"));
    }
}
