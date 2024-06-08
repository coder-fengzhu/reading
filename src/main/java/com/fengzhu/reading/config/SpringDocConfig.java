package com.fengzhu.reading.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("reading system api")
                        .description("restful api")
                        .version("v1.0.0")
                        .license(new License().name("").url("")))
                .externalDocs(new ExternalDocumentation().description("")
                        .url(""));
    }
}