package com.cavosh.cafebackend.pedidos.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record DetallePedido(
        Integer id,
        Integer productoId,
        String nombreProducto,
        String nombreEscala,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotalItem,
        List<DetalleOpcionSeleccionada> opciones
) {}