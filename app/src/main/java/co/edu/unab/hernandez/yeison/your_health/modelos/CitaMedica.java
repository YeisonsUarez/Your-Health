package co.edu.unab.hernandez.yeison.your_health.modelos;

public class CitaMedica {
    private Paciente paciente;
    private Medico medico;
    private TipoCita tipoCita;
    private String idCita, detallesCita, fechaCita,estadoCita;
    private Cupo cupo;


    public CitaMedica(Paciente paciente, Medico medico, TipoCita tipoCita, String idCita, String detallesCita, Cupo cupo, String fechaCita,String estadoCita) {
        this.paciente = paciente;
        this.medico = medico;
        this.tipoCita = tipoCita;
        this.idCita = idCita;
        this.detallesCita = detallesCita;
        this.cupo = cupo;
        this.fechaCita= fechaCita;
        this.estadoCita= estadoCita;
    }

    public CitaMedica() {
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public TipoCita getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(TipoCita tipoCita) {
        this.tipoCita = tipoCita;
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getDetallesCita() {
        return detallesCita;
    }

    public void setDetallesCita(String detallesCita) {
        this.detallesCita = detallesCita;
    }

    public Cupo getCupo() {
        return cupo;
    }

    public void setCupo(Cupo cupo) {
        this.cupo = cupo;
    }
}
