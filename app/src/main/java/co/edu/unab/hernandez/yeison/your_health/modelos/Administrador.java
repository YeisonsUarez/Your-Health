package co.edu.unab.hernandez.yeison.your_health.modelos;

import java.util.ArrayList;

public class Administrador extends Usuario{

    private ArrayList<CitaMedica> citasAceptadas;

    public Administrador() {
    }

    public ArrayList<CitaMedica> getCitasAceptadas() {
        return citasAceptadas;
    }

    public void setCitasAceptadas(ArrayList<CitaMedica> citasAceptadas) {
        this.citasAceptadas = citasAceptadas;
    }
}
