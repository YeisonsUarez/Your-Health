package co.edu.unab.hernandez.yeison.your_health.fragmentosPaciente;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;

import co.edu.unab.hernandez.yeison.your_health.MainActivity;
import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.MedicosAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilPaciente extends Fragment {
    private Button cerrarsesion;
    private SharedPreferences LocalStorage;
    private View view;
    private TextView nombre, tipoDocYNum,correo,telefono;
    private CircleImageView circleImageView;
    private Paciente paciente;

    public PerfilPaciente() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_perfil_paciente, container, false);
        paciente= (Paciente) getArguments().getSerializable(getString(R.string.textPaciente));
        asociarElementos();
        llenarElementos();
        operacionesBotones();

    return view;
    }

    private void llenarElementos() {
        nombre.setText(paciente.getNombreUsuario());
        tipoDocYNum.setText(paciente.getTipoDocumento()+paciente.getNumeroDocumento());
        correo.setText(paciente.getCorreoUsuario());
        telefono.setText(paciente.getTelefono());
        cargarImagenWebService(paciente.getFotoPerfil());


    }
    private void cargarImagenWebService(String rutaImagen) {
        String urlImagen=getString(R.string.urlBase,getString(R.string.nameServer))+rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                circleImageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error al cargar la imagen",Toast.LENGTH_SHORT).show();
            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(getActivity()).addToRequestQueue(imageRequest);
    }


    private void operacionesBotones(){
        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalStorage= getActivity().getSharedPreferences(getString(R.string.nameStorage),MODE_PRIVATE);
                SharedPreferences.Editor edit= LocalStorage.edit();
                edit.clear();
                edit.apply();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private  void asociarElementos(){
        cerrarsesion= view.findViewById(R.id.cerrarsesionPaciente);
        nombre= view.findViewById(R.id.textNombrePaciente);
        tipoDocYNum= view.findViewById(R.id.docyNumeroDoc);
        correo= view.findViewById(R.id.correoText);
        telefono= view.findViewById(R.id.telefonoPacienteP);
        circleImageView=view.findViewById(R.id.fotoPacientePerfil);
    }
}
