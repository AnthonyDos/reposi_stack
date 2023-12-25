package com.pastrycertified.pastrycertified.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration pour la personnalisation de la documentation OpenAPI.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configure les éléments de sécurité pour l'authentification Bearer (JWT) dans la documentation OpenAPI.
     *
     * @return La configuration OpenAPI personnalisée.
     */
    @Bean
    public OpenAPI customOpenApiConfig() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );

    }
}
