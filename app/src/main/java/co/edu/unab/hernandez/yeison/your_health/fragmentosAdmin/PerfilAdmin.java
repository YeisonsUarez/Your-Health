package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;

import co.edu.unab.hernandez.yeison.your_health.MainActivity;
import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilAdmin extends Fragment {

    private Administrador admin;
    View view;
    EditText nombreAdmin,correoAdmin,telefonoAdmin;
    TextView tipoYNumeroDocumento;
    Button cerrarSesion, actualizarDatos;
    ImageButton cambiarfotoAdmin,cerrar;
    CircleImageView fotoAdmin;
    private SharedPreferences LocalStorage;


    public PerfilAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_perfil_admin, container, false);
        admin= (Administrador) getArguments().getSerializable(getString(R.string.idAdmin));
        asociarElementos();
        anadirDatos();
        operacionesBotones();
        return view;
    }


    public  void asociarElementos(){
        nombreAdmin= view.findViewById(R.id.textNombreAdmin);
        correoAdmin= view.findViewById(R.id.correoTextAdmin);
        telefonoAdmin= view.findViewById(R.id.telefonoAdmin);
        tipoYNumeroDocumento= view.findViewById(R.id.docyNumeroDocAdmin);
        cerrarSesion= view.findViewById(R.id.cerrarsesionAdmin);
        actualizarDatos= view.findViewById(R.id.actulizarAdmin);
        cerrar= view.findViewById(R.id.cerrarPerfilAdmin);
        cambiarfotoAdmin= view.findViewById(R.id.cambiarFotoAdmin);
        fotoAdmin=view.findViewById(R.id.fotoAdminPerfil);
    }

    public void anadirDatos(){
        nombreAdmin.setText(admin.getNombreUsuario());
        correoAdmin.setText(admin.getCorreoUsuario());
        telefonoAdmin.setText(admin.getTelefono());
        tipoYNumeroDocumento.setText(admin.getTipoDocumento()+" :"+admin.getNumeroDocumento());
        cargarImagenWebService(admin.getFotoPerfil());

    }
    private void cargarImagenWebService(String rutaImagen) {
        String urlImagen=getString(R.string.urlBase,getString(R.string.nameServer))+rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                fotoAdmin.setImageBitmap(response);
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
    public void operacionesBotones(){
        actualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
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
}
