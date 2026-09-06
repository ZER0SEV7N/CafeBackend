package com.cavosh.cafebackend.tarjetas.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrarTarjetaRequest(
        @NotBlank(message = "El número de tarjeta es obligatorio")
        @Pattern(regexp = "^[0-9]{13,19}$", message = "El número de tarjeta debe contener entre 13 y 19 dígitos numéricos")
        String numeroTarjeta,

        @NotBlank(message = "El titular de la tarjeta es obligatorio")
        @Size(max = 120, message = "El nombre del titular no puede exceder los 120 caracteres")
        String titular,

        @NotBlank(message = "La marca es obligatoria (ej. MasterCard, Visa)")
        String marca,

        boolean predeterminado
) {}