package co.edu.unab.hernandez.yeison.your_health.fragmentosmedico;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.CitasMedicasAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.CitaMedica;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medicamento;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.TipoCita;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerCitas extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    private Medico medico;
    private View view;
    private ArrayList<CitaMedica> citaMedicas;

    private RecyclerView listaCitas;
    private RecyclerView.LayoutManager manager;
    private AlertDialog alert;
    private JsonObjectRequest jsonObjectRequestCitas;
    private ProgressDialog progress;
    private Context context;
    private StringRequest stringRequest;


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
        context= getContext();
        asociarElementos();
        iniciarListaCitas();
        return view;
    }

    private void iniciarListaCitas() {
        progress = new ProgressDialog(getContext());
        progress.setMessage("Consultando citamedica...");
        progress.show();

        String url = getString(R.string.obtenerCitasyMedicamentos, getString(R.string.nameServer), "citamedica",context.getString(R.string.textMedico) , medico.getNumeroDocumento(),medico.getInstitucion());

        jsonObjectRequestCitas = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequestCitas);
    }

    public void asociarElementos() {
        listaCitas = view.findViewById(R.id.listCitasDoctor);
        manager = new LinearLayoutManager(getContext());

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
        cargarImagenWebService(citaMedica.getPaciente().getFotoPerfil(),foto);
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
        edad.setText(getString(R.string.textEdad)+citaMedica.getPaciente().getFechaNacimientoUsuario());
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
                alert.dismiss();
                mostrarOpcionesMedicamentos(citaMedica);


            }
        });
        builder.setView(dialoglayout);
        alert = builder.create();
        alert.show();
        //builder.show();
        return citaMedica;
    }

    private void mostrarOpcionesMedicamentos(CitaMedica citaMedica){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.preguntaMedicamentos)
                .setPositiveButton(R.string.aceptarCita, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mostrarDialogMedicamento(citaMedica);
                    }
                })
                .setNegativeButton(R.string.denegar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        actualizarCita(citaMedica,getString(R.string.citaFinalizada));
                        Toast.makeText(context, context.getString(R.string.citaTerminada), Toast.LENGTH_LONG).show();
                        citaMedica.setEstadoCita(getString(R.string.citaFinalizada));                    }
                });
        // Create the AlertDialog object and return it
         builder.create();
         builder.show();

    }
    public void mostrarDialogMedicamento(CitaMedica citaMedica){
        LayoutInflater inflater = getLayoutInflater();
        ArrayList<Medicamento> listaMedicamentos= new ArrayList<>();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View dialoglayout = inflater.inflate(R.layout.dialoganadirmedicamento, null);
        final EditText nombre, detalle, cantidad;
        Button anadirMedicamento,guardar;
        ImageButton salir;
        RecyclerView recyclerView= dialoglayout.findViewById(R.id.medicamentosagregados);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        nombre= dialoglayout.findViewById(R.id.nombremedicamento);
        detalle=dialoglayout.findViewById(R.id.detallemedicamento);
        cantidad= dialoglayout.findViewById(R.id.cantidadmedicamento);
        anadirMedicamento= dialoglayout.findViewById(R.id.anadirmedicamentos);
        guardar= dialoglayout.findViewById(R.id.guardar);
        salir=dialoglayout.findViewById(R.id.cerrarmedicamentos);
        anadirMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Medicamento medicamento = new Medicamento(nombre.getText().toString(),detalle.getText().toString(),cantidad.getText().toString(),citaMedica.getMedico(),citaMedica.getPaciente());
                listaMedicamentos.add(medicamento);

            }
        });


        builder.setView(dialoglayout);
        alert = builder.create();
        alert.show();


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(context, "Error al obtener citas", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        CitaMedica citaMedica = null;
        citaMedicas = new ArrayList<>();
        Paciente paciente = null;
        TipoCita tipoCita = null;
        Cupo cupo = null;
        progress.dismiss();
        JSONArray json = response.optJSONArray("usuario");
        try {

            for (int i = 0; i < json.length(); i++) {
                citaMedica = new CitaMedica();
                paciente = new Paciente();
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
                citaMedica.setMedico(medico);

                citaMedicas.add(citaMedica);
            }
            CitasMedicasAdapter adapter = new CitasMedicasAdapter(citaMedicas, new CitasMedicasAdapter.onItemClickListener() {
                @Override
                public void onItemClick(CitaMedica citaMedica, int posicion) {
                    DetalleCita(citaMedica);
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
    private void cargarImagenWebService(String rutaImagen, ImageView circleImageView) {
        String urlImagen = context.getString(R.string.urlBase, context.getString(R.string.nameServer)) + rutaImagen;
        urlImagen = urlImagen.replace(" ", "%20");

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Glide.with(getContext()).load(response).apply(RequestOptions.circleCropTransform()).into(circleImageView);

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


        String url = getString(R.string.actualizarCita, getString(R.string.nameServer), citaMedica.getIdCita(),estado,medico.getNumeroDocumento());

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