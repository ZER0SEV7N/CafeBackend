package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.*;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "Endpoints para autenticación, registro y recuperación de contraseñas")
public interface AuthDoc {

    @Operation(summary = "Iniciar sesión", description = "Autentica credenciales y devuelve los datos del usuario con su token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "422", description = "La cuenta está desactivada")
    })
    ResponseEntity<ResponseGlobal<AuthResponse>> login(LoginRequest request);

    @Operation(summary = "Solicitar recuperación de contraseña", description = "Envía un correo electrónico con un token si el usuario existe.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Solicitud procesada") })
    ResponseEntity<ResponseGlobal<Void>> forgotPassword(ForgotPasswordRequest request);

    @Operation(summary = "Restablecer contraseña con token", description = "Valida el token enviado por correo y actualiza la contraseña.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Token inválido, expirado o contraseñas no coinciden"),
            @ApiResponse(responseCode = "404", description = "Token o usuario no encontrado")
    })
    
    ResponseEntity<ResponseGlobal<Void>> resetPassword(ResetPasswordRequest request);
    @Operation(summary = "Registrar nuevo usuario", description = "Crea la cuenta inactiva y envía el OTP de 4 dígitos al correo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Código enviado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El correo ya está registrado")
    })
    ResponseEntity<ResponseGlobal<Void>> register(@Valid @RequestBody RegisterRequest request);

    @Operation(summary = "Verificar código OTP", description = "Activa la cuenta y entrega el token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta verificada e inicio de sesión"),
            @ApiResponse(responseCode = "400", description = "Código incorrecto o expirado")
    })
    ResponseEntity<ResponseGlobal<AuthResponse>> verificarCodigo(@Valid @RequestBody VerificarCodigoRequest request);

    @Operation(summary = "Reenviar código OTP", description = "Genera un nuevo código de 4 dígitos")
    ResponseEntity<ResponseGlobal<Void>> reenviarCodigo(@Valid @RequestBody ReenviarCodigoRequest request);
}