package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    public String getJwtSecret() {
        return jwtSecret;
    }
}