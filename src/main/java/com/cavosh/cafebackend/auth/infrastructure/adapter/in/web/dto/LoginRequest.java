package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de correo no válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password
){
}
