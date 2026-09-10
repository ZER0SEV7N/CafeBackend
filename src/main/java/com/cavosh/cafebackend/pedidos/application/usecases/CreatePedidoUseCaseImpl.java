package com.cavosh.cafebackend.pedidos.application.usecases;

import com.cavosh.cafebackend.pedidos.domain.model.*;
import com.cavosh.cafebackend.pedidos.domain.ports.in.CreatePedidoUseCase;
import com.cavosh.cafebackend.pedidos.domain.ports.out.PedidoRepositoryPort;
import com.cavosh.cafebackend.productos.domain.model.*;
import com.cavosh.cafebackend.productos.domain.ports.out.ProductoRepositoryPort;
import com.cavosh.cafebackend.tienda.domain.port.out.TiendaRepositoryPort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.cavosh.cafebackend.cupones.domain.ports.out.ValidateCuponPort;
import com.cavosh.cafebackend.global.domain.exception.BusinessRuleException;
import com.cavosh.cafebackend.global.domain.exception.ResourceNotFoundException;


/** 
 * Implementación del caso de uso para la creación de pedidos.
 * Tiene el siguiente método:
 * - savePedido(CreatePedidoCommand command): Crea un nuevo pedido en el sistema.
 * 
 */
@Service
@RequiredArgsConstructor 
public class CreatePedidoUseCaseImpl implements CreatePedidoUseCase {
    
    private final TiendaRepositoryPort tiendaRepository;
    private final ProductoRepositoryPort productoRepository;
    private final PedidoRepositoryPort pedidoRepository;
    private final ValidateCuponPort validateCuponPort;


     /**
     * Guarda un nuevo pedido en el sistema.
     * @param command los datos del pedido a crear.
     * @return el pedido creado.
     * @throws BusinessRuleException si el pedido no cumple con las reglas de negocio.
     * @throws ResourceNotFoundException si algún recurso relacionado con el pedido no se encuentra.
     */
    @Transactional 
    public Pedido savePedido(CreatePedidoCommand command) {
        validarItems(command);
        validarCafeteria(command.cafeteriaId());
        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal subtotalAcumulado = command.items().stream()
                .map(item -> crearDetalle(item, detalles))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //Descontar cupon si aplica
        BigDecimal descuento = BigDecimal.ZERO;
        if (command.codigoCupon() != null && !command.codigoCupon().isBlank()) 
            descuento = validateCuponPort.calculateDiscount(command.codigoCupon(), subtotalAcumulado);
        
        //Calcular total final del pedido
        BigDecimal total = subtotalAcumulado.subtract(descuento).max(BigDecimal.ZERO);
        String codigoOrden = String.valueOf(ThreadLocalRandom.current().nextInt(10, 999)); //Código de orden #76

        //Crear el pedido con todos los detalles y precios calculados
        Pedido nuevoPedido = new Pedido(
                null,
                command.usuarioId(),
                command.cafeteriaId(),
                codigoOrden,
                command.tipoEntrega(),
                command.fechaRecojo(),
                command.horaRecojo(),
                command.metodoPago(),
                command.referenciaPago(),
                EstadoPedido.PEDIDO_REALIZADO,
                subtotalAcumulado,
                descuento,
                total,
                detalles,
                Instant.now()
        );

        return pedidoRepository.save(nuevoPedido);
    }

    /** 
     * Valida los items del pedido.
     * @param command El comando del pedido a validar.
     */
    private void validarItems(CreatePedidoCommand command) {
        if (command.items() == null || command.items().isEmpty())
            throw new BusinessRuleException("El pedido debe contener al menos un item.");
    }

    /** 
     * Valida la existencia y estado de la cafetería.
     * @param cafeteriaId El ID de la cafetería a validar.
     */
    private void validarCafeteria(Integer cafeteriaId) {
        tiendaRepository.findById(cafeteriaId).filter(c -> c.activo())
                .orElseThrow(() -> new ResourceNotFoundException("La caferetia seleccionada no existe o no esta operativa."));
    }

    /** 
     * Crea un detalle de pedido.
     * @param itemCmd El comando del item del pedido.
     * @param detalles La lista de detalles del pedido.
     * @return El subtotal del detalle creado.
     */
    private BigDecimal crearDetalle(ItemPedidoCommand itemCmd, List<DetallePedido> detalles) {
        validarCantidad(itemCmd);
        
        //Buscar el producto y la escala seleccionada, validando que existan y estén activos
        Producto producto = productoRepository.findById(itemCmd.productoId()).filter(Producto::activo)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no disponible con ID: " + itemCmd.productoId()));
        
        //Buscar la escala seleccionada para el producto, validando que exista
        Escala escala = producto.escalas().stream().filter(e -> e.id().equals(itemCmd.escalaId())).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Escala no disponible para el producto con ID: " + itemCmd.productoId()));
        
        //Calcular el precio del item, sumando el precio base del producto, el recargo de la escala y los recargos de las opciones seleccionadas
        List<DetalleOpcionSeleccionada> opciones = new ArrayList<>();
        BigDecimal precio = producto.precioBase().add(valor(escala.recargoPrecio()));
        
        //Agregar las opciones seleccionadas al detalle del pedido y calcular el precio final del item
        for (Integer opcionId : itemCmd.opcionIds() == null ? List.<Integer>of() : itemCmd.opcionIds())
            precio = agregarOpcion(producto, opcionId, opciones, precio);

        //Calcular el subtotal del item multiplicando el precio final por la cantidad seleccionada
        BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(itemCmd.cantidad()));
        detalles.add(new DetallePedido(null, producto.id(), producto.nombre(), escala.nombre(), itemCmd.cantidad(), precio, subtotal, opciones));
        return subtotal;
    }

    /** 
     * Valida la cantidad de un item del pedido.
     * @param item El item a validar.
     */
    private void validarCantidad(ItemPedidoCommand item) {
        if (item.cantidad() == null || item.cantidad() <= 0)
            throw new BusinessRuleException("La cantidad de un item debe ser mayor a cero.");
    }

    /** 
     * Agrega una opción de personalización a un item del pedido.
     * @param producto El producto al que se le agregará la opción.
     * @param opcionId El ID de la opción a agregar.
     * @param opciones La lista de opciones seleccionadas.
     * @param precio El precio actual del item.
     * @return El nuevo precio del item con la opción agregada.
     */
    private BigDecimal agregarOpcion(Producto producto, Integer opcionId, List<DetalleOpcionSeleccionada> opciones, BigDecimal precio) {
        for (GrupoPersonalizacion grupo : producto.gruposPersonalizacion()) {
            for (OpcionPersonalizacion opcion : grupo.opciones()) {
                if (opcion.id().equals(opcionId)) {
                    BigDecimal recargo = valor(opcion.recargoPrecio());
                    opciones.add(new DetalleOpcionSeleccionada(null, grupo.nombreGrupo(), opcion.nombre(), recargo));
                    return precio.add(recargo);
                }
            }
        }
        throw new BusinessRuleException("La opción de personalización no pertenece al producto " + producto.nombre());
    }

    /** 
     * Devuelve el valor de un importe, o cero si es null.
     * @param importe El importe a validar.
     * @return El valor del importe o cero si es null.
     */
    private BigDecimal valor(BigDecimal importe) { return importe == null ? BigDecimal.ZERO : importe; }
}