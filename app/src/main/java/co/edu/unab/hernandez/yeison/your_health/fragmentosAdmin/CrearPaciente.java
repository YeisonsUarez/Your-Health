package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * RC = Registro Civil TI = Tarjeta de identidad. CC = Cédula de ciudadanía CE = Cédula de extranjería. PA = Pasaporte MS = Menor sin identificación. AS = Adulto sin identidad.
 * A simple {@link Fragment} subclass.
 */
public class CrearPaciente extends Fragment {
    private EditText numeroDoc,nombreUser,emailUser,passUser, telefonoUser;
    private Spinner generoUser,tipoDocUser;
    private TextView edadUser;
    private View view;
    private ConstraintLayout pasoUnoPaciente,pasoDosPaciente;
    private Button crear,siguiente,atras;
    private ImageButton cancelar, tomarFoto;
    private Administrador admin;
    private CircleImageView fotoUser;
    private static final int PICK_IMAGE = 100;
    private static  final int TAKE_PHOTO=200;
    private Context context;
    private String name = "";
    Resources res;
    String[] tiposDocumentos ;
    String[] tipoSexo;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private static final String CERO = "0";
    private static final String BARRA = "/";

    Bitmap bitmap;
    ProgressDialog progreso;
    // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

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
        context= getContext();
        admin= (Administrador) getArguments().getSerializable(getString(R.string.idAdmin));
        res =getResources();
        tiposDocumentos= res.getStringArray(R.array.tiposDocumentos);
        tipoSexo= res.getStringArray(R.array.tipoSexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.tiposDocumentos, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterSexoPersona = ArrayAdapter.createFromResource(getContext(),
                R.array.tipoSexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSexoPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoDocUser.setAdapter(adapter);
        generoUser.setAdapter(adapterSexoPersona);
        operacionesBotones();
        name= Environment.getExternalStorageDirectory() + "/foto.jpg";

        return view;
    }
    public  void asociarElementos(){
        atras= view.findViewById(R.id.atrasUnoPaciente);
        pasoUnoPaciente= view.findViewById(R.id.pasoUnoPaciente);
        pasoDosPaciente= view.findViewById(R.id.pasoDosPaciente);
        siguiente=view.findViewById(R.id.siguientePasoUnoPaciente);
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
        edadUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerFecha();
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
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
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoDosPaciente.setVisibility(View.INVISIBLE);
                pasoUnoPaciente.setVisibility(View.VISIBLE);
            }
        });
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasoUnoPaciente.setVisibility(View.INVISIBLE);
                pasoDosPaciente.setVisibility(View.VISIBLE);
            }
        });
    }
    public  void  obtenerFoto(){

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

    public void guardarDatos(){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();


        String url= getString(R.string.urlRegistroUsuario,getString(R.string.nameServer));

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progreso.dismiss();

                if (response.trim().equalsIgnoreCase("registra")){
                    numeroDoc.setText("");
                    nombreUser.setText("");
                    emailUser.setText("");
                    passUser.setText("");
                    edadUser.setText("");
                    telefonoUser.setText("");
                    Toast.makeText(context,"Se ha registrado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"No se ha registrado "+response,Toast.LENGTH_LONG).show();
                    Log.i("RESPUESTA: ",""+response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.dismiss();
                Log.i("ERRORVOLLEY: ",""+error.getMessage());
                Toast.makeText(context,"No se ha podido conectar"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen=convertirImgString(bitmap);
                String numero =numeroDoc.getText().toString();
                String tipoDoc=tipoDocUser.getSelectedItem().toString();
                String nombre=nombreUser.getText().toString();
                String edad=edadUser.getText().toString();
                String genero=generoUser.getSelectedItem().toString();
                String email= emailUser.getText().toString();
                String contrasena=passUser.getText().toString();
                String telefono=telefonoUser.getText().toString();
                String intitucion= admin.getInstitucion();
                String tipoUSer= context.getString(R.string.textPaciente);
                Map<String,String> parametros=new HashMap<>();
                parametros.put("numeroDocumento",numero);
                parametros.put("tipoDocumento",tipoDoc);
                parametros.put("nombreUsuario",nombre);
                parametros.put("fechaNacimientoUsuario",edad);
                parametros.put("sexoUsuario",genero);
                parametros.put("correoUsuario",email);
                parametros.put("contrasenaUsuario",contrasena);
                parametros.put("telefonoUsuario", telefono);
                parametros.put("institucionUsuario",intitucion);
                parametros.put("tipoUsuario",tipoUSer);
                parametros.put("fotoPerfilUsuario",imagen);

                return parametros;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);

        getActivity().onBackPressed();

    }
    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }



    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                edadUser.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            fotoUser.setImageURI(imageUri);
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                fotoUser.setImageBitmap(bitmap);
            }
        }

    }


}
