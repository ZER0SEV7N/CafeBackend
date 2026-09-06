package com.cavosh.cafebackend.pedidos.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record Pedido(
        Integer id,
        Integer usuarioId,
        Integer cafeteriaId,
        String codigoOrden,
        TipoEntrega tipoEntrega,
        LocalDate fechaRecojo,
        LocalTime horaRecojo,
        MetodoPago metodoPago,
        String referenciaPago,
        EstadoPedido estado,
        BigDecimal subtotal,
        BigDecimal descuento,
        BigDecimal total,
        List<DetallePedido> detalles,
        Instant createdAt
) {}