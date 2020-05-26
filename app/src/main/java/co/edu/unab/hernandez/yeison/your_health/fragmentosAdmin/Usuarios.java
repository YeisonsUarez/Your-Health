package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.MedicosAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.UsuariosAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.Usuario;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class Usuarios extends Fragment {
    private RecyclerView listadeUsuarios;
    private FloatingActionMenu anadirUsuarios;
    private FloatingActionButton btnPaciente,btnMedico;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Usuario> usuarios;
    private View view;
    private ArrayList<Medico> medicos;
    private ArrayList<Paciente> pacientes;
    private Administrador admin;
    private Switch tipoUsuarioSwitch;
    private FragmentTransaction transaction;
    ProgressDialog progress;
    Context context;
    JsonObjectRequest jsonObjectRequestMedicos, jsonObjectRequestPacientes;
    Fragment crearPaciente, crearMedico, editarUsuario;
    public Usuarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_usuarios, container, false);
        admin= (Administrador) getArguments().getSerializable(getString(R.string.idAdmin));
        context= getActivity();
        Toast.makeText(context, ""+admin.getInstitucion(), Toast.LENGTH_SHORT).show();

       asociarElementos();
       operacionesDeBotones();

        return view;
    }


    private void operacionesDeBotones() {
        btnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarFragmentCrearPaciente();
            }
        });
        btnMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarFragmentCrearMedico();;
            }
        });
        tipoUsuarioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    Toast.makeText(context, "Medicos registrados", Toast.LENGTH_SHORT).show();
                    iniciarMedicos();


                }else{
                    Toast.makeText(context, "Pacientes registrados", Toast.LENGTH_SHORT).show();
                    iniciarPacientes();
                }
            }
        });
    }

    private void mostrarFragmentCrearPaciente(){

        transaction= getActivity().getSupportFragmentManager().beginTransaction();
        if(crearPaciente!=null){
            transaction.replace(R.id.frameLayoutUsuarios,crearPaciente).addToBackStack(null).commit();
        }else{
            crearPaciente= new CrearPaciente();
            Bundle bundle= new Bundle();
            bundle.putSerializable(getString(R.string.idAdmin),admin);
            crearPaciente.setArguments(bundle);
            transaction.add(R.id.frameLayoutUsuarios,crearPaciente);
            transaction.addToBackStack(null);
            transaction.commit();
            }
        /*
        NavOptions.Builder navOptionsBuilder = new NavOptions.Builder();
        navOptionsBuilder.setEnterAnim(R.anim.slide_in_right);
        navOptionsBuilder.setExitAnim(R.anim.slide_out_left);
        navOptionsBuilder.setPopEnterAnim(R.anim.slide_in_left);
        navOptionsBuilder.setPopExitAnim(R.anim.slide_out_right);
        final NavOptions options = navOptionsBuilder.build();
        NavHostFragment.findNavController(this).navigate(R.id.crearPacienteAdm,null,options);
*/

    }
    private   void mostrarFragmentCrearMedico(){
        transaction= getActivity().getSupportFragmentManager().beginTransaction();
        if(crearMedico!=null){
            transaction.replace(R.id.frameLayoutUsuarios,crearMedico).addToBackStack(null).commit();
        }else{
            crearMedico= new CrearMedico();
            Bundle bundle= new Bundle();
            bundle.putSerializable(getString(R.string.idAdmin),admin);
            crearMedico.setArguments(bundle);
            transaction.add(R.id.frameLayoutUsuarios,crearMedico);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
    private void iniciarMedicos(){
            medicos= new ArrayList<>();
            progress=new ProgressDialog(getContext());
            progress.setMessage("Consultando Medico...");
            progress.show();
            String url=getString(R.string.obtenerDatos,getString(R.string.nameServer),"usuario",admin.getInstitucion(),context.getString(R.string.textMedico));

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
                            medico.setFechaRegistro(jsonObject.optString("fechaRegistro"));
                            medico.setTipoDocumento(jsonObject.optString("tipoDocumento"));
                            medico.setContrasenauUsuario(jsonObject.optString("contrasenaUsuario"));
                            medico.setFechaNacimientoUsuario(jsonObject.optString("fechaNacimientoUsuario"));
                            medicos.add(medico);
                        }
                        MedicosAdapter adapter= new MedicosAdapter(medicos, new MedicosAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(Medico medico, int posicion) {
                                Toast.makeText(context, "Nombre:"+medico.getNombreUsuario(), Toast.LENGTH_SHORT).show();

                            }
                        }, getActivity());
                        listadeUsuarios.setLayoutManager(manager);
                        listadeUsuarios.setAdapter(adapter);



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
                    Toast.makeText(getContext(), "No se puede conectar con Medico"+error.toString()+error.getMessage(), Toast.LENGTH_LONG).show();
                    System.out.println();
                    Log.d("ERROR: ", error.toString()+error.getMessage());


                }
            });
            // request.add(jsonObjectRequest);
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequestMedicos);
    }

    private void iniciarPacientes(){
        usuarios= new ArrayList<>();
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando Paciente...");
        progress.show();
        String url=getString(R.string.obtenerDatos,getString(R.string.nameServer),"usuario",admin.getInstitucion(),context.getString(R.string.textPaciente));

        jsonObjectRequestPacientes=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Usuario usuario= null;
                progress.dismiss();
                JSONArray json=response.optJSONArray("usuario");
                try {

                    for (int i=0;i<json.length();i++){
                        usuario=new Usuario();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);

                        usuario.setNumeroDocumento(jsonObject.optString("numeroDocumento"));
                        usuario.setNombreUsuario(jsonObject.optString("nombreUsuario"));
                        usuario.setCorreoUsuario(jsonObject.optString("correoUsuario"));
                        usuario.setFotoPerfil(jsonObject.optString("fotoPerfilUsuario"));
                        usuario.setTelefono(jsonObject.optString("telefonoUsuario"));
                        usuario.setSexo(jsonObject.optString("sexoUsuario"));
                        usuario.setFechaRegistro(jsonObject.optString("fechaRegistro"));
                        usuario.setTipoDocumento(jsonObject.optString("tipoDocumento"));
                        usuario.setContrasenauUsuario(jsonObject.optString("contrasenaUsuario"));
                        usuario.setFechaNacimientoUsuario(jsonObject.optString("fechaNacimientoUsuario"));
                        usuarios.add(usuario);
                    }
                    UsuariosAdapter adapter= new UsuariosAdapter(usuarios, new UsuariosAdapter.onItemClickListener() {
                        @Override
                        public void onItemClick(Usuario usuario, int posicion) {
                            Toast.makeText(context, "NombrePaciente"+usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
                        }
                    },context);
                    listadeUsuarios.setLayoutManager(manager);
                    listadeUsuarios.setAdapter(adapter);



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
                Toast.makeText(getContext(), "No se puede conectar con pacientes"+error.toString()+error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString()+error.getMessage());


            }
        });
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequestPacientes);
    }
    private void asociarElementos(){
        listadeUsuarios= view.findViewById(R.id.listaUsuarios);
        anadirUsuarios= view.findViewById(R.id.menuButton);
        anadirUsuarios.setClosedOnTouchOutside(true);
        btnMedico= view.findViewById(R.id.btnMedico);
        btnPaciente= view.findViewById(R.id.btnPaciente);
        tipoUsuarioSwitch= view.findViewById(R.id.seleccionUsuario);
        manager = new LinearLayoutManager(getContext());

        listadeUsuarios.setLayoutManager(manager);
    }

}
