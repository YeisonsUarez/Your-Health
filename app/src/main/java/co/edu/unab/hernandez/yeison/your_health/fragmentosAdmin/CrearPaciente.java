package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;

import co.edu.unab.hernandez.yeison.your_health.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrearPaciente extends Fragment {
    private EditText numeroDoc,nombreUser,emailUser,passUser,edadUser, telefonoUser;
    private Spinner generoUser,tipoDocUser;
    private View view;
    private Button crear;
    private ImageButton cancelar, tomarFoto;
    private CircleImageView fotoUser;
    private static final int PICK_IMAGE = 100;
    private static  final int TAKE_PHOTO=200;
    private String name = "";


    Uri imageUri;
    public CrearPaciente() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_crear_paciente, container, false);
        asociarElementos();
        operacionesBotones();
        name= Environment.getExternalStorageDirectory() + "/foto.jpg";

        return view;
    }
    public  void asociarElementos(){
        numeroDoc= view.findViewById(R.id.numeroDocCrearPaciente);
        nombreUser= view.findViewById(R.id.nombreDocCrearPaciente);
        emailUser= view.findViewById(R.id.emailCrearPaciente);
        passUser=view.findViewById(R.id.contrasenaCrearPacienteAdm);
        edadUser=view.findViewById(R.id.edadCrearPaciente);
        telefonoUser= view.findViewById(R.id.telefonoCrearPaciente);
        generoUser= view.findViewById(R.id.selectorSexoPaciente);
        tipoDocUser=view.findViewById(R.id.tipoDocCrearPaciente);
        cancelar=view.findViewById(R.id.cancelarCrearPaciente);
        tomarFoto=view.findViewById(R.id.btnCamera);
        fotoUser=view.findViewById(R.id.fotoCrearPaciente);
        crear=view.findViewById(R.id.crearPacienteAdm);
    }
    public  void  operacionesBotones(){
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Nice", Toast.LENGTH_SHORT).show();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerFoto();
            }
        });
    }
    public  void  obtenerFoto(){
        /*Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);*/
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, TAKE_PHOTO);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            fotoUser.setImageURI(imageUri);
        }else {
            if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                fotoUser.setImageBitmap(imageBitmap);
            }
        }

    }
}
