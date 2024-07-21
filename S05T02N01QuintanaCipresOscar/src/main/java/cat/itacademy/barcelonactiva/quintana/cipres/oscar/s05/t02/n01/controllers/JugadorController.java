package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Jugador;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.services.JugadorService;

@RestController
@RequestMapping("/api/players")
public class JugadorController {

    private static final Logger logger = LoggerFactory.getLogger(JugadorController.class);

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<List<Jugador>> getAllPlayers() {
        logger.info("GET /api/players called");
        List<Jugador> jugadores = jugadorService.getAllJugadoresOrdenados();
        return ResponseEntity.ok(jugadores);
    }

    @GetMapping("/{id}/games")
    public ResponseEntity<List<Game>> getPlayerGames(@PathVariable Long id) {
        logger.info("GET /api/players/{id}/games called");
        List<Game> games = jugadorService.getGamesByJugador(id);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/ranking")
    public ResponseEntity<Double> getPlayersRanking() {
        logger.info("GET /api/players/ranking called");
        double ranking = jugadorService.getRanking();
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<Jugador> getWorstPlayer() {
        logger.info("GET /api/players/ranking/loser called");
        Jugador worstPlayer = jugadorService.getLoser();
        return ResponseEntity.ok(worstPlayer);
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<Jugador> getBestPlayer() {
        logger.info("GET /api/players/ranking/winner called");
        Jugador bestPlayer = jugadorService.getWinner();
        return ResponseEntity.ok(bestPlayer);
    }
}

