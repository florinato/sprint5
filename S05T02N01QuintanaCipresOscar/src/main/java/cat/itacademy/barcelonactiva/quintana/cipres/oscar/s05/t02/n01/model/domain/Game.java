package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long partidaId; // Nuevo campo para identificar la partida
    private int dado1;
    private int dado2;
    private boolean ganada;
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    @JsonBackReference
    private Jugador jugador;

    public Game() {
    }

    public Game(Jugador jugador, Long partidaId) {
        this.jugador = jugador;
        this.partidaId = partidaId;
        this.dado1 = (int) (Math.random() * 6) + 1;
        this.dado2 = (int) (Math.random() * 6) + 1;
        this.ganada = (this.dado1 + this.dado2) == 7;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(Long partidaId) {
        this.partidaId = partidaId;
    }

    public int getDado1() {
        return dado1;
    }

    public void setDado1(int dado1) {
        this.dado1 = dado1;
    }

    public int getDado2() {
        return dado2;
    }

    public void setDado2(int dado2) {
        this.dado2 = dado2;
    }

    public boolean isGanada() {
        return ganada;
    }

    public void setGanada(boolean ganada) {
        this.ganada = ganada;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
