package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Jugador;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.services.JugadorService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpiration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JugadorService jugadorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private SecretKey jwtSecretKey;

    @PostMapping("/signin")
    public Map<String, String> authenticateUser(@RequestBody Jugador loginRequest) {
        try {
            System.out.println("Attempting to authenticate user: " + loginRequest.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            
            if (authentication.isAuthenticated()) {
                System.out.println("User authenticated: " + loginRequest.getUsername());

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = generateToken(authentication);
                System.out.println("Generated JWT: " + jwt);

                Map<String, String> response = new HashMap<>();
                response.put("token", jwt);
                return response;
            } else {
                System.out.println("Authentication failed for user: " + loginRequest.getUsername());
                throw new RuntimeException("Authentication failed");
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }

    @PostMapping("/signup")
    public void registerUser(@RequestBody Jugador signUpRequest) {
        jugadorService.createJugador(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()));
    }
    private String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User userPrincipal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        if (jwtSecretKey == null) {
            byte[] keyBytes = java.util.Base64.getDecoder().decode(jwtSecret);
            jwtSecretKey = Keys.hmacShaKeyFor(keyBytes); // Genera una clave segura
        }

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecretKey)
                .compact();
    }
}
