package co.edu.unab.hernandez.yeison.your_health.fragmentosPaciente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
public class CitasPaciente extends Fragment {
    private View view;
    private ArrayList<CitaMedica> citaMedicas;
    private RecyclerView listaCitasPaciente;
    private RecyclerView.LayoutManager manager;
    private Paciente paciente;

    public CitasPaciente() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_citas_paciente, container, false);
        paciente= (Paciente) getArguments().getSerializable(getString(R.string.textPaciente));
        manager = new LinearLayoutManager(getContext());
        asociarElementos();
        iniciarListaCitasPaciente();
        return view;
    }

    private void iniciarListaCitasPaciente() {
        agregarCitas();
        CitasMedicasAdapter adapter= new CitasMedicasAdapter(citaMedicas, new CitasMedicasAdapter.onItemClickListener() {
            @Override
            public void onItemClick(CitaMedica citaMedica, int posicion) {

                Toast.makeText(getContext(), ""+citaMedica.getMedico().getNombreUsuario(), Toast.LENGTH_SHORT).show();
            }


        }, getActivity());
        listaCitasPaciente.setLayoutManager(manager);
        listaCitasPaciente.setAdapter(adapter);
    }

    private void agregarCitas() {
        citaMedicas= new ArrayList<>();
        CitaMedica citaMedica= new CitaMedica();
        citaMedica.setPaciente(paciente);
        Medico medico= new Medico();
        medico.setNombreUsuario("Sherlock Holmes");
        medico.setNumeroDocumento("1234567");
        citaMedica.setMedico(medico);
        citaMedica.setEstadoCita(getString(R.string.textPendiente));
        TipoCita tipoCita= new TipoCita();
        tipoCita.setNombreTipoCita("Odontologia");
        citaMedica.setTipoCita(tipoCita);
        citaMedica.setFechaCita("23-agusto-2020");
        Cupo cupo= new Cupo();
        cupo.setLugar("Sala 3-1");
        cupo.setFecha("3:00pm");
        citaMedica.setCupo(cupo);
        for(int i =0;i<8;i++) {
            citaMedicas.add(citaMedica);
        }
        }

    private void asociarElementos() {
        listaCitasPaciente= view.findViewById(R.id.listCitasPaciente);
    }
}
