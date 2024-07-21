package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FlorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk_FlorID;
    private String nomFlor;
    private String paisFlor;
    public Integer getPk_FlorID() {
        return pk_FlorID;
    }
    public void setPk_FlorID(Integer pk_FlorID) {
        this.pk_FlorID = pk_FlorID;
    }
    public String getNomFlor() {
        return nomFlor;
    }
    public void setNomFlor(String nomFlor) {
        this.nomFlor = nomFlor;
    }
    public String getPaisFlor() {
        return paisFlor;
    }
    public void setPaisFlor(String paisFlor) {
        this.paisFlor = paisFlor;
    }

}
