package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    

    public Optional<Jugador> findByUsername(String username);

    
}

