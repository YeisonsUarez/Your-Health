package co.edu.unab.hernandez.yeison.your_health.modelos;

import java.util.ArrayList;

public class AreaDeTrabajo {
    private String idArea;
    private TipoCita tipoCita;
    private String aniosExperiencia;
    private ArrayList<Cupo> cuposAreaTrabajo;

    public AreaDeTrabajo() {
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    public TipoCita getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(TipoCita tipoCita) {
        this.tipoCita = tipoCita;
    }

    public ArrayList<Cupo> getCuposAreaTrabajo() {
        return cuposAreaTrabajo;
    }

    public void setCuposAreaTrabajo(ArrayList<Cupo> cuposAreaTrabajo) {
        this.cuposAreaTrabajo = cuposAreaTrabajo;
    }

    public String getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(String aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }
}
