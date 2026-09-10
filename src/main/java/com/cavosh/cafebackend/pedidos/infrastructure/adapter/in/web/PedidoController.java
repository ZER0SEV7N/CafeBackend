package com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web;

import com.cavosh.cafebackend.auth.domain.model.Rol;
import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido;
import com.cavosh.cafebackend.pedidos.domain.model.Pedido;
import com.cavosh.cafebackend.pedidos.domain.ports.in.*;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.doc.PedidoDoc;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.dto.CrearPedidoRequest;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.dto.PedidoDetalleResponse;
import com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.dto.PedidoResumenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** 
 * Controlador para la gestión de pedidos.
 * Contiene los siguientes endpoints:
 * - POST /api/pedidos: Crear un nuevo pedido (Checkout).
 * - GET /api/pedidos/{id}: Obtener el detalle de un pedido por su ID.
 * - GET /api/pedidos/mis-pedidos: Listar el historial de pedidos del usuario autenticado.
 * - PATCH /api/pedidos/{id}/estado: Actualizar el estado de un pedido (requiere rol ADMIN o BARISTA).
 */
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController implements PedidoDoc {

    private final CreatePedidoUseCase createPedidoUseCase;
    private final GetPedidoUseCase getPedidoUseCase;
    private final ChangeEstadoUseCase changeEstadoUseCase;

    /** 
     * Crea un nuevo pedido (Checkout).
     * @post /api/pedidos
     * @param usuarioAuth El usuario autenticado que realiza el pedido.
     * @param request La solicitud de creación de pedido, que incluye detalles como la cafetería, tipo de entrega, método de pago, y los items del pedido.
     * @dto {
     *     - "cafeteriaId": "ID de la cafetería donde se realiza el pedido",
     *     - "tipoEntrega": "Tipo de entrega (por ejemplo, 'RECOJO_EN_TIENDA', 'DOMICILIO')",
     *     - "fechaRecojo": "Fecha en la que se debe recoger el pedido (si aplica)",
     *     - "horaRecojo": "Hora en la que se debe recoger el pedido
     *     - "metodoPago": "Método de pago seleccionado (por ejemplo, 'TARJETA', 'EFECTIVO')",
     *     - "referenciaPago": "Referencia del pago (si aplica)",
     *     - "codigoCupon": "Código de cupón aplicado (si aplica)",
     *     - "items": [
     *         {
     *             "productoId": "ID del producto seleccionado",
     *             "escalaId": "ID de la escala seleccionada para el producto",
     *             "opcionIds": "IDs de las opciones seleccionadas para el producto",
     *             "cantidad": "Cantidad del producto seleccionado"
     *         }
     *     ]
     * }
     * @return Una respuesta con el detalle del pedido creado y un mensaje de éxito.
     */
    @PostMapping
    public ResponseEntity<ResponseGlobal<PedidoDetalleResponse>> createPedido(@AuthenticationPrincipal Usuario usuarioAuth, @Valid @RequestBody CrearPedidoRequest request) {
        List<CreatePedidoUseCase.ItemPedidoCommand> itemsCommand = request.items().stream()
                .map(i -> new CreatePedidoUseCase.ItemPedidoCommand(
                        i.productoId(),
                        i.escalaId(),
                        i.opcionIds(),
                        i.cantidad()
                )).toList();

        var command = new CreatePedidoUseCase.CreatePedidoCommand(
                usuarioAuth.id(),
                request.cafeteriaId(),
                request.tipoEntrega(),
                request.fechaRecojo(),
                request.horaRecojo(),
                request.metodoPago(),
                request.referenciaPago(),
                request.codigoCupon(),
                itemsCommand
        );

        Pedido pedidoCreado = createPedidoUseCase.savePedido(command);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseGlobal.success(HttpStatus.CREATED.value(), PedidoDetalleResponse.from(pedidoCreado), "Pedido generado con éxito"));
    }

    /**
     * Obtiene el detalle de un pedido por su ID.
     * @get /api/pedidos/{id}
     * @param usuarioAuth El usuario autenticado que solicita el detalle del pedido.
     * @param id El ID del pedido a obtener.
     * @return Una respuesta con el detalle del pedido y un mensaje de éxito.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGlobal<PedidoDetalleResponse>> getPedidoById(@AuthenticationPrincipal Usuario usuarioAuth, @PathVariable Integer id) {
        boolean isStaff = usuarioAuth.rol() == Rol.ADMIN || usuarioAuth.rol() == Rol.BARISTA;
        Pedido pedido = getPedidoUseCase.getPedidoById(id, usuarioAuth.id(), isStaff);

        return ResponseEntity.ok(ResponseGlobal.success(PedidoDetalleResponse.from(pedido), "Detalle del pedido obtenido"));
    }


    /**
     * Lista el historial de pedidos del usuario autenticado.
     * @get /api/pedidos/mis-pedidos
     * @param usuarioAuth El usuario autenticado cuyo historial de pedidos se desea obtener.
     * @return Una respuesta con la lista de pedidos del usuario y un mensaje de éxito.
     */
    @GetMapping("/mis-pedidos")
    public ResponseEntity<ResponseGlobal<List<PedidoResumenResponse>>> listMyPedidos(@AuthenticationPrincipal Usuario usuarioAuth) {
        List<PedidoResumenResponse> response = getPedidoUseCase.listPedidosByUsuarioId(usuarioAuth.id())
                .stream()
                .map(PedidoResumenResponse::from)
                .toList();

        return ResponseEntity.ok(ResponseGlobal.success(response, "Historial de pedidos obtenido"));
    }


    /**
     * Actualiza el estado de un pedido existente.
     * @patch /api/pedidos/{id}/estado
     * @param id El ID del pedido cuyo estado se desea actualizar.
     * @param nuevoEstado El nuevo estado a asignar al pedido.
     * @pre Requiere rol ADMIN o BARISTA.
     * @return Una respuesta con un mensaje de éxito indicando que el estado del pedido ha sido
     */
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'BARISTA')")
    public ResponseEntity<ResponseGlobal<Void>> changeEstado(@PathVariable Integer id,@RequestParam EstadoPedido nuevoEstado) {
        changeEstadoUseCase.changeEstadoPedido(id, nuevoEstado);
        return ResponseEntity.ok(ResponseGlobal.success(null, "Estado actualizado a " + nuevoEstado.name()));
    }
}