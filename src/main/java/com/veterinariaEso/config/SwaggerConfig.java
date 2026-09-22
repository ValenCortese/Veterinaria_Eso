package com.veterinariaEso.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI vetSystemOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Veterinaria - Eso UP")
                        .description("Documentacion de la API de la Veterinaria Eso")
                        .version("1.0")
                        .contact(new Contact().name("Valentino Cortese")
                                .email("valen4002@gmail.com"))
                        .license(new License().name("Uso Academico")));
    }
}