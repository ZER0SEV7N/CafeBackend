package com.cavosh.cafebackend.pedidos.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_opciones_seleccionadas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleOpcionSeleccionadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detalle_pedido_id", nullable = false)
    private DetallePedidoEntity detallePedido;

    @Column(name = "nombre_grupo", nullable = false, length = 80)
    private String nombreGrupo;

    @Column(name = "nombre_opcion", nullable = false, length = 100)
    private String nombreOpcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal recargo;
}