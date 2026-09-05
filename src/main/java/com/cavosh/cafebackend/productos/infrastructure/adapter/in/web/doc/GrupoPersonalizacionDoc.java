package com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.personalizacion.CrearGrupoPersonalizacionRequest;
import com.cavosh.cafebackend.productos.infrastructure.adapter.in.web.dto.personalizacion.GrupoPersonalizacionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Personalizaciones", description = "Catálogo maestro de grupos de personalización y opciones (Milk, Whipped Cream, Caffeine)")
public interface GrupoPersonalizacionDoc {

    @Operation(summary = "Listar todos los grupos de personalización", description = "Retorna todos los grupos maestros configurables con sus respectivas opciones.")
    ResponseEntity<ResponseGlobal<List<GrupoPersonalizacionResponse>>> getAllGrupos();

    @Operation(summary = "Crear nuevo grupo de personalización", description = "Requiere rol ADMIN. Crea un grupo maestro y sus opciones base.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Grupo creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (Requiere ADMIN)"),
            @ApiResponse(responseCode = "409", description = "El grupo ya existe")
    })
    ResponseEntity<ResponseGlobal<GrupoPersonalizacionResponse>> crearGrupo(CrearGrupoPersonalizacionRequest request);
}