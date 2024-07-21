package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.services.JugadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/games")
@Tag(name = "GameController", description = "Controlador para manejar las tiradas de dados")
public class GameController {

    @Autowired
    private JugadorService jugadorService;

    @Operation(summary = "Realizar una tirada de dados")
    @PostMapping("/players/{id}")
    public ResponseEntity<Game> playGame(@PathVariable Long id) {
        Game game = jugadorService.playGame(id);
        return ResponseEntity.ok(game);
    }

    @Operation(summary = "Obtener todas las jugadas de un jugador")
    @GetMapping("/players/{id}")
    public ResponseEntity<List<Game>> getPlayerGames(@PathVariable Long id) {
        List<Game> games = jugadorService.getGamesByJugador(id);
        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Eliminar todas las jugadas de un jugador")
    @DeleteMapping("/players/{id}")
    public ResponseEntity<Void> deleteGames(@PathVariable Long id) {
        jugadorService.deleteGames(id);
        return ResponseEntity.noContent().build();
    }
}




