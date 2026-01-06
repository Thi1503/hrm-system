package com.hrm.apigateway.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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
}
