package com.cavosh.cafebackend.auth.infrastructure.adapter.out.security;

import com.cavosh.cafebackend.auth.domain.port.out.TokenProviderPort;
import com.cavosh.cafebackend.auth.domain.port.out.UsuarioRepositoryPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Componente que actúa como un filtro de autenticación JWT para cada solicitud HTTP entrante.
 * Este filtro intercepta las solicitudes, extrae el token JWT del encabezado de autorización,
 * valida el token y, si es válido, establece la autenticación en el contexto de seguridad
 * de Spring Security.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProviderPort tokenProviderPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    /**
     * Metodo para filtrar internamente en cada solicitud HTTP entrante.
     * @param request - Solicitud HTTP entrante
     * @param response - Respuesta del servidor
     * @param filterChain - Cadena de filtros para continuar con el procesamiento de la solicitud
     * @throws ServletException - Excepcion por
     * @throws IOException
     */
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        if (tokenProviderPort.validateToken(token)) {
            String correo = tokenProviderPort.extractEmail(token);

            if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                usuarioRepositoryPort.findByEmail(correo).ifPresent(usuario -> {
                    if (usuario.activo()) {
                        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.rol().name()));
                        var authToken = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                });
            }
        }
        filterChain.doFilter(request, response);
    }
}
