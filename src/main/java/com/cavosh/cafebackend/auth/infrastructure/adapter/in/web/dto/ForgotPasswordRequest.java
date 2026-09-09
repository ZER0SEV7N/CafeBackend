package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(
        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El formato de correo no es válido")
        String email
) {}