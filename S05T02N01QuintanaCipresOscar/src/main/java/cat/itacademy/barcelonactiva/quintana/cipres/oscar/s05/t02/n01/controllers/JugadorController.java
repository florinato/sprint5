package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Jugador;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.services.JugadorService;


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

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> updatePlayerName(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        System.out.println("Actualizando jugador con ID: " + id);

        String newUsername = updates.get("username");
        System.out.println("Nuevo nombre de usuario recibido: " + newUsername);

        Jugador existingJugador = jugadorService.findById(id);

        if (existingJugador == null) {
            System.out.println("Jugador no encontrado: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (newUsername != null && !newUsername.isEmpty()) {
            System.out.println("Estableciendo nuevo nombre de usuario: " + newUsername);
            existingJugador.setUsername(newUsername);
        } else {
            System.out.println("No se proporcionó nuevo nombre de usuario, estableciendo ANONIM");
            existingJugador.setUsername("ANONIM");
        }

        Jugador updated = jugadorService.updateJugador(existingJugador);
        System.out.println("Nombre de usuario actualizado en la base de datos: " + updated.getUsername());
        return ResponseEntity.ok(updated);
    }
}