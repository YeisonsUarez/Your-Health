package co.edu.unab.hernandez.yeison.your_health.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Medico extends Usuario implements Serializable {

    private String aniosExperiencia;
    private ArrayList<AreaDeTrabajo> areasDeTrabajo;
    private ArrayList<CitaMedica> citasRealizadas;
    private ArrayList<FormulaMedica> formulasDadas;
    private String descripcion;


    public Medico() {
    }

    public Medico(String idUsuario, String aniosExperiencia, ArrayList<AreaDeTrabajo> areasDeTrabajo, ArrayList<CitaMedica> citasRealizadas, ArrayList<FormulaMedica> formulasDadas, String descripcion) {

        this.aniosExperiencia = aniosExperiencia;
        this.areasDeTrabajo = areasDeTrabajo;
        this.citasRealizadas = citasRealizadas;
        this.formulasDadas = formulasDadas;
        this.descripcion = descripcion;
    }


    public String getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(String aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public ArrayList<AreaDeTrabajo> getAreasDeTrabajo() {
        return areasDeTrabajo;
    }

    public void setAreasDeTrabajo(ArrayList<AreaDeTrabajo> areasDeTrabajo) {
        this.areasDeTrabajo = areasDeTrabajo;
    }

    public ArrayList<CitaMedica> getCitasRealizadas() {
        return citasRealizadas;
    }

    public void setCitasRealizadas(ArrayList<CitaMedica> citasRealizadas) {
        this.citasRealizadas = citasRealizadas;
    }

    public ArrayList<FormulaMedica> getFormulasDadas() {
        return formulasDadas;
    }

    public void setFormulasDadas(ArrayList<FormulaMedica> formulasDadas) {
        this.formulasDadas = formulasDadas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
