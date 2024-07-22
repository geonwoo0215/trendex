package com.trendex.trendex.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Trendex Swagger", version = "v1", description = "Trendex Api 명세서"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server"),
        }

)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi rsiApi() {
        return GroupedOpenApi.builder()
                .group("rsi")
                .pathsToMatch("/rsis/**")
                .build();
    }

    @Bean
    public GroupedOpenApi macdApi() {
        return GroupedOpenApi.builder()
                .group("macd")
                .pathsToMatch("/macds/**")
                .build();
    }

}
