package cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.model.dto;

import java.util.Arrays;
import java.util.List;

public class SucursalDTO {

    private int pk_SucursalID;
    private String nomSucursal;
    private String paisSucursal;
    private String tipusSucursal;
    private static final List<String> paisosUE = Arrays.asList("Austria", "Belgium", "Bulgaria", "Croatia", "Republic of Cyprus", "Czech Republic", "Denmark", "Estonia", "Finland", "France", "Germany", "Greece", "Hungary", "Ireland", "Italy", "Latvia", "Lithuania", "Luxembourg", "Malta", "Netherlands", "Poland", "Portugal", "Romania", "Slovakia", "Slovenia", "Spain", "Sweden");

    // Getters y Setters
    public int getPk_SucursalID() {
        return pk_SucursalID;
    }

    public void setPk_SucursalID(int pk_SucursalID) {
        this.pk_SucursalID = pk_SucursalID;
    }

    public String getNomSucursal() {
        return nomSucursal;
    }

    public void setNomSucursal(String nomSucursal) {
        this.nomSucursal = nomSucursal;
    }

    public String getPaisSucursal() {
        return paisSucursal;
    }

    public void setPaisSucursal(String paisSucursal) {
        this.paisSucursal = paisSucursal;
        setTipusSucursal(paisSucursal);
    }

    public String getTipusSucursal() {
        return tipusSucursal;
    }

    public void setTipusSucursal(String paisSucursal) {
        if (paisosUE.contains(paisSucursal)) {
            this.tipusSucursal = "UE";
        } else {
            this.tipusSucursal = "Fora UE";
        }
    }
}

