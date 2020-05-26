package co.edu.unab.hernandez.yeison.your_health.modelos;

import java.util.ArrayList;

public class FormulaMedica {
    private String fechaEntrega, fechaMaximaEntrega,lugarPedido;
    private CitaMedica citaMedica;
    private ArrayList<Medicamento> medicamentos;

    public FormulaMedica(String fechaEntrega, String fechaMaximaEntrega, String lugarPedido, CitaMedica citaMedica, ArrayList<Medicamento> medicamentos) {
        this.fechaEntrega = fechaEntrega;
        this.fechaMaximaEntrega = fechaMaximaEntrega;
        this.lugarPedido = lugarPedido;
        this.citaMedica = citaMedica;
        this.medicamentos = medicamentos;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaMaximaEntrega() {
        return fechaMaximaEntrega;
    }

    public void setFechaMaximaEntrega(String fechaMaximaEntrega) {
        this.fechaMaximaEntrega = fechaMaximaEntrega;
    }

    public String getLugarPedido() {
        return lugarPedido;
    }

    public void setLugarPedido(String lugarPedido) {
        this.lugarPedido = lugarPedido;
    }

    public CitaMedica getCitaMedica() {
        return citaMedica;
    }

    public void setCitaMedica(CitaMedica citaMedica) {
        this.citaMedica = citaMedica;
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(ArrayList<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }
}
