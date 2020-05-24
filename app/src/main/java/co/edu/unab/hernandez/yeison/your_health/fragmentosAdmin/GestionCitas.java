package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.CitasMedicasAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.MedicosAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import co.edu.unab.hernandez.yeison.your_health.modelos.CitaMedica;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.TipoCita;
import co.edu.unab.hernandez.yeison.your_health.modelos.Usuario;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class GestionCitas extends Fragment {
    private RecyclerView listaCitas;
    private ArrayList<CitaMedica> citaMedicas;
    private View view;
    private RecyclerView.LayoutManager manager;
    private ProgressDialog progress;
    private JsonObjectRequest jsonObjectRequestCitas;
    private Administrador admin;

    Context context;


    public GestionCitas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_gestion_citas, container, false);
        admin= (Administrador) getArguments().getSerializable(getString(R.string.idAdmin));
        context= getContext();
        asociarElementos();
        cargarCitas();

        return view;
    }

    public void  asociarElementos(){
        listaCitas= view.findViewById(R.id.listaCitas);
        manager = new LinearLayoutManager(getContext());

    }
    public void  cargarCitas()
    {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando citamedica...");
        progress.show();

        String url=getString(R.string.obtenerDatos,getString(R.string.nameServer),"citamedica",admin.getInstitucion(),context.getString(R.string.enespera));

        jsonObjectRequestCitas=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                CitaMedica citaMedica= null;
                citaMedicas= new ArrayList<>();
                Paciente paciente= null;
                Medico medico= null;
                TipoCita tipoCita=null;
                Cupo cupo= null;
                progress.dismiss();
                JSONArray json=response.optJSONArray("usuario");
                try {

                    for (int i=0;i<json.length();i++){
                        citaMedica=new CitaMedica();
                        paciente= new Paciente();
                        medico=new Medico();
                        tipoCita= new TipoCita();
                        cupo=new Cupo();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);

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
                    CitasMedicasAdapter adapter= new CitasMedicasAdapter(citaMedicas, new CitasMedicasAdapter.onItemClickListener() {
                        @Override
                        public void onItemClick(CitaMedica citaMedica, int posicion) {
                            Toast.makeText(context, ""+citaMedica.getMedico().getNombreUsuario(), Toast.LENGTH_SHORT).show();
                        }
                    },context);
                    listaCitas.setLayoutManager(manager);
                    listaCitas.setAdapter(adapter);



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
                Toast.makeText(getContext(), "No se puede conectar con citas medicas"+error.toString()+error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString()+error.getMessage());


            }
        });
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequestCitas);


    }

}
