package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Flores")
                        .version("1.0")
                        .description("Documentación de la API para gestionar flores"));
    }
}

