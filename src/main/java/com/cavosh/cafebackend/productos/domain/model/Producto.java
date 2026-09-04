package com.cavosh.cafebackend.productos.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 *
 * @param id
 * @param categoriaId
 * @param nombre
 * @param descripcion
 * @param imagenUrl
 * @param precioBase
 * @param esNuevo
 * @param esFrecuente
 * @param activo
 * @param escalas
 * @param gruposPersonalizacion
 * @param createdAt
 */
public record Producto(
        Integer id,
        Integer categoriaId,
        String nombre,
        String descripcion,
        String imagenUrl,
        BigDecimal precioBase,
        boolean nuevo,
        boolean frecuente,
        boolean activo,
        List<Escala> escalas,
        List<GrupoPersonalizacion> gruposPersonalizacion,
        Instant createdAt,
        Instant updatedAt
) {
    /**
     * Regla de negocio en el dominio: calcula el precio total dinámicamente sumando tamaño y opciones
     * @param escalaSeleccionado - La escala seleccionada por el usuario
     * @param opcionesSeleccionadas - Las opciones de personalización seleccionadas por el usuario
     * @return El precio final del producto considerando la escala y las opciones seleccionadas
     */
    public BigDecimal calcularPrecioFinal(Escala escalaSeleccionado, List<OpcionPersonalizacion> opcionesSeleccionadas) {
        BigDecimal total = this.precioBase;

        //Verificar si la escala seleccionada no es nula y si tiene un recargo de precio
        if (escalaSeleccionado != null && escalaSeleccionado.recargoPrecio() != null)
            total = total.add(escalaSeleccionado.recargoPrecio());


        //Verificar que la opcion no sea nula y que tenga un recargo de precio, si es así se suma al total
        if (opcionesSeleccionadas != null) {
            for (OpcionPersonalizacion opcion : opcionesSeleccionadas) {
                if (opcion.recargoPrecio() != null)
                    total = total.add(opcion.recargoPrecio());
            }
        }

        return total;
    }
}
