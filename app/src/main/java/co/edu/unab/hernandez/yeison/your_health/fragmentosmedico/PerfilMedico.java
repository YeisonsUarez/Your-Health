package co.edu.unab.hernandez.yeison.your_health.fragmentosmedico;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import co.edu.unab.hernandez.yeison.your_health.Login;
import co.edu.unab.hernandez.yeison.your_health.MainActivity;
import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilMedico extends Fragment {
    private Button cerrarsesion;
    private SharedPreferences LocalStorage;
    private TextView nombreDOc, tipoDocYNumDOC,correoDOC,telefonoDOC;
    private CircleImageView circleImageViewDOC;
    private Medico medico;

    View view;
    public PerfilMedico() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_perfil_medico, container, false);
        medico= (Medico) getArguments().getSerializable(getString(R.string.textMedico));
        asociarElementos();
        llenarElementos();
        operacionesBotones();
        return view;
    }

    private void llenarElementos() {
        nombreDOc.setText(medico.getNombreUsuario());
        tipoDocYNumDOC.setText(medico.getTipoDocumento()+medico.getNumeroDocumento());
        correoDOC.setText(medico.getCorreoUsuario());
        telefonoDOC.setText(medico.getTelefono());
        Glide.with(getActivity()).load(medico.getFotoPerfil()).dontAnimate().placeholder(new ColorDrawable(Color.WHITE)).into(circleImageViewDOC);

        /*if(medico.getFotoPerfil().isEmpty()){
            medico.setFotoPerfil("https://sites.google.com/site/moratbandasimonvargas/_/rsrc/1528023184336/home/simpn.jpg");
            Toast.makeText(getActivity(), "Tenemos problemas en profile llega vacio", Toast.LENGTH_SHORT).show();
        }*/

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
        cerrarsesion= view.findViewById(R.id.cerrarsesionMedico);
        nombreDOc= view.findViewById(R.id.textNombreMedico);
        tipoDocYNumDOC= view.findViewById(R.id.docyNumeroDocMedico);
        correoDOC= view.findViewById(R.id.correoTextDoc);
        telefonoDOC= view.findViewById(R.id.telefonoDoc);
        circleImageViewDOC=view.findViewById(R.id.fotoDoctorPerfil);
    }
}
