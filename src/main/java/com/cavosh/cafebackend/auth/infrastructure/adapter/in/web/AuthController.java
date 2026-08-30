package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.LoginUseCase;
import com.cavosh.cafebackend.auth.domain.port.in.RegisterUseCase;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.AuthResponse;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.LoginRequest;
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
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;

    /**
     * EndPoint para registrar un nuevo cliente
     * - Post /api/auth/register
     * - Recibe un objeto RegisterRequest con los datos del usuario a registrar
     * @param request : dto con los datos del usuario a registrar
     * @return ResponseEntity con el estado de la operación y el objeto AuthResponse con los datos del usuario registrado y el token JWT
     */
    @PostMapping("/registrar")
    public ResponseEntity<ResponseGlobal<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        var command = new RegisterUseCase.RegisterCommand(
                request.fullName(),
                request.email(),
                request.password(),
                request.confirmPassword()
        );
        Usuario usuarioCreado = registerUseCase.register(command);

        //Generamos la sesión para el usuario recién registrado
        var loginResult = loginUseCase.login(new LoginUseCase.LoginCommand(request.email(), request.password()));

        AuthResponse authResponse = AuthResponse.from(loginResult.token(), usuarioCreado);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseGlobal.success(HttpStatus.CREATED.value(), authResponse, "Usuario registrado exitosamente"));
    }

    /**
     * EndPoint para iniciar sesión
     * - Post /api/auth/login
     * - Recibe un objeto LoginRequest con los datos del usuario para iniciar sesión
     * @param request : dto con los datos del usuario para iniciar sesión
     * @return ResponseEntity con el estado de la operación y el objeto AuthResponse con los datos del usuario autenticado y el token JWT
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseGlobal<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        var command = new LoginUseCase.LoginCommand(request.email(), request.password());
        var loginResult = loginUseCase.login(command);

        AuthResponse authResponse = AuthResponse.from(loginResult.token(), loginResult.usuario());

        return ResponseEntity.ok(ResponseGlobal.success(authResponse, "Inicio de sesión exitoso"));
    }
}
