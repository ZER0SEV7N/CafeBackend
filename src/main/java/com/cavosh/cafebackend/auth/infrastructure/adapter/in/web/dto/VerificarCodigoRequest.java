package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerificarCodigoRequest(
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de correo no válido")
        String email,

        @NotBlank(message = "El código es obligatorio")
        @Pattern(regexp = "\\d{4}", message = "El código debe ser exactamente de 4 dígitos")
        String codigo
) {}