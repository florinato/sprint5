package cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk_SucursalID;
    private String nomSucursal;
    private String paisSucursal;
    private String tipusSucursal;
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
    }

    public String getTipusSucursal() {
        return tipusSucursal;
    }

    public void setTipusSucursal(String tipusSucursal) {
        this.tipusSucursal = tipusSucursal;
    }

    
}

