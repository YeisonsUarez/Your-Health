package co.edu.unab.hernandez.yeison.your_health.modelos;

public class TipoCita {
    private String idTipo;
    private String nombreTipoCita;
    private String detalleTipoCita;
    private String urlImagen;

    public TipoCita() {
    }

    public TipoCita(String idTipo, String nombreTipoCita, String detalleTipoCita, String urlImagen) {
        this.idTipo = idTipo;
        this.nombreTipoCita = nombreTipoCita;
        this.detalleTipoCita = detalleTipoCita;
        this.urlImagen= urlImagen;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombreTipoCita() {
        return nombreTipoCita;
    }

    public void setNombreTipoCita(String nombreTipoCita) {
        this.nombreTipoCita = nombreTipoCita;
    }

    public String getDetalleTipoCita() {
        return detalleTipoCita;
    }

    public void setDetalleTipoCita(String detalleTipoCita) {
        this.detalleTipoCita = detalleTipoCita;
    }
}
