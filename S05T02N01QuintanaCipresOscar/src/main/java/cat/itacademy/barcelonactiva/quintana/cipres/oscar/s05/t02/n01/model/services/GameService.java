package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Game;
import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game save(Game game) {
        return gameRepository.save(game);
    }

    public List<Game> findByJugadorId(Long jugadorId) {
        return gameRepository.findByJugadorId(jugadorId);
    }


}

