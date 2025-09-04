package com.accenture.desafio_fullstack.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("Desafio Full-Stack API").version("1.0.0")
				.description("API REST para gerenciamento de Empresas e Fornecedores")
				.contact(new Contact().name("Accenture").email("contato@accenture.com"))
				.license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")));
	}
}