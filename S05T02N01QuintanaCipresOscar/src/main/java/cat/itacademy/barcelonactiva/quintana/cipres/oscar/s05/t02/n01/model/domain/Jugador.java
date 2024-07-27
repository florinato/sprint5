package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t02.n01.model.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Jugador {



    public Jugador() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private LocalDateTime fechaRegistro;
    private double porcentajeExito;

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
    
    private List<Game> partidas;

    public Jugador(String username,  String password) {
        this.username = username;
        this.fechaRegistro = LocalDateTime.now();
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }



    public double getPorcentajeExito() {
        return porcentajeExito;
    }

    public void setPorcentajeExito(double porcentajeExito) {
        this.porcentajeExito = porcentajeExito;
    }

    public List<Game> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Game> partidas) {
        this.partidas = partidas;
    }

    // Método para calcular la tasa de éxito
    public double calcularPorcentajeExito() {
        long totalGames = partidas.size();
        long wonGames = partidas.stream().filter(Game::isGanada).count();
        return totalGames == 0 ? 0 : (double) wonGames / totalGames * 100;
    }



    


}

