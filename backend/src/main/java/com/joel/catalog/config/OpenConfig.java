package com.joel.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API CATALOG PRODUCTS")
						.description("User lists the products and chooses those who want to buy them")
						.version("1.0.0"));
	}

}
