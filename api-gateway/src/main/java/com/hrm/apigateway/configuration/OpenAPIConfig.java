package com.hrm.apigateway.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "HRM API Gateway",
                version = "v0.0.1",
                description = "API Gateway for HRM System"
        )
)
public class OpenAPIConfig {

        @Bean
        public OpenAPI gatewayOpenAPI() {
                return new OpenAPI()
                        .addServersItem(new Server()
                                .url("http://localhost:8086")
                                .description("API Gateway"));
        }
}
