package co.edu.unab.hernandez.yeison.your_health.modelos;

public class Cupo {
    private String idCupo;
    private String lugar;
    private String fecha;
    private Boolean disponible;
    public Cupo(String idCupo, String lugar, String fecha, Boolean disponible) {
        this.idCupo = idCupo;
        this.lugar = lugar;
        this.fecha = fecha;
        this.disponible=disponible;
    }

    public Cupo() {
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getIdCupo() {
        return idCupo;
    }

    public void setIdCupo(String idCupo) {
        this.idCupo = idCupo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
