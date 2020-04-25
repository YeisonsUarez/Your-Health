package co.edu.unab.hernandez.yeison.your_health.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Paciente extends Usuario implements Serializable {
    private ArrayList<CitaMedica> historialCitasMedicas;
    private ArrayList<FormulaMedica> historialFormulasMedicas;

    public Paciente() {
    }

    public ArrayList<CitaMedica> getHistorialCitasMedicas() {
        return historialCitasMedicas;
    }

    public void setHistorialCitasMedicas(ArrayList<CitaMedica> historialCitasMedicas) {
        this.historialCitasMedicas = historialCitasMedicas;
    }

    public ArrayList<FormulaMedica> getHistorialFormulasMedicas() {
        return historialFormulasMedicas;
    }

    public void setHistorialFormulasMedicas(ArrayList<FormulaMedica> historialFormulasMedicas) {
        this.historialFormulasMedicas = historialFormulasMedicas;
    }
}
