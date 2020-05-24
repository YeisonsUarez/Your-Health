package co.edu.unab.hernandez.yeison.your_health.fragmentosPaciente;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.CuposAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.MedicosAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.TiposCitasAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.CitaMedica;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.TipoCita;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;

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
    private ProgressDialog progress;
    private JsonObjectRequest jsonObjectRequestMedicos;
    private JsonObjectRequest jsonObjectRequestTipoCita;
    private JsonObjectRequest jsonObjectRequestCupos;

    private StringRequest stringRequestCrearCita;
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
        cupos= new ArrayList<>();
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando cupos...");
        progress.show();
        String idTipocita=citaMedica.getTipoCita().getIdTipo();
        String idMedico= citaMedica.getMedico().getNumeroDocumento();
        String url=getString(R.string.obtenerDatos,getString(R.string.nameServer),"cupo",idMedico,idTipocita);

        jsonObjectRequestCupos=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Cupo cupo= null;
                progress.dismiss();
                JSONArray json=response.optJSONArray("usuario");
                try {

                    for (int i=0;i<json.length();i++){
                        cupo=new Cupo();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);
                        cupo.setIdCupo(jsonObject.optString("idCupo"));
                        cupo.setFecha(jsonObject.optString("hora"));
                        cupo.setLugar(jsonObject.optString("lugar"));
                        cupo.setDisponible(jsonObject.optString("disponible"));
                        cupos.add(cupo);
                    }

                    CuposAdapter adapter= new CuposAdapter(cupos, new CuposAdapter.onItemClickListener() {
                        @Override
                        public void onItemClick(Cupo cupo, int posicion) {
                            citaMedica.setCupo(cupo);
                            confirmarCita();
                        }


                    }, getActivity());
                    horasList.setLayoutManager(managerHoras);
                    horasList.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                            " "+response, Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(getContext(), "No se puede conectar "+error.toString()+error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString()+error.getMessage());


            }
        });
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequestCupos);

    }


    private void iniciarRecyclerTipoCita() {
        medicos= new ArrayList<>();
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando Tipo Cita...");
        progress.show();


        String url=getString(R.string.obtenerDatos,getString(R.string.nameServer),"tipocita",paciente.getInstitucion(),"");

        jsonObjectRequestTipoCita=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listaTiposCitas= new ArrayList<>();
                TipoCita tipoCita= null;
                progress.dismiss();
                JSONArray json=response.optJSONArray("usuario");
                try {

                    for (int i=0;i<json.length();i++){
                        tipoCita=new TipoCita();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);
                        tipoCita.setUrlImagen(jsonObject.optString("urlImagenCita"));
                        tipoCita.setDetalleTipoCita(jsonObject.optString("detalleTipoCita"));
                        tipoCita.setNombreTipoCita(jsonObject.optString("nombreTipoCita"));
                        tipoCita.setIdTipo(jsonObject.optString("idTipoCita"));
                        listaTiposCitas.add(tipoCita);
                    }
                    TiposCitasAdapter adapter= new TiposCitasAdapter(listaTiposCitas, new TiposCitasAdapter.onItemClickListener() {
                        @Override
                        public void onItemClick(TipoCita tiposCitasAdapter, int posicion) {
                            citaMedica.setTipoCita(tiposCitasAdapter);
                            inicarRecyclerMedicos();
                            pasoCuatro.setVisibility(View.INVISIBLE);
                            pasoDos.setVisibility(View.VISIBLE);
                        }
                    }, getActivity());
                    tipoCitasEs.setLayoutManager(managerTipoCitaEs);
                    tipoCitasEs.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                            " "+response, Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(getContext(), "No se puede conectar "+error.toString()+error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString()+error.getMessage());


            }
        });
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequestTipoCita);

    }





    private void inicarRecyclerMedicos() {
        medicos= new ArrayList<>();
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando Medico...");
        progress.show();

        String idTipocita= citaMedica.getTipoCita().getIdTipo();
        String url=getString(R.string.obtenerDatos,getString(R.string.nameServer),"medico",paciente.getInstitucion(),idTipocita);

        jsonObjectRequestMedicos=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Medico medico= null;
                progress.dismiss();
                JSONArray json=response.optJSONArray("usuario");
                try {

                    for (int i=0;i<json.length();i++){
                        medico=new Medico();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);

                        medico.setNumeroDocumento(jsonObject.optString("numeroDocumento"));
                        medico.setNombreUsuario(jsonObject.optString("nombreUsuario"));
                        medico.setCorreoUsuario(jsonObject.optString("correoUsuario"));
                        medico.setFotoPerfil(jsonObject.optString("fotoPerfilUsuario"));
                        medico.setDescripcion(jsonObject.optString("descripcionMedico"));
                        medico.setTelefono(jsonObject.optString("telefonoUsuario"));
                        medico.setSexo(jsonObject.optString("sexoUsuario"));
                        medico.setTipoDocumento(jsonObject.optString("tipoDocumento"));
                        medico.setFechaNacimientoUsuario(jsonObject.optString("fechaNacimientoUsuario"));
                        medicos.add(medico);
                    }
                    MedicosAdapter adapter= new MedicosAdapter(medicos, new MedicosAdapter.onItemClickListener() {
                        @Override
                        public void onItemClick(Medico medico, int posicion) {
                            citaMedica.setMedico(medico);
                            iniciarRecyclerHoras();
                            pasoDos.setVisibility(View.INVISIBLE);
                            pasoTres.setVisibility(View.VISIBLE);

                        }
                    }, getActivity());
                    medicosList.setLayoutManager(manager);
                    medicosList.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                            " "+response, Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(getContext(), "No se puede conectar "+error.toString()+error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString()+error.getMessage());


            }
        });
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequestMedicos);

    }



    private void confirmarCita() {
        citaMedica.setEstadoCita(getString(R.string.textPendiente));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.titleConfirmar);

        builder.setMessage(getString(R.string.textNumeroDoc)+citaMedica.getPaciente().getNumeroDocumento()+getString(R.string.textDetalleTipoCita)+citaMedica.getTipoCita().getNombreTipoCita()+getString(R.string.textDetalleFecha)+citaMedica.getFechaCita()+getString(R.string.textConDocM)+citaMedica.getMedico().getNombreUsuario()+getString(R.string.textHora)+citaMedica.getCupo().getFecha()+getString(R.string.textLugar)+citaMedica.getCupo().getLugar()+". "+getString(R.string.confirmarCita))
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        crearCita();
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
    private void crearCita(){

        progress=new ProgressDialog(getContext());
        progress.setMessage("Cargando...");
        progress.show();


        String url= getString(R.string.urlOperacionesCita,getString(R.string.nameServer));

        stringRequestCrearCita=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progress.dismiss();

                if (response.trim().equalsIgnoreCase("registra")){
                    Toast.makeText(getContext(),"Se ha registrado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No se ha registrado "+response,Toast.LENGTH_LONG).show();
                    Log.i("RESPUESTA: ",""+response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Log.i("ERRORVOLLEY: ",""+error.getMessage());
                Toast.makeText(getContext(),"No se ha podido conectar"+error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(),"No se ha podido conectar"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idTipoCita= citaMedica.getTipoCita().getIdTipo();
                String idCupo= citaMedica.getCupo().getIdCupo();
                String idMedico= citaMedica.getMedico().getNumeroDocumento();
                String fechaCita= citaMedica.getFechaCita();
                String detalleCita= "Holi";
                String estado= getString(R.string.enespera);
                String idPaciente= citaMedica.getPaciente().getNumeroDocumento();
                String institucion=paciente.getInstitucion();
                String idAdministrador= "Default";
                String operacion= "insertar";
                Map<String,String> parametros=new HashMap<>();
                parametros.put("idCupo",idCupo);
                parametros.put("idTipoCita",idTipoCita);
                parametros.put("idMedico",idMedico);
                parametros.put("idPaciente", idPaciente);
                parametros.put("fechaCita", fechaCita);
                parametros.put("detallesCita",detalleCita);
                parametros.put("estadoCita", estado);
                parametros.put("institucion",institucion);
                parametros.put("idAdministrador",idAdministrador);
                parametros.put("operacion",operacion);
                return parametros;
            }

        };
        stringRequestCrearCita.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequestCrearCita);


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
