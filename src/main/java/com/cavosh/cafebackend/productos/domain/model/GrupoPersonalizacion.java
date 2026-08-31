package com.cavosh.cafebackend.productos.domain.model;

import java.util.List;

/**
 *
 * @param id
 * @param nombreGrupo
 * @param seleccionMultiple
 * @param obligatorio
 * @param opciones
 */
public record GrupoPersonalizacion (
    Integer id,
    String nombreGrupo,
    boolean seleccionMultiple,
    boolean obligatorio,
    List<OpcionPersonalizacion> opciones
){ }
