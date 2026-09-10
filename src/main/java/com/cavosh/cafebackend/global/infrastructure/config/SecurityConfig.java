package com.cavosh.cafebackend.global.infrastructure.config;

import com.cavosh.cafebackend.auth.infrastructure.adapter.out.security.JwtAuthenticationFilter;
import com.cavosh.cafebackend.global.domain.exception.SecurityConfigurationException;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuracion
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Filtro de seguridad para configurar la seguridad de la aplicación.
     * Se desactiva CSRF, se establece la política de creación de sesiones como sin estado, y se permiten ciertas rutas sin autenticación.
     * @param http - HttpSecurity para configurar la seguridad de la aplicación
     * @return retorna un objeto SecurityFilterChain que representa la cadena de filtros de seguridad configurada
     */
    @Bean
    @SuppressWarnings("java:S4502")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        // CSRF is unnecessary because this API is stateless and authenticates with JWTs.
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/escalas/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/grupos-personalizacion/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/tiendas/**", "/api/tienda/**", "/api/stores/**").permitAll()

                    .requestMatchers("/api/cupones/validar").permitAll()
                    .anyRequest().authenticated()
            ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //En caso de que se produzca un error al construir la cadena de filtros de seguridad, s
        //se lanza una excepción personalizada SecurityConfigurationException con un mensaje y la causa del error.
        try {
            return http.build();
        } catch (Exception exception) {
            throw new SecurityConfigurationException("No se pudo configurar la cadena de filtros de seguridad", exception);
        }
    }
}
