package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ReenviarCodigoRequest(
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de correo no válido")
        String email
) {}