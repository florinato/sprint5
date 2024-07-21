package cat.itacademy.barcelonactiva.quintana.cipres.oscar.s05.t01.n02.model.dto;

import java.util.Arrays;
import java.util.List;

public class FlorDTO {
    private Integer pk_FlorID;
    private String nomFlor;
    private String paisFlor;
    private String tipusFlor;

    private static final List<String> paisosUE = Arrays.asList("España", "Francia", "Alemania", "Italia"); // Ejemplo de países UE

    public FlorDTO() {
        // Constructor vacío
    }

    public FlorDTO(Integer pk_FlorID, String nomFlor, String paisFlor) {
        this.pk_FlorID = pk_FlorID;
        this.nomFlor = nomFlor;
        this.paisFlor = paisFlor;
        this.tipusFlor = paisosUE.contains(paisFlor) ? "UE" : "Fora UE";
    }

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

    public String getTipusFlor() {
        return tipusFlor;
    }

    public void setTipusFlor(String tipusFlor) {
        this.tipusFlor = tipusFlor;
    }

    public static List<String> getPaisosue() {
        return paisosUE;
    }

}
