package com.openclassrooms.rentals.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(
						new Components().addSecuritySchemes("Bearer Authentication",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
				.info(new Info()
						.title("Rentals API")
						.description("API de gestion de locations immobilières.")
						.version("1.0.0")
						.contact(new Contact().name("Équipe de support").email("support@example.com")));
	}
}
