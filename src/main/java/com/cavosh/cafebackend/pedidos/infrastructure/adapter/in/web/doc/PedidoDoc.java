package com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.dto.CrearPedidoRequest;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.dto.PedidoDetalleResponse;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.dto.PedidoResumenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Pedidos", description = "Endpoints para checkout, seguimiento de órdenes y cambio de estados")
@SecurityRequirement(name = "bearerAuth")
public interface PedidoDoc {

    @Operation(summary = "Crear nuevo pedido (Checkout)", description = "Genera la orden calculando totales en servidor, aplicando cupones mediante el puerto de cupones y devolviendo el código de recojo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido generado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación o cupón no válido"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    ResponseEntity<ResponseGlobal<PedidoDetalleResponse>> createPedido(
            @Parameter(hidden = true) Usuario usuarioAuth,
            CrearPedidoRequest request
    );

    @Operation(summary = "Detalle y seguimiento de pedido", description = "Retorna el detalle completo y estado en vivo (PEDIDO_REALIZADO, PREPARANDO, LISTO).")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Detalle del pedido obtenido"),
            @ApiResponse(responseCode = "403", description = "No autorizado para ver este pedido"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    ResponseEntity<ResponseGlobal<PedidoDetalleResponse>> getPedidoById(
            @Parameter(hidden = true) Usuario usuarioAuth,
            @Parameter(description = "ID del pedido") Integer id
    );

    @Operation(summary = "Historial de pedidos del cliente", description = "Retorna el historial de compras del usuario autenticado.")
    ResponseEntity<ResponseGlobal<List<PedidoResumenResponse>>> listMyPedidos(
            @Parameter(hidden = true) Usuario usuarioAuth
    );

    @Operation(summary = "Actualizar estado del pedido", description = "Requiere rol ADMIN o BARISTA. Permite avanzar el flujo del pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado modificado exitosamente"),
            @ApiResponse(responseCode = "403", description = "Requiere rol ADMIN o BARISTA"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    ResponseEntity<ResponseGlobal<Void>> changeEstado(
            @Parameter(description = "ID del pedido") Integer id,
            @Parameter(description = "Nuevo estado a asignar") EstadoPedido nuevoEstado
    );
}