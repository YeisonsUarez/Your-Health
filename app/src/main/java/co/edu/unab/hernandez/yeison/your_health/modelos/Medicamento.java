package co.edu.unab.hernandez.yeison.your_health.modelos;

public class Medicamento {
    private String nombreMedicamento, detalleMedicamento, cantidadMedicamento;
    private Medico medico;
    private Paciente paciente;

    public Medicamento(String nombreMedicamento, String detalleMedicamento, String cantidadMedicamento, Medico medico, Paciente paciente) {
        this.nombreMedicamento = nombreMedicamento;
        this.detalleMedicamento = detalleMedicamento;
        this.cantidadMedicamento = cantidadMedicamento;
        this.medico = medico;
        this.paciente = paciente;
    }

    public Medicamento() {
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getDetalleMedicamento() {
        return detalleMedicamento;
    }

    public void setDetalleMedicamento(String detalleMedicamento) {
        this.detalleMedicamento = detalleMedicamento;
    }

    public String getCantidadMedicamento() {
        return cantidadMedicamento;
    }

    public void setCantidadMedicamento(String cantidadMedicamento) {
        this.cantidadMedicamento = cantidadMedicamento;
    }


}
