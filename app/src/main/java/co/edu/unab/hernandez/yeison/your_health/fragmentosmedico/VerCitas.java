package co.edu.unab.hernandez.yeison.your_health.fragmentosmedico;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.CitasMedicasAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.CitaMedica;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.TipoCita;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerCitas extends Fragment {
    private Medico medico;
    private View view;
    private ArrayList<CitaMedica> citaMedicas;

    private RecyclerView listaCitas;
    private RecyclerView.LayoutManager manager;
    private AlertDialog alert;


    public VerCitas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        medico = (Medico) getArguments().getSerializable(getString(R.string.textMedico));
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ver_citas, container, false);
        manager = new LinearLayoutManager(getContext());
        asociarElementos();
        iniciarListaCitas();
        return view;
    }

    private void iniciarListaCitas() {
        agregarCitas();
        CitasMedicasAdapter adapter = new CitasMedicasAdapter(citaMedicas, new CitasMedicasAdapter.onItemClickListener() {
            @Override
            public void onItemClick(CitaMedica citaMedica, int posicion) {
                DetalleCita(citaMedica);
            }


        }, getActivity());
        listaCitas.setLayoutManager(manager);
        listaCitas.setAdapter(adapter);
    }

    public void asociarElementos() {
        listaCitas = view.findViewById(R.id.listCitasDoctor);
    }

    private void agregarCitas() {
        citaMedicas = new ArrayList<>();
        CitaMedica citaMedica = new CitaMedica();
        Paciente paciente = new Paciente();
        paciente.setTipoDocumento("Cedula");
        paciente.setNumeroDocumento("12121");
        paciente.setNombreUsuario("Elliot Alderson");
        paciente.setCorreoUsuario("ygmail.com");
        paciente.setEdadUsuario("20");
        paciente.setSexo("Masculino");
        paciente.setFotoPerfil("https://cadenaser00.epimg.net/ser/imagenes/2015/03/31/television/1427762051_794989_1427763164_noticia_normal.jpg");
        citaMedica.setPaciente(paciente);
        citaMedica.setMedico(medico);
        citaMedica.setEstadoCita(getString(R.string.textPendiente));
        TipoCita tipoCita = new TipoCita();
        tipoCita.setNombreTipoCita("Odontologia");
        citaMedica.setTipoCita(tipoCita);
        citaMedica.setFechaCita("23-agusto-2020");
        Cupo cupo = new Cupo();
        cupo.setLugar("Sala 3-1");
        cupo.setFecha("3:00pm");
        citaMedica.setCupo(cupo);
        for (int i = 0; i < 8; i++) {
            citaMedicas.add(citaMedica);
        }
    }
    private CitaMedica DetalleCita(final CitaMedica citaMedica){
        LayoutInflater inflater = getLayoutInflater();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View dialoglayout = inflater.inflate(R.layout.dialogcitadoctor, null);
        final TextView nombre,edad,genero,tipoynumeroDoc,fechaHora,lugar,tipo,estado, correo;
        Button terminarCita;
        ImageButton salir;
        ImageView foto;
        foto= dialoglayout.findViewById(R.id.fotoPerfilPaciente);
        correo= dialoglayout.findViewById(R.id.correoPacienteDoc);
        Glide.with(getContext()).load(citaMedica.getPaciente().getFotoPerfil()).apply(RequestOptions.circleCropTransform()).into(foto);
        nombre= dialoglayout.findViewById(R.id.nombrePacienteDoc);
        edad= dialoglayout.findViewById(R.id.edadPacienteDoc);
        genero= dialoglayout.findViewById(R.id.generoDocumento);
        tipoynumeroDoc= dialoglayout.findViewById(R.id.tipoynumeroDocumento2);
        fechaHora= dialoglayout.findViewById(R.id.fechayhora);
        lugar= dialoglayout.findViewById(R.id.lugarDoc);
        tipo= dialoglayout.findViewById(R.id.tipoCitaDoc);
        estado= dialoglayout.findViewById(R.id.estadoCita);
        salir= dialoglayout.findViewById(R.id.btnCerrarDialog);
        terminarCita= dialoglayout.findViewById(R.id.btnFinalizarCita);
        nombre.setText(citaMedica.getPaciente().getNombreUsuario());
        correo.setText(citaMedica.getPaciente().getCorreoUsuario());
        edad.setText(getString(R.string.textEdad)+citaMedica.getPaciente().getEdadUsuario());
        genero.setText(getString(R.string.textSexo)+citaMedica.getPaciente().getSexo());
        tipoynumeroDoc.setText(citaMedica.getPaciente().getTipoDocumento()+citaMedica.getPaciente().getNumeroDocumento());
        fechaHora.setText(citaMedica.getFechaCita()+" "+citaMedica.getCupo().getFecha());
        lugar.setText(citaMedica.getCupo().getLugar());
        tipo.setText(citaMedica.getTipoCita().getNombreTipoCita());
        estado.setText(citaMedica.getEstadoCita());

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.dismiss();

            }
        });

        terminarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), getString(R.string.citaTerminada), Toast.LENGTH_LONG).show();
                citaMedica.setEstadoCita(getString(R.string.citaFinalizada));
                alert.dismiss();
            }
        });
        builder.setView(dialoglayout);
        alert = builder.create();
        alert.show();
        //builder.show();
        return citaMedica;
    }
}