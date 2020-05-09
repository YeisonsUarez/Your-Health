package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.CuposAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.TiposCitasAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.TipoCita;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
//Vaciar los elementos al crear...
public class CrearMedico extends Fragment {
    private ArrayList<Cupo> cupos;
    private ArrayList<TipoCita> areasTrabajoMedic;
    private ArrayList<Cupo> cuposMedico;
    private ArrayList<TipoCita> listaTiposCitasArray;
    private RecyclerView listaTiposCitas,listaCupos;
    private CircleImageView fotoCrearMedico;
    private Spinner sexoMedico, tipoDocumento;
    private EditText numeroDocumentoDoc,nombreDoc,edadDoc, email,telefono,detalle ,contrasena, repetircontrasena;
    private Button siguientePasoUno, siguientePasoDos,siguientePasoTres;
    private Button atrasPasoDos,atrasPasoTres,atrasPasoCuatro,crearMedico;
    private ImageButton cancelar,tomarFotoMedico;
    private ConstraintLayout pasoUno,pasoDos,pasoTres,pasoCuatro;
    private View view;
    private Medico medico;
    private Boolean estaConfirmado;
    private String name = "";
    Resources res;
    String[] tiposDocumentos ;
    String[] tipoSexo;

    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    private static  final int TAKE_PHOTO=200;
    private RecyclerView.LayoutManager managerTipoCIta;
    private RecyclerView.LayoutManager managerCupos;


