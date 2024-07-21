package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.services.JugadorService;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.security.JwtAuthenticationEntryPoint;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JugadorService jugadorService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public AuthenticationManager customAuthenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(jugadorService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
            .authorizeRequests(requests -> requests
                .requestMatchers("/auth/**",
                                 "/v3/api-docs/**",
                                 "/swagger-ui.html",
                                 "/swagger-ui/**",
                                 "/swagger-resources/**",
                                 "/webjars/**").permitAll()
                .requestMatchers("/api/players/**", "/games/**").authenticated()
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



