package com.cavosh.cafebackend.tienda.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;

/**
 * Modelo de la cafeteria
 * @param id - Id de la cafeteria
 * @param nombre - Nombre de la cafeteria
 * @param direccion - Direccion de la cafeteria
 * @param ciudad - Ciudad de la cafeteria
 * @param latitud - Latitud de la cafeteria
 * @param longitud - Longitud de la cafeteria
 * @param horaApertura - Hora de apertura de la cafeteria
 * @param horaCierre - Hora de cierre de la cafeteria
 * @param frecuente - Indica si la cafeteria es frecuente
 * @param activo - Indica si la cafeteria está activa
 * @param createdAt - Fecha de creación de la cafeteria
 */
public record Cafeteria(
        Integer id,
        String nombre,
        String direccion,
        String ciudad,
        BigDecimal latitud,
        BigDecimal longitud,
        LocalTime horaApertura,
        LocalTime horaCierre,
        boolean frecuente,
        boolean activo,
        Instant createdAt
) {

    /**
     * Regla de negocio para validar si la cafeteria esta abierta
     * @param hora - La hora a validar
     * @return true si la cafeteria esta abierta, false si esta cerrada
     */
    public boolean estaAbierto(LocalTime hora){
        if(!activo) return false;
        return !hora.isBefore(horaApertura) && !hora.isAfter(horaCierre);
    }
}
