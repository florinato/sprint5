package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService customUserDetailsService;

    private static final List<String> SWAGGER_PATHS = List.of(
        "/v3/api-docs",
        "/swagger-ui",
        "/swagger-ui.html",
        "/swagger-resources",
        "/webjars"
    );
    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (isSwaggerPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJwtFromRequest(request);
        if (StringUtils.hasText(token)) {
            System.out.println("Token presente en la solicitud: " + token);
            if (tokenProvider.validateToken(token)) {
                String username = tokenProvider.getUserNameFromJwtToken(token);
                System.out.println("Token válido, usuario: " + username);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Token no válido");
            }
        } else {
            System.out.println("No se encontró el token en la solicitud");
        }
        filterChain.doFilter(request, response);
    }

    private boolean isSwaggerPath(String path) {
        return SWAGGER_PATHS.stream().anyMatch(path::startsWith);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
