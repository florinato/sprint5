package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Game;


public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByJugadorId(Long jugadorId);
    Optional<Game> findFirstByOrderByIdDesc();
}

