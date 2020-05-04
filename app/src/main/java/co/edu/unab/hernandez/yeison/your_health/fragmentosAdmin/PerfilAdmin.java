package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilAdmin extends Fragment {

    Administrador admin;
    View view;
    EditText nombreAdmin,correoAdmin,telefonoAdmin;
    TextView tipoYNumeroDocumento;
    Button cerrarSesion, actualizarDatos, cambiarContrasena;
    ImageButton cambiarfotoAdmin;
    CircleImageView fotoAdmin;

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
        return view;
    }


    public  void asociarElementos(){
        nombreAdmin= view.findViewById(R.id.textNombreAdmin);
        correoAdmin= view.findViewById(R.id.correoTextAdmin);
        telefonoAdmin= view.findViewById(R.id.telefonoAdmin);
        tipoYNumeroDocumento= view.findViewById(R.id.docyNumeroDocAdmin);
        cerrarSesion= view.findViewById(R.id.cerrarsesionAdmin);
        actualizarDatos= view.findViewById(R.id.actulizarAdmin);
        cambiarContrasena= view.findViewById(R.id.actulizarContrasena);
        cambiarfotoAdmin= view.findViewById(R.id.cambiarFotoAdmin);
        fotoAdmin=view.findViewById(R.id.fotoAdminPerfil);
    }

    public void anadirDatos(){
        nombreAdmin.setText(admin.getNombreUsuario());
        correoAdmin.setText(admin.getCorreoUsuario());
        telefonoAdmin.setText(admin.getTelefono());
        tipoYNumeroDocumento.setText(admin.getTipoDocumento()+" :"+admin.getNumeroDocumento());
        Glide.with(getActivity()).load(admin.getFotoPerfil()).dontAnimate().placeholder(new ColorDrawable(Color.WHITE)).into(fotoAdmin);

    }
}