    public CrearMedico() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_crear_medico, container, false);
        asociarElementos();
        medico= new Medico();
        cuposMedico= new ArrayList<>();
        areasTrabajoMedic= new ArrayList<>();
        res= getResources();
        name= Environment.getExternalStorageDirectory() + "/foto.jpg";
        tiposDocumentos= res.getStringArray(R.array.tiposDocumentos);
        tipoSexo= res.getStringArray(R.array.tipoSexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.tiposDocumentos, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterSexoPersona = ArrayAdapter.createFromResource(getContext(),
                R.array.tipoSexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSexoPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexoMedico.setAdapter(adapterSexoPersona);
        tipoDocumento.setAdapter(adapter);
        iniciarRecyclerHoras();
        iniciarRecyclerTipoCita();
        operacionesBotonesAtrasSiguiente();
        operacionBotonTomarFoto();

        crearMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoMedico();
                getActivity().onBackPressed();
            }
        });
        return view;
    }
    public  void asociarElementos(){
        cancelar= view.findViewById(R.id.cancelarCrearMedico);
        fotoCrearMedico= view.findViewById(R.id.fotoCrearMedico);
        siguientePasoUno= view.findViewById(R.id.siguienteCrearMedicoUno);
        numeroDocumentoDoc= view.findViewById(R.id.numeroDocCrearMedico);
        nombreDoc= view.findViewById(R.id.nombreDocCrearMedico);
        edadDoc=view.findViewById(R.id.edadCrearMedico);
        sexoMedico= view.findViewById(R.id.selectorSexoMedico);
        tipoDocumento= view.findViewById(R.id.tipoDocCrearMedico);
        pasoUno= view.findViewById(R.id.pasoUnoMedico);
        pasoDos=view.findViewById(R.id.pasoDosMedico);
        pasoTres= view.findViewById(R.id.pasoTresMedico);
        pasoCuatro= view.findViewById(R.id.pasoCuatroMedico);
        email= view.findViewById(R.id.emailCrearMedico);
        telefono= view.findViewById(R.id.telefonoCrearMedico);
        detalle= view.findViewById(R.id.detalleCrearMedico);
        contrasena= view.findViewById(R.id.contrasenaCrearMedicoAdm);
        repetircontrasena= view.findViewById(R.id.confirmarContrasenaDoc);
        siguientePasoDos= view.findViewById(R.id.siguienteDosCrearMedico);
        atrasPasoDos= view.findViewById(R.id.atrasPasoDos);
        atrasPasoTres= view.findViewById(R.id.atrasPasoTres);
        siguientePasoTres= view.findViewById(R.id.siguienteTresCrearMedico);
        listaTiposCitas= view.findViewById(R.id.listaAreasAtencion);
        listaCupos= view.findViewById(R.id.cuposCrearMedico);
        atrasPasoCuatro= view.findViewById(R.id.atrasPasoCuatro);
        crearMedico= view.findViewById(R.id.crearMedico);
        tomarFotoMedico= view.findViewById(R.id.tomarFotoMedico);
        managerTipoCIta = new LinearLayoutManager(getContext());
        listaTiposCitas.setLayoutManager(managerTipoCIta);
        managerCupos = new LinearLayoutManager(getContext());
        listaCupos.setLayoutManager(managerCupos);
    }

    public void operacionesBotonesAtrasSiguiente(){
        siguientePasoUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoUno.setVisibility(View.INVISIBLE);
                pasoDos.setVisibility(View.VISIBLE);
                medico.setNombreUsuario(nombreDoc.getText().toString());

            }
        });
        siguientePasoDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoDos.setVisibility(View.INVISIBLE);
                pasoTres.setVisibility(View.VISIBLE);
            }
        });
        siguientePasoTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoTres.setVisibility(View.INVISIBLE);
                pasoCuatro.setVisibility(View.VISIBLE);
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        atrasPasoDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoDos.setVisibility(View.INVISIBLE);
                pasoUno.setVisibility(View.VISIBLE);
            }
        });
        atrasPasoTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoTres.setVisibility(View.INVISIBLE);
                pasoDos.setVisibility(View.VISIBLE);
            }
        });
        atrasPasoCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoCuatro.setVisibility(View.INVISIBLE);
                pasoTres.setVisibility(View.VISIBLE);
            }
        });
    }
    public  void operacionBotonTomarFoto(){
        tomarFotoMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTomarFoto();
            }
        });
    }
    public void dialogTomarFoto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.mensajeDialogoFoto)
                .setPositiveButton(R.string.textTomarFoto, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivityForResult(takePictureIntent, TAKE_PHOTO);
                        }                    }
                })
                .setNegativeButton(R.string.textGaleria, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, PICK_IMAGE);

                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();

    }
    public void nuevoMedico(){
        Toast.makeText(getContext(), "Creado", Toast.LENGTH_SHORT).show();
    }
    private void iniciarRecyclerHoras() {
        agregarCupos();
        CuposAdapter adapter= new CuposAdapter(cupos, new CuposAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Cupo cupo, int posicion) {
                if(confirmarSeleccionCupo(cupo)){
                    cuposMedico.add(cupo);
                }
            }


        }, getActivity());
        listaCupos.setAdapter(adapter);
    }
    private void agregarCupos() {
        cupos= new ArrayList<>();
        for(int i =0; i<10;i++){
            Cupo cupo= new Cupo("1234"+i,"sala 3-2","2:00pm",getString(R.string.noDisponible));
            cupos.add(cupo);
        }

    }
    private void iniciarRecyclerTipoCita(){
        addTipoCitas();
        TiposCitasAdapter adapter= new TiposCitasAdapter(listaTiposCitasArray, new TiposCitasAdapter.onItemClickListener() {
            @Override
            public void onItemClick(TipoCita tiposCitasAdapter, int posicion) {
              if(confirmarSeleccionCita(tiposCitasAdapter)){
                    areasTrabajoMedic.add(tiposCitasAdapter);
              }
            }
        }, getActivity());
        listaTiposCitas.setAdapter(adapter);


    }


    private void  addTipoCitas(){
        listaTiposCitasArray= new ArrayList<>();
        listaTiposCitasArray.add(new TipoCita(1234,"Odontologia","Cita para revisión de dientes",getString(R.string.fotoOdontologia)));
        listaTiposCitasArray.add(new TipoCita(12345,"Fisioterapia","Cita para reparación y restauración de la movilidad física",getString(R.string.fotoFisioterapia)));

    }
    public boolean confirmarSeleccionCita(TipoCita cita){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.titleConfirmar);
        estaConfirmado=true;
        builder.setMessage(getString(R.string.confrmarSeleccionTipoCita ,cita.getNombreTipoCita(),medico.getNombreUsuario()))
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), getString(R.string.textAgregadoCita), Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(R.string.denegar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    estaConfirmado=false;
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
        return estaConfirmado;
    }
    public boolean confirmarSeleccionCupo(Cupo cupo){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.titleConfirmar);
        estaConfirmado=true;
        builder.setMessage(getString(R.string.confirmarSeleccionCupo ,cupo.getFecha(),cupo.getLugar(),medico.getNombreUsuario()))
                .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), getString(R.string.textAgregadoCupo), Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(R.string.denegar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        estaConfirmado=false;
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
        return estaConfirmado;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            fotoCrearMedico.setImageURI(imageUri);
        }else {
            if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                fotoCrearMedico.setImageBitmap(imageBitmap);
            }
        }

    }
}
