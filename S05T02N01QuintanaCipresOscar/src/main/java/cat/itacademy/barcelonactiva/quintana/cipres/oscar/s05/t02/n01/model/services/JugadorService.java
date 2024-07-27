package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.services;

import java.util.List;
import java.util.Optional;
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

    public Jugador createJugador(String nom, String password) {
        // Verificar nombres duplicados
        if (nom != null && !nom.isEmpty() && !"ANONIM".equalsIgnoreCase(nom)) {
            Optional<Jugador> existingJugador = jugadorRepository.findByUsername(nom);
            if (existingJugador.isPresent()) {
                throw new IllegalArgumentException("El nombre de usuario ya está en uso");
            }
        }
    
        // Asignar "ANONIM" si el nombre es nulo o está vacío
        String finalNom = (nom == null || nom.isEmpty()) ? "ANONIM" : nom;
        
        Jugador jugador = new Jugador(finalNom, password);
        return jugadorRepository.save(jugador);
    }
    
    public Jugador updateJugador(Jugador jugador) {
        // Verificar nombres duplicados
        if (jugador.getUsername() != null && !jugador.getUsername().isEmpty() && !"ANONIM".equalsIgnoreCase(jugador.getUsername())) {
            Optional<Jugador> existingJugador = jugadorRepository.findByUsername(jugador.getUsername());
            if (existingJugador.isPresent() && !existingJugador.get().getId().equals(jugador.getId())) {
                throw new IllegalArgumentException("El nombre de usuario ya está en uso");
            }
        }
    
        // Asignar "ANONIM" si el nombre es nulo o está vacío
        String finalNom = (jugador.getUsername() == null || jugador.getUsername().isEmpty()) ? "ANONIM" : jugador.getUsername();
        jugador.setUsername(finalNom);
    
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
    
            // Buscar la última partida en la tabla de juegos
            Optional<Game> lastGame = gameRepository.findFirstByOrderByIdDesc();
            Long partidaId;
    
            if (lastGame.isPresent() && !lastGame.get().isGanada()) {
                // Si la última partida no fue ganada, mantener el mismo `partidaId`
                partidaId = lastGame.get().getPartidaId();
            } else {
                // Si no hay partida previa o la última fue ganada, incrementar el `partidaId`
                partidaId = (lastGame.isPresent() ? lastGame.get().getPartidaId() : 0L) + 1;
            }
    
            Game game = new Game(jugador, partidaId);
            gameRepository.save(game);
    
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
