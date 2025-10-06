package com.amf.banking.system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API do Desafio Técnico - AMF Promotora")
                        .description("API com algumas operações bancárias relacionadas a cliente, conta bancária e transações")
                        .version("v1.0"));
    }
}
