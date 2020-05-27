package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.CitasMedicasAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.MedicosAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.TiposCitasAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import co.edu.unab.hernandez.yeison.your_health.modelos.CitaMedica;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.TipoCita;
import co.edu.unab.hernandez.yeison.your_health.modelos.Usuario;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GestionCitas extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    private RecyclerView listaCitas;
    private ArrayList<CitaMedica> citaMedicas;
    private View view;
    private RecyclerView.LayoutManager manager;
    private ProgressDialog progress;
    private JsonObjectRequest jsonObjectRequestCitas;
    private Administrador admin;
    StringRequest stringRequest;

    Context context;
    private AlertDialog alert;

    public GestionCitas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gestion_citas, container, false);
        admin = (Administrador) getArguments().getSerializable(getString(R.string.idAdmin));
        context = getContext();
        asociarElementos();
        cargarCitas();

        return view;
    }

    public void asociarElementos() {
        listaCitas = view.findViewById(R.id.listaCitas);
        manager = new LinearLayoutManager(getContext());

    }

    public void cargarCitas() {
        progress = new ProgressDialog(getContext());
        progress.setMessage("Consultando citamedica...");
        progress.show();

        String url = getString(R.string.obtenerDatos, getString(R.string.nameServer), "citamedica", admin.getInstitucion(), context.getString(R.string.enespera));

        jsonObjectRequestCitas = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequestCitas);


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "No se puede cargar los datos de citas", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        CitaMedica citaMedica = null;
        citaMedicas = new ArrayList<>();
        Paciente paciente = null;
        Medico medico = null;
        TipoCita tipoCita = null;
        Cupo cupo = null;
        progress.dismiss();
        JSONArray json = response.optJSONArray("usuario");
        try {

            for (int i = 0; i < json.length(); i++) {
                citaMedica = new CitaMedica();
                paciente = new Paciente();
                medico = new Medico();
                tipoCita = new TipoCita();
                cupo = new Cupo();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                citaMedica.setEstadoCita(jsonObject.optString("estadoCita"));
                citaMedica.setFechaCita(jsonObject.optString("fechaCita"));
                citaMedica.setDetallesCita(jsonObject.optString("detallesCita"));
                citaMedica.setIdCita(jsonObject.optString("idCita"));
                citaMedica.setFechaCreacion(jsonObject.getString("fechaCreacion"));
                tipoCita.setUrlImagen(jsonObject.optString("urlImagenCita"));
                tipoCita.setDetalleTipoCita(jsonObject.optString("detalleTipoCita"));
                tipoCita.setNombreTipoCita(jsonObject.optString("nombreTipoCita"));
                tipoCita.setIdTipo(jsonObject.optString("idTipoCita"));
                citaMedica.setTipoCita(tipoCita);
                cupo.setIdCupo(jsonObject.optString("idCupo"));
                cupo.setFecha(jsonObject.optString("hora"));
                cupo.setLugar(jsonObject.optString("lugar"));
                cupo.setDisponible(jsonObject.optString("disponible"));
                citaMedica.setCupo(cupo);
                paciente.setNombreUsuario(jsonObject.optString("nombreUsuario"));
                paciente.setCorreoUsuario(jsonObject.optString("correoUsuario"));
                paciente.setFotoPerfil(jsonObject.optString("fotoPerfilUsuario"));
                paciente.setTelefono(jsonObject.optString("telefonoUsuario"));
                paciente.setSexo(jsonObject.optString("sexoUsuario"));
                paciente.setTipoDocumento(jsonObject.optString("tipoDocumento"));
                paciente.setFechaNacimientoUsuario(jsonObject.optString("fechaNacimientoUsuario"));
                paciente.setNumeroDocumento(jsonObject.optString("numeroDocumento"));
                citaMedica.setPaciente(paciente);

                medico.setNumeroDocumento(jsonObject.optString("numeroMedico"));
                medico.setSexo(jsonObject.optString("sexomedico"));
                medico.setCorreoUsuario(jsonObject.optString("correomedico"));
                medico.setFotoPerfil(jsonObject.optString("fotomedico"));
                medico.setNombreUsuario(jsonObject.optString("nombremedico"));
                medico.setTelefono(jsonObject.optString("telefonomedico"));
                medico.setDescripcion(jsonObject.optString("descripcionMedico"));
                citaMedica.setMedico(medico);

                citaMedicas.add(citaMedica);
            }
            CitasMedicasAdapter adapter = new CitasMedicasAdapter(citaMedicas, new CitasMedicasAdapter.onItemClickListener() {
                @Override
                public void onItemClick(CitaMedica citaMedica, int posicion) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    LayoutInflater inflater = getActivity().getLayoutInflater();

                    View v = inflater.inflate(R.layout.dialogovercita, null);

                    builder.setView(v);


                    Button aceptar = (Button) v.findViewById(R.id.aceptarCita);
                    Button rechazar = (Button) v.findViewById(R.id.rechazarCita);
                    ImageButton cerrar = (ImageButton) v.findViewById(R.id.cerrarvercita);
                    CircleImageView fotoPaciente = (CircleImageView) v.findViewById(R.id.fotopacientecita);
                    CircleImageView fotoMedico = (CircleImageView) v.findViewById(R.id.fotomedicocita);
                    CircleImageView fotoTipoCita = (CircleImageView) v.findViewById(R.id.fototipocitacita);
                    TextView fechacreacioncita = v.findViewById(R.id.fechacreaciontxt);
                    TextView fechayhoracita = v.findViewById(R.id.fechayhoracitatxt);
                    TextView detallecita = v.findViewById(R.id.detallevitatxt);
                    TextView estadocita = v.findViewById(R.id.estadocitatxt);
                    TextView nombrePaciente = v.findViewById(R.id.nombrepacientecita);
                    TextView emailPacienteCita = v.findViewById(R.id.emailpacientecita);
                    TextView telpacientecita = v.findViewById(R.id.telpacientecita);
                    TextView cedulapacientecita = v.findViewById(R.id.numeropacientecita);
                    TextView nombremedico = v.findViewById(R.id.nombremedicocita);
                    TextView emailmedico = v.findViewById(R.id.emailmedicocita);
                    TextView telmedicocita = v.findViewById(R.id.telmedicocita);
                    TextView cedulamedico = v.findViewById(R.id.numeroMedicocita);
                    TextView detalletipocita = v.findViewById(R.id.detaletipocita);
                    TextView nombretipocita = v.findViewById(R.id.nombretipocita);
                    TextView lugar = v.findViewById(R.id.lugarCupo);
                    TextView hora = v.findViewById(R.id.hora);

                    if (citaMedica.getMedico().getFotoPerfil() != null) {
                        //
                        cargarImagenWebService(citaMedica.getMedico().getFotoPerfil(), fotoMedico);
                    } else {
                        fotoMedico.setImageResource(R.drawable.camera);
                    }
                    if (citaMedica.getPaciente().getFotoPerfil() != null) {
                        //
                        cargarImagenWebService(citaMedica.getPaciente().getFotoPerfil(), fotoPaciente);
                    } else {
                        fotoPaciente.setImageResource(R.drawable.camera);
                    }
                    if (citaMedica.getTipoCita().getUrlImagen() != null) {
                        //
                        cargarImagenWebService(citaMedica.getTipoCita().getUrlImagen(), fotoTipoCita);
                    } else {
                        fotoTipoCita.setImageResource(R.drawable.camera);
                    }
                    telpacientecita.setText(citaMedica.getPaciente().getTelefono());
                    cedulapacientecita.setText(citaMedica.getPaciente().getNumeroDocumento());
                    nombremedico.setText(citaMedica.getMedico().getNombreUsuario());
                    emailmedico.setText(citaMedica.getMedico().getCorreoUsuario());
                    telmedicocita.setText(citaMedica.getMedico().getTelefono());
                    cedulamedico.setText(citaMedica.getMedico().getNumeroDocumento());
                    detalletipocita.setText(citaMedica.getTipoCita().getDetalleTipoCita());
                    nombretipocita.setText(citaMedica.getTipoCita().getNombreTipoCita());
                    lugar.setText(citaMedica.getCupo().getLugar());
                    hora.setText(citaMedica.getCupo().getFecha());
                    fechacreacioncita.setText(citaMedica.getFechaCreacion());
                    detallecita.setText(citaMedica.getDetallesCita());
                    estadocita.setText(citaMedica.getEstadoCita());
                    fechayhoracita.setText(citaMedica.getFechaCita() + " Hora:" + citaMedica.getCupo().getFecha());
                    nombrePaciente.setText(citaMedica.getPaciente().getNombreUsuario());
                    emailPacienteCita.setText(citaMedica.getPaciente().getCorreoUsuario());
                    aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            actualizarCita(citaMedica,context.getString(R.string.aprobado));
                            citaMedicas.remove(posicion);
                            alert.dismiss();
                        }
                    });
                    rechazar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            actualizarCita(citaMedica,context.getString(R.string.desaprobado));
                            alert.dismiss();
                        }
                    });
                    cerrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alert.dismiss();
                        }
                    });
                    alert = builder.create();
                    alert.show();

                }
            }, context);
            listaCitas.setLayoutManager(manager);
            listaCitas.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " " + response, Toast.LENGTH_LONG).show();

        }

    }

    private void cargarImagenWebService(String rutaImagen, CircleImageView circleImageView) {
        String urlImagen = context.getString(R.string.urlBase, context.getString(R.string.nameServer)) + rutaImagen;
        urlImagen = urlImagen.replace(" ", "%20");

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                circleImageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(imageRequest);
    }

    private void actualizarCita(CitaMedica citaMedica, String estado) {
        progress = new ProgressDialog(getContext());
        progress.setMessage("Guardando...");
        progress.show();


        String url = getString(R.string.actualizarCita, getString(R.string.nameServer), citaMedica.getIdCita(),estado,admin.getNumeroDocumento());

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();

                if (response.trim().equalsIgnoreCase("registra")){
                    Toast.makeText(context,context.getString(R.string.aprobado),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"No se ha registrado "+response,Toast.LENGTH_LONG).show();
                    Log.i("RESPUESTA: ",""+response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Log.i("ERRORVOLLEY: ",""+error.getMessage());
                Toast.makeText(context,"No se ha podido conectar"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);
    }
}