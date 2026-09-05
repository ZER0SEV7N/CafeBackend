package com.cavosh.cafebackend.productos.domain.ports.in;

import com.cavosh.cafebackend.productos.domain.model.Escala;

import java.math.BigDecimal;
import java.util.List;

public interface ManageEscalaUseCase {
    record SaveEscalaCommand(String nombre, Integer volumenMl, BigDecimal recargoPrecio) {}
    record UpdateEscalaCommand(String nombre, Integer volumenMl, BigDecimal recargoPrecio) {}

    List<Escala> getAllEscalas();
    Escala getEscalaById(Integer id);
    Escala saveEscala(SaveEscalaCommand command);
    Escala updateEscala(Integer id, UpdateEscalaCommand command);
}