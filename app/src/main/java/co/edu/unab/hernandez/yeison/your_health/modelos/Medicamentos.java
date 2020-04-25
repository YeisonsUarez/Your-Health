package co.edu.unab.hernandez.yeison.your_health.modelos;

public class Medicamentos {
    private String nombreMedicamento, detalleMedicamento, cantidadMedicamento,dosisMedicamento;

    public Medicamentos(String nombreMedicamento, String detalleMedicamento, String cantidadMedicamento, String dosisMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
        this.detalleMedicamento = detalleMedicamento;
        this.cantidadMedicamento = cantidadMedicamento;
        this.dosisMedicamento = dosisMedicamento;
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

    public String getDosisMedicamento() {
        return dosisMedicamento;
    }

    public void setDosisMedicamento(String dosisMedicamento) {
        this.dosisMedicamento = dosisMedicamento;
    }
}