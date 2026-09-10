package com.cavosh.cafebackend.pedidos.infrastructure.adapter.in.web.dto;

import com.cavosh.cafebackend.pedidos.domain.model.EstadoPedido;
import com.cavosh.cafebackend.pedidos.domain.model.MetodoPago;
import com.cavosh.cafebackend.pedidos.domain.model.Pedido;
import com.cavosh.cafebackend.pedidos.domain.model.TipoEntrega;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/** 
 * DTO de respuesta para los detalles de un pedido.
 * Contiene información detallada sobre el pedido, incluyendo sus items y opciones seleccionadas.
 * @param id Identificador único del pedido.
 * @param codigoOrden Código de orden del pedido.
 * @param cafeteriaId Identificador de la cafetería asociada al pedido.
 * @param tipoEntrega Tipo de entrega del pedido (por ejemplo, para llevar, en el lugar, etc.).
 * @param fechaRecojo Fecha en la que se debe recoger el pedido (si aplica).
 * @param horaRecojo Hora en la que se debe recoger el pedido (si aplica).
 * @param metodoPago Método de pago utilizado para el pedido.
 * @param referenciaPago Referencia del pago (si aplica).
 * @param estado Estado actual del pedido (por ejemplo, pendiente, completado, cancelado, etc.).
 * @param subtotal Subtotal del pedido antes de descuentos e impuestos.
 * @param descuento Descuento aplicado al pedido.
 * @param total Total del pedido después de aplicar descuentos e impuestos.
 * @param createdAt Fecha y hora de creación del pedido.
 * @param items Lista de items que componen el pedido, cada uno con sus detalles y opciones seleccionadas.
 */
public record PedidoDetalleResponse(
        Integer id,
        String codigoOrden,
        Integer cafeteriaId,
        TipoEntrega tipoEntrega,
        LocalDate fechaRecojo,
        LocalTime horaRecojo,
        MetodoPago metodoPago,
        String referenciaPago,
        EstadoPedido estado,
        BigDecimal subtotal,
        BigDecimal descuento,
        BigDecimal total,
        Instant createdAt,
        List<ItemDetalleResponse> items
) {

    /**
     * DTO de respuesta para los detalles de un item dentro de un pedido.
     * Contiene información sobre el producto, la escala, la cantidad, el precio unitario, el subtotal del item y las opciones seleccionadas.
     * @param id Identificador único del item dentro del pedido.
     * @param productoId Identificador del producto asociado al item.
     * @param nombreProducto Nombre del producto asociado al item.
     * @param nombreEscala Nombre de la escala seleccionada para el producto (por ejemplo, tamaño).
     * @param cantidad Cantidad del producto seleccionada en el pedido.
     * @param precioUnitario Precio unitario del producto seleccionado.
     * @param subtotalItem Subtotal del item calculado como precio unitario multiplicado por
     * la cantidad, antes de aplicar descuentos e impuestos.
     * @param opciones Lista de opciones seleccionadas para el item, cada una con su nombre
     */
    public record ItemDetalleResponse(
            Integer id,
            Integer productoId,
            String nombreProducto,
            String nombreEscala,
            Integer cantidad,
            BigDecimal precioUnitario,
            BigDecimal subtotalItem,
            List<OpcionDetalleResponse> opciones
    ) {}

    /** 
     * DTO de respuesta para los detalles de una opción seleccionada dentro de un item de un pedido.
     * Contiene información sobre el grupo de la opción, el nombre de la opción y el
     * recargo aplicado por la opción seleccionada.
     * @param nombreGrupo Nombre del grupo al que pertenece la opción (por ejemplo,  "adicionales", "toppings", etc.).
     * @param nombreOpcion Nombre de la opción seleccionada.
     * @param recargo Recargo aplicado por la opción seleccionada, si corresponde.
     */
    public record OpcionDetalleResponse(
            String nombreGrupo,
            String nombreOpcion,
            BigDecimal recargo
    ) {}

    /**
     * Crea una instancia de PedidoDetalleResponse a partir de un objeto Pedido.
     * Mapea los detalles del pedido y sus items, incluyendo las opciones seleccionadas, a la estructura de respuesta.
     * @param p El objeto Pedido del cual se desea crear la respuesta.
     * @return Una instancia de PedidoDetalleResponse con los detalles del pedido mapeados.
     */
    public static PedidoDetalleResponse from(Pedido p) {
        List<ItemDetalleResponse> items = p.detalles() != null ? p.detalles().stream().map(d -> {
            List<OpcionDetalleResponse> opciones;
            
            //Mapea las opciones del detalle del pedido a la estructura de respuesta, si existen.
            if (d.opciones() != null) 
                opciones = d.opciones().stream().map(o -> new OpcionDetalleResponse(o.nombreGrupo(), o.nombreOpcion(), o.recargo())).toList();
             else 
                opciones = List.of();

            return new ItemDetalleResponse(
                    d.id(),
                    d.productoId(),
                    d.nombreProducto(),
                    d.nombreEscala(),
                    d.cantidad(),
                    d.precioUnitario(),
                    d.subtotalItem(),
                    opciones
            );
        }).toList() : List.of();

        return new PedidoDetalleResponse(
                p.id(),
                p.codigoOrden(),
                p.cafeteriaId(),
                p.tipoEntrega(),
                p.fechaRecojo(),
                p.horaRecojo(),
                p.metodoPago(),
                p.referenciaPago(),
                p.estado(),
                p.subtotal(),
                p.descuento(),
                p.total(),
                p.createdAt(),
                items
        );
    }
}