package com.example.agropecuariaapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gerenciamento de Agropecuária API")
                        .description("API do Sistema Gerenciamento de Agropecuária")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Jonathas e Samuel")
                                .url("http://github.com/jonathas-quintao")
                                .url("http://github.com/samuelrivelli")
                                .email("jonathasquintaosilva2002@gmail.com / samuelmagrivelli@gmail.com")
                        )
                )
                .servers(List.of(new Server().url("http://localhost:8080").description("Servidor Local")));
    }
}
