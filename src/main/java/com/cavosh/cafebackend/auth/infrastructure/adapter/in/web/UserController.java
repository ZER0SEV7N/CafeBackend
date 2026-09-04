package com.cavosh.cafebackend.auth.infrastructure.adapter.in.web;
import com.cavosh.cafebackend.auth.domain.model.Usuario;
import com.cavosh.cafebackend.auth.domain.port.in.ProfileUseCase;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.UpdateProfileRequest;
import com.cavosh.cafebackend.auth.infrastructure.adapter.in.web.dto.UserProfileResponse;
import com.cavosh.cafebackend.global.infrastructure.web.response.ResponseGlobal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar las solicitudes relacionadas con el perfil del usuario.
 * Proporciona endpoints para obtener y actualizar el perfil del usuario autenticado.
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final ProfileUseCase profileUseCase;

    /**
     * Endpoint para obtener tu perfil de usuario
     * @Get /api/user/profile
     * @param usuarioAuth - Tu usuario
     * @return Retorna el perfil de usuario
     */
    @GetMapping("/profile")
    public ResponseEntity<ResponseGlobal<UserProfileResponse>> getProfile(@AuthenticationPrincipal Usuario usuarioAuth) {
        Usuario usuario = profileUseCase.getProfile(usuarioAuth.id());
        return ResponseEntity.ok(ResponseGlobal.success(UserProfileResponse.from(usuario), "Perfil obtenido"));
    }

    /**
     * Endpoint para actualizar los datos de tu usuario
     * @param usuarioAuth - Usuario autentificado
     * @param request
     * @return
     */
    @PutMapping("/profile")
    public ResponseEntity<ResponseGlobal<UserProfileResponse>> updateProfile(
            @AuthenticationPrincipal Usuario usuarioAuth,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        var command = new ProfileUseCase.UpdateProfileCommand(usuarioAuth.id(), request.fullName());
        Usuario actualizado = profileUseCase.updateMyProfile(command);
        return ResponseEntity.ok(ResponseGlobal.success(UserProfileResponse.from(actualizado), "Perfil actualizado"));
    }
}