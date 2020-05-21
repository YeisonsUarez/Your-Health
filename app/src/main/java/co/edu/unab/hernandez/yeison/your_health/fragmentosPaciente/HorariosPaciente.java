package co.edu.unab.hernandez.yeison.your_health.fragmentosPaciente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.CuposAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.MedicosAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.TiposCitasAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.CitaMedica;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.TipoCita;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HorariosPaciente extends Fragment {
    private View view;
    private ArrayList<Medico> medicos;
    private ArrayList<Cupo> cupos;
    private ArrayList<TipoCita> listaTiposCitas;
    private CitaMedica citaMedica;
    private Switch switchTipoCita;
    private Button siguienteUno,atrasUno,atrasDos,atrasTipoCita;
    private CalendarView calendarView;
    private Paciente paciente;
    private ConstraintLayout pasoUno,pasoDos,pasoTres,pasoCuatro;
    private RecyclerView medicosList,horasList,tipoCitasEs;
    private RecyclerView.LayoutManager manager, managerHoras,managerTipoCitaEs;
    public HorariosPaciente() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_horarios_paciente, container, false);
        paciente= (Paciente) getArguments().getSerializable(getString(R.string.textPaciente));
        asignarObjetos();
        configurarCalendario();
        manager = new LinearLayoutManager(getContext());
        managerHoras = new LinearLayoutManager(getContext());
        managerTipoCitaEs = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        citaMedica= new CitaMedica();
        citaMedica.setPaciente(paciente);
        inicarRecyclerMedicos();
        iniciarRecyclerHoras();
        iniciarRecyclerTipoCita();
        operacionesBotones();
        return  view;
    }


    private void asignarObjetos() {
        switchTipoCita= view.findViewById(R.id.tipoCita);
        siguienteUno= view.findViewById(R.id.siguienteUno);
        atrasUno= view.findViewById(R.id.atrasUno);
        atrasDos= view.findViewById(R.id.atrasDos);
        atrasTipoCita= view.findViewById(R.id.atrasTipoCita);
        calendarView= view.findViewById(R.id.calendarioGeneral);
        pasoUno= view.findViewById(R.id.pasoUno);
        pasoDos= view.findViewById(R.id.pasoDos);
        pasoTres= view.findViewById(R.id.pasoTres);
        pasoCuatro= view.findViewById(R.id.pasoTipoCita);
        medicosList= view.findViewById(R.id.listaDoctores);
        horasList= view.findViewById(R.id.listHoras);
        tipoCitasEs= view.findViewById(R.id.tiposCitasEs);
    }
    public void configurarCalendario(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,Calendar.getInstance().getActualMinimum(Calendar.DATE));
        long date = calendar.getTime().getTime();
        calendarView.setMinDate(date);
    }



    private void iniciarRecyclerHoras() {
        agregarCupos();
        CuposAdapter adapter= new CuposAdapter(cupos, new CuposAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Cupo cupo, int posicion) {
                citaMedica.setCupo(cupo);
                confirmarCita();
            }


        }, getActivity());
        horasList.setLayoutManager(managerHoras);
        horasList.setAdapter(adapter);
    }

    private void agregarCupos() {
        cupos= new ArrayList<>();
        for(int i =0; i<10;i++){
            Cupo cupo= new Cupo("1234"+i,"sala 3-2","2:00pm",getString(R.string.noDisponible));
            cupos.add(cupo);
        }

    }
    private void iniciarRecyclerTipoCita(){
        addTipoCitas();
        TiposCitasAdapter adapter= new TiposCitasAdapter(listaTiposCitas, new TiposCitasAdapter.onItemClickListener() {
            @Override
            public void onItemClick(TipoCita tiposCitasAdapter, int posicion) {
                citaMedica.setTipoCita(tiposCitasAdapter);
                pasoCuatro.setVisibility(View.INVISIBLE);
                pasoDos.setVisibility(View.VISIBLE);
            }
        }, getActivity());
        tipoCitasEs.setLayoutManager(managerTipoCitaEs);
        tipoCitasEs.setAdapter(adapter);


    }


    private void  addTipoCitas(){
        listaTiposCitas= new ArrayList<>();
        listaTiposCitas.add(new TipoCita("1234","Odontologia","Cita para revisión de dientes",getString(R.string.fotoOdontologia)));
        listaTiposCitas.add(new TipoCita("12345","Fisioterapia","Cita para reparación y restauración de la movilidad física",getString(R.string.fotoFisioterapia)));

    }
    public void inicarRecyclerMedicos(){
        addMedicos();
        MedicosAdapter adapter= new MedicosAdapter(medicos, new MedicosAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Medico medico, int posicion) {
                citaMedica.setMedico(medico);
                pasoDos.setVisibility(View.INVISIBLE);
                pasoTres.setVisibility(View.VISIBLE);

            }
        }, getActivity());
        medicosList.setLayoutManager(manager);
        medicosList.setAdapter(adapter);
    }
    public void addMedicos(){
        medicos= new ArrayList<>();
        for(int i=0;i<=4;i++){
            Medico medico= new Medico();
            medico.setNombreUsuario("Mr.robot");
            medico.setDescripcion("Confiable y seguro");
            medico.setFotoPerfil("https://pm1.narvii.com/6236/d49bb1b56cb7caff2d71aa2c0182ef6b1e079261_00.jpg");
            medicos.add(medico);
        }
    }



    private void confirmarCita() {
        citaMedica.setEstadoCita(getString(R.string.textPendiente));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.titleConfirmar);

        builder.setMessage(getString(R.string.textNumeroDoc)+citaMedica.getPaciente().getNumeroDocumento()+getString(R.string.textDetalleTipoCita)+citaMedica.getTipoCita().getNombreTipoCita()+getString(R.string.textDetalleFecha)+citaMedica.getFechaCita()+getString(R.string.textConDocM)+citaMedica.getMedico().getNombreUsuario()+getString(R.string.textHora)+citaMedica.getCupo().getFecha()+getString(R.string.textLugar)+citaMedica.getCupo().getLugar()+". "+getString(R.string.confirmarCita))
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), getString(R.string.mensajeEnvioSolicitud), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.denegar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
         builder.create();
         builder.show();
    }


  //  String idUsuario, String aniosExperiencia, ArrayList<String> areasDeTrabajo, ArrayList<CitaMedica> citasRealizadas, ArrayList<FormulaMedica> formulasDadas, String descripcion

    public  void  operacionesBotones(){
        siguienteUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDate = sdf.format(new Date(calendarView.getDate()));
                citaMedica.setFechaCita(selectedDate);
                if(switchTipoCita.isChecked()){

                    switchTipoCita.setVisibility(View.INVISIBLE);
                    pasoUno.setVisibility(View.INVISIBLE);
                    pasoDos.setVisibility(View.VISIBLE);
                    TipoCita tipoCitaMedica = new TipoCita("22",getString(R.string.nombreGeneral),getString(R.string.detalleGeneral),getString(R.string.UrlCitaGeneral));
                    citaMedica.setTipoCita(tipoCitaMedica);
                }else{
                    switchTipoCita.setVisibility(View.INVISIBLE);
                    pasoUno.setVisibility(View.INVISIBLE);
                    pasoCuatro.setVisibility(View.VISIBLE);
                }


            }
        });
        atrasUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoDos.setVisibility(View.INVISIBLE);
                switchTipoCita.setVisibility(View.VISIBLE);
                pasoUno.setVisibility(View.VISIBLE);
            }
        });
        atrasDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoTres.setVisibility(View.INVISIBLE);
                pasoDos.setVisibility(View.VISIBLE);
            }
        });
        atrasTipoCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoCuatro.setVisibility(View.INVISIBLE);
                switchTipoCita.setVisibility(View.VISIBLE);
                pasoUno.setVisibility(View.VISIBLE);

            }
        });

    }
}
