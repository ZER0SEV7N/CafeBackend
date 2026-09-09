package com.cavosh.cafebackend.tienda.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public record TiendaResponse (
        Integer id,
        String nombre,
        String direccion,
        String ciudad,
        BigDecimal latitud,
        BigDecimal longitud,
        String horaApertura,
        boolean abierto,
        boolean frecuente
) {
    //Establecer formato de tiempo
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static TiendaResponse from(Cafeteria c) {
        LocalTime ahora = LocalTime.now(ZoneId.systemDefault());
        String formateoHoras = c.horaApertura().format(TIME_FORMATTER) + " - " + c.horaCierre().format(TIME_FORMATTER);
        return new TiendaResponse(
                c.id(),
                c.nombre(),
                c.direccion(),
                c.ciudad(),
                c.latitud(),
                c.longitud(),
                formateoHoras,
                c.estaAbierto(ahora),
                c.frecuente()
        );
    }
}
