package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.doc;

import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.UpdateProfileRequest;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.UserProfileResponse;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuario", description = "Endpoints para la gestión del perfil del usuario autenticado")
@SecurityRequirement(name = "bearerAuth")
public interface UserDoc {

    @Operation(
            summary = "Obtener perfil de usuario",
            description = "Retorna la información del usuario autenticado (nombre, correo, rol y puntos de recompensa) extrayendo su identidad del token JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil obtenido exitosamente",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autenticado o token JWT inválido/expirado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    ResponseEntity<ResponseGlobal<UserProfileResponse>> getProfile(
            @Parameter(hidden = true) Usuario usuarioAuth
    );

    @Operation(
            summary = "Actualizar perfil de usuario",
            description = "Permite al cliente autenticado modificar su nombre completo."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    ResponseEntity<ResponseGlobal<UserProfileResponse>> updateProfile(
            @Parameter(hidden = true) Usuario usuarioAuth,
            UpdateProfileRequest request
    );
}