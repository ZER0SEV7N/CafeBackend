package com.cavosh.cafebackend.productos.domain.model;

/**
 * Modelo de las categorias:
 * @param id - Id categoria
 * @param nombre - Nombre de la categoria
 * @param iconoUrl - Url del icono de la categoria
 * @param ordenVisual - Orden de visualización de la categoria
 * @param activa - Indica si la categoria está activa
 */
public record Categoria (
    Integer id,
    String nombre,
    String iconoUrl,
    Integer ordenVisual,
    boolean activa
)
{ }
