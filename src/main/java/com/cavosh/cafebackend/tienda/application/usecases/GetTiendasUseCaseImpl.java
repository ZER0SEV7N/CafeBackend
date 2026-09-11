package com.cavosh.cafebackend.tienda.application.usecases;

import com.cavosh.cafebackend.tienda.domain.model.Cafeteria;
import com.cavosh.cafebackend.tienda.domain.port.in.GetTiendasUseCase;
import com.cavosh.cafebackend.tienda.domain.port.out.TiendaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementacion de los metodos del caso de uso para obtener tiendas
 */
@Service
@RequiredArgsConstructor
public class GetTiendasUseCaseImpl implements GetTiendasUseCase {

    private final TiendaRepositoryPort tiendaRepository;

    /**
     * Obtiene todas las tiendas activas.
     * @return Lista de cafeterias activas.
     */
    @Transactional(readOnly = true)
    public List<Cafeteria> getAllActiveTiendas() {
        return tiendaRepository.findAllActive();
    }

    /**
     * Obtener la tienda por ciudad
     * @param ciudad - Ciudad a buscar
     * @return una lista de tiendas dependiendo de la ciudad
     */
    @Transactional(readOnly = true)
    public List<Cafeteria> getTiendasByCiudad(String ciudad){
        if(ciudad == null || ciudad.trim().isBlank())
            return tiendaRepository.findAllActive();

        return tiendaRepository.findByCiudad(ciudad.trim());
    }

    /** 
     * Obtiene las tiendas más frecuentes para un usuario específico.
     * @param usuarioId - El ID del usuario.
     * @return Una lista de tiendas más frecuentes para el usuario.
     */
    @Transactional(readOnly = true)
    public List<Cafeteria> getTiendasFrecuentes(Integer usuarioId) {
        if (usuarioId == null) 
            return List.of();
        
        // Evaluamos sedes con 3 o más pedidos en los últimos 30 días, devolviendo hasta 3
        return tiendaRepository.findFrecuentesPorUsuario(usuarioId);
    }
}
