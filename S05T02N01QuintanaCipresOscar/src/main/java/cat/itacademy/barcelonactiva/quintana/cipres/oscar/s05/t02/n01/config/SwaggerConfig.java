package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.config;

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
                        .title("API de Juego de Dados")
                        .version("1.0")
                        .description("Documentación de la API del Juego de Dados"));
               
    }
}
