package com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.pedidos.domain.model.MetodoPago;
import com.cavosh.cafebackend.pedidos.domain.model.TipoEntrega;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CrearPedidoRequest(
        @NotNull(message = "La sede/cafetería es obligatoria")
        Integer cafeteriaId,

        @NotNull(message = "El tipo de entrega es obligatorio")
        TipoEntrega tipoEntrega,

        LocalDate fechaRecojo,
        LocalTime horaRecojo,

        @NotNull(message = "El método de pago es obligatorio")
        MetodoPago metodoPago,

        String referenciaPago,
        String codigoCupon,

        @NotEmpty(message = "El carrito debe contener al menos un producto")
        @Valid
        List<ItemPedidoRequest> items
) {
    public record ItemPedidoRequest(
            @NotNull(message = "El id del producto es obligatorio")
            Integer productoId,

            @NotNull(message = "La escala del tamaño es obligatoria")
            Integer escalaId,

            List<Integer> opcionIds,

            @NotNull(message = "La cantidad es obligatoria")
            Integer cantidad
    ) {}
}