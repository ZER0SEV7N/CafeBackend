package com.cavosh.cafebackend.global.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion del openApi
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "Bearer Auth";

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Cavosh Coffee App - Backend API")
                .version("1.0.0")
                .description("API REST modular para la aplicación móvil de pedidos y fidelización de cafeterías Cavosh.")
                .license(new License().name("Apache 2.0").url("https://springdoc.org")))
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .components(new Components()
                    .addSecuritySchemes(SECURITY_SCHEME_NAME,
                            new SecurityScheme()
                                    .name(SECURITY_SCHEME_NAME)
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")));
    }
}
