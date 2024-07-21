package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Jugador;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.repository.GameRepository;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.repository.JugadorRepository;

@Service
public class JugadorService implements UserDetailsService {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private GameRepository gameRepository;
    
    private final  AtomicLong partidaCounter = new AtomicLong(); // Contador para las partidas

    public Jugador createJugador(String nom) {
        Jugador jugador = new Jugador();
        jugador.setUsername(nom != null && !nom.isEmpty() ? nom : "ANONIM");
        jugador.setFechaRegistro(LocalDateTime.now());
        return jugadorRepository.save(jugador);
    }

    public Jugador updateJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public Optional<Jugador> getJugadorById(Long id) {
        return jugadorRepository.findById(id);
    }

    public Jugador findById(Long id) {
        return jugadorRepository.findById(id).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
    }

    public List<Jugador> getAllJugadoresOrdenados() {
        return jugadorRepository.findAll().stream()
            .map(jugador -> {
                jugador.setPorcentajeExito(jugador.calcularPorcentajeExito());
                return jugador;
            })
            .sorted((j1, j2) -> Double.compare(j2.getPorcentajeExito(), j1.getPorcentajeExito()))
            .map(jugador -> {
                jugador.setPartidas(null); // No incluir las partidas en la respuesta
                return jugador;
            })
            .collect(Collectors.toList());
    }

    public void deleteJugador(Long id) {
        jugadorRepository.deleteById(id);
    }

    public void deleteAllJugadores() {
        jugadorRepository.deleteAll();
    }

    public Game playGame(Long id) {
        Optional<Jugador> jugadorOptional = jugadorRepository.findById(id);
        if (jugadorOptional.isPresent()) {
            Jugador jugador = jugadorOptional.get();
            Long partidaId = partidaCounter.incrementAndGet(); // Nueva partida
            Game game;
            
            do {
                game = new Game(jugador, partidaId);
                gameRepository.save(game);
            } while (!game.isGanada());
            
            double porcentajeExito = jugador.calcularPorcentajeExito();
            jugador.setPorcentajeExito(porcentajeExito);
            jugadorRepository.save(jugador);
            
            return game;
        }
        return null;
    }

    public void deleteGames(Long id) {
        List<Game> games = gameRepository.findByJugadorId(id);
        gameRepository.deleteAll(games);
    }

    public List<Game> getGamesByJugador(Long id) {
        return gameRepository.findByJugadorId(id);
    }

    public double getRanking() {
        List<Jugador> jugadores = jugadorRepository.findAll();
        double totalGames = 0;
        double totalWins = 0;

        for (Jugador jugador : jugadores) {
            List<Game> games = gameRepository.findByJugadorId(jugador.getId());
            totalGames += games.size();
            for (Game game : games) {
                if (game.isGanada()) {
                    totalWins += 1;
                }
            }
        }
        return totalGames > 0 ? (totalWins / totalGames) * 100 : 0;
    }

    public Jugador getLoser() {
        List<Jugador> jugadores = jugadorRepository.findAll();
        Jugador loser = null;
        double worstRanking = 100;

        for (Jugador jugador : jugadores) {
            List<Game> games = gameRepository.findByJugadorId(jugador.getId());
            double totalGames = games.size();
            double totalWins = 0;

            for (Game game : games) {
                if (game.isGanada()) {
                    totalWins += 1;
                }
            }
            double ranking = totalGames > 0 ? (totalWins / totalGames) * 100 : 0;
            if (ranking < worstRanking) {
                worstRanking = ranking;
                loser = jugador;
            }
        }
        return loser;
    }

    public Jugador getWinner() {
        List<Jugador> jugadores = jugadorRepository.findAll();
        Jugador winner = null;
        double bestRanking = 0;

        for (Jugador jugador : jugadores) {
            List<Game> games = gameRepository.findByJugadorId(jugador.getId());
            double totalGames = games.size();
            double totalWins = 0;

            for (Game game : games) {
                if (game.isGanada()) {
                    totalWins += 1;
                }
            }
            double ranking = totalGames > 0 ? (totalWins / totalGames) * 100 : 0;
            if (ranking > bestRanking) {
                bestRanking = ranking;
                winner = jugador;
            }
        }
        return winner;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Jugador> jugador = jugadorRepository.findByUsername(username);
        if (jugador.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Jugador user = jugador.get();
        return User.withUsername(user.getUsername())
                   .password(user.getPassword())
                   .roles("USER")
                   .build();
    }

    public Jugador save(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }
}
