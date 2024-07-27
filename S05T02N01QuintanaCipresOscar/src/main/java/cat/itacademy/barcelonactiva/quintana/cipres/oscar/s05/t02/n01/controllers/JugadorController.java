package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Jugador;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.services.JugadorService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/players")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<String> getAllPlayers() {
        List<Jugador> players = jugadorService.getAllJugadoresOrdenados();
        StringBuilder response = new StringBuilder();
        players.forEach(player -> {
            response.append("id: ").append(player.getId()).append("\n")
                    .append("username: ").append(player.getUsername()).append("\n")
                    .append("fechaRegistro: ").append(player.getFechaRegistro()).append("\n")
                    .append("porcentajeExito: ").append(player.getPorcentajeExito()).append("\n\n");
        });
        return ResponseEntity.ok(response.toString());
    }

    @GetMapping("/ranking")
    public ResponseEntity<String> getRanking() {
        double ranking = jugadorService.getRanking();
        return ResponseEntity.ok(String.format("Ranking medio: %.2f%%", ranking));
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<String> getLoser() {
        Jugador loser = jugadorService.getLoser();
        if (loser == null) {
            return ResponseEntity.ok("No hay jugadores.");
        }
        StringBuilder response = new StringBuilder();
        response.append("id: ").append(loser.getId()).append("\n")
                .append("username: ").append(loser.getUsername()).append("\n")
                .append("fechaRegistro: ").append(loser.getFechaRegistro()).append("\n")
                .append("porcentajeExito: ").append(loser.getPorcentajeExito()).append("\n");
        return ResponseEntity.ok(response.toString());
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<String> getWinner() {
        Jugador winner = jugadorService.getWinner();
        if (winner == null) {
            return ResponseEntity.ok("No hay jugadores.");
        }
        StringBuilder response = new StringBuilder();
        response.append("id: ").append(winner.getId()).append("\n")
                .append("username: ").append(winner.getUsername()).append("\n")
                .append("fechaRegistro: ").append(winner.getFechaRegistro()).append("\n")
                .append("porcentajeExito: ").append(winner.getPorcentajeExito()).append("\n");
        return ResponseEntity.ok(response.toString());
    }
    @PutMapping("/players/{id}")
    public ResponseEntity<Jugador> updatePlayerName(@PathVariable Long id, @RequestBody Jugador updatedJugador) {
        Jugador existingJugador = jugadorService.findById(id);  // Usar findById que ya maneja el Optional
        
        // Solo actualizar el nombre si se proporciona un nuevo nombre
        if (updatedJugador.getUsername() != null && !updatedJugador.getUsername().isEmpty()) {
            existingJugador.setUsername(updatedJugador.getUsername());
        } else {
            existingJugador.setUsername("ANONIM");
        }
        
        Jugador updated = jugadorService.updateJugador(existingJugador);
        return ResponseEntity.ok(updated);
    }
    
    
}
    



