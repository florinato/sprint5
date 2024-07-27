package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.security.JwtAuthenticationEntryPoint;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**").permitAll()
                .requestMatchers("/api/players/**", "/games/**").authenticated()
                .anyRequest().authenticated())
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
