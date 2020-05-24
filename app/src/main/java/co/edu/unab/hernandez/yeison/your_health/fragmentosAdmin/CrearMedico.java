package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.CuposAdapter;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.TiposCitasAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import co.edu.unab.hernandez.yeison.your_health.modelos.AreaDeTrabajo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.TipoCita;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
//Vaciar los elementos al crear...
public class CrearMedico extends Fragment {
    private ArrayList<Cupo> cupos;
    private ArrayList<AreaDeTrabajo> areaDeTrabajos;
    private ArrayList<Cupo> cuposSeleccionados;
    private ArrayList<TipoCita> listaTiposCitasArray;
    private RecyclerView listaTiposCitas,listaCupos;
    private CircleImageView fotoCrearMedico;
    private Spinner sexoMedico, tipoDocumento;
    private EditText numeroDocumentoDoc,nombreDoc,edadDoc, email,telefono,detalle ,contrasena, repetircontrasena;
    private Button siguientePasoUno, siguientePasoDos;
    private Button atrasPasoDos,atrasPasoTres,atrasPasoCuatro,crearMedico;
    private ImageButton cancelar,tomarFotoMedico;
    private ConstraintLayout pasoUno,pasoDos,pasoTres,pasoCuatro;
    private View view;
    private Administrador administrador;
    private Medico medico;
    private Boolean estaConfirmado;
    private String name = "";
    AreaDeTrabajo nuevaArea;
    Bitmap bitmap;
    Context context;
    Resources res;
    String[] tiposDocumentos ;
    String[] tipoSexo;
    String aniosExp;
    ProgressDialog progress;
    ProgressDialog progreso;
    // RequestQueue request;


    StringRequest stringRequest;

    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    private static  final int TAKE_PHOTO=200;
    private RecyclerView.LayoutManager managerTipoCIta;
    private RecyclerView.LayoutManager managerCupos;
    JsonObjectRequest jsonObjectRequest;
    JsonObjectRequest jsonObjectRequestTipoCita;

    //calendario

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private static final String CERO = "0";
    private static final String BARRA = "/";


    public CrearMedico() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_crear_medico, container, false);
        asociarElementos();
        context= getContext();
        medico= new Medico();
        administrador= (Administrador) getArguments().getSerializable(getString(R.string.idAdmin));
        areaDeTrabajos= new ArrayList<>();
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
        agregarCupos();
        addTipoCitas();
        operacionesBotonesAtrasSiguiente();
        operacionBotonTomarFoto();
        edadDoc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                obtenerFecha();

                return false;
            }
        });

        crearMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaArea.setAniosExperiencia(aniosExp);
                nuevaArea.setCuposAreaTrabajo(cuposSeleccionados);
                areaDeTrabajos.add(nuevaArea);
                nuevoMedico();

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
        edadDoc.setFocusable(false);
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
        listaTiposCitas= view.findViewById(R.id.listaAreasAtencion);
        listaCupos= view.findViewById(R.id.cuposCrearMedico);
        atrasPasoCuatro= view.findViewById(R.id.atrasPasoCuatro);
        crearMedico= view.findViewById(R.id.crearMedico);
        tomarFotoMedico= view.findViewById(R.id.tomarFotoMedico);
        managerTipoCIta = new LinearLayoutManager(getContext());
        listaTiposCitas.setLayoutManager(managerTipoCIta);
        managerCupos = new LinearLayoutManager(getContext());
        listaCupos.setLayoutManager(managerCupos);
        repetircontrasena.setEnabled(true);
        repetircontrasena.setFocusable(true);

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
                nuevaArea.setAniosExperiencia(aniosExp);
                nuevaArea.setCuposAreaTrabajo(cuposSeleccionados);
                areaDeTrabajos.add(nuevaArea);
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
    public void nuevoMedico()
    {
        progreso=new ProgressDialog(getActivity());
        progreso.setMessage("Cargando...");
        progreso.show();


        String url= getString(R.string.urlRegistroUsuarioM,getString(R.string.nameServer));

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progreso.dismiss();
                if (response.trim().contains("registra")){
                    Toast.makeText(context, "Se han guardado los datos.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"No se ha registrado "+response,Toast.LENGTH_LONG).show();
                    Log.i("RESPUESTA: ",""+response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.dismiss();
                Toast.makeText(context, "No se ha podido guardar"+error.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("ERRORVOLLEY: ",""+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen=convertirImgString(bitmap);
                String numero =numeroDocumentoDoc.getText().toString();
                String tipoDoc=tipoDocumento.getSelectedItem().toString();
                String nombre=nombreDoc.getText().toString();
                String edad=edadDoc.getText().toString();
                String genero=sexoMedico.getSelectedItem().toString();
                String emailDoc=email.getText().toString();
                String contrasenaDoc=contrasena.getText().toString();
                String telefonoDoc=telefono.getText().toString();
                String intitucion= administrador.getInstitucion();
                String tipoUSer= context.getString(R.string.textMedico);
                String descripcion= detalle.getText().toString();
                String json = new Gson().toJson(areaDeTrabajos);
                Map<String,String> parametros=new HashMap<>();
                parametros.put("numeroDocumento",numero);
                parametros.put("tipoDocumento",tipoDoc);
                parametros.put("nombreUsuario",nombre);
                parametros.put("fechaNacimientoUsuario",edad);
                parametros.put("sexoUsuario",genero);
                parametros.put("correoUsuario",emailDoc);
                parametros.put("contrasenaUsuario",contrasenaDoc);
                parametros.put("telefonoUsuario", telefonoDoc);
                parametros.put("institucionUsuario",intitucion);
                parametros.put("tipoUsuario",tipoUSer);
                parametros.put("fotoPerfilUsuario",imagen);
                parametros.put("areasDeTrabajo",json);
                parametros.put("descripcion",descripcion);
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

    private void agregarCupos() {
        cupos= new ArrayList<>();

        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultandocupo...");
        progress.show();


        String url=getString(R.string.obtenerDatos,getString(R.string.nameServer),"cupo",administrador.getInstitucion(),"");

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Cupo cupo= null;
                progress.dismiss();
                JSONArray json=response.optJSONArray("usuario");
                try {

                    for (int i=0;i<json.length();i++){
                        cupo=new Cupo();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);

                        cupo.setIdCupo(jsonObject.optString("idCupo"));
                        cupo.setFecha(jsonObject.optString("hora"));
                        cupo.setLugar(jsonObject.optString("lugar"));
                        cupo.setDisponible(jsonObject.optString("disponible"));
                        cupos.add(cupo);
                    }

                    CuposAdapter adapter= new CuposAdapter(cupos, new CuposAdapter.onItemClickListener() {
                        @Override
                        public void onItemClick(final Cupo cupo, int posicion) {
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.titleConfirmar);
                            estaConfirmado=true;
                            builder.setMessage(getString(R.string.confirmarSeleccionCupo ,cupo.getFecha(),cupo.getLugar(),medico.getNombreUsuario()))
                                    .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            cuposSeleccionados.add(cupo);
                                            Toast.makeText(getContext(), getString(R.string.textAgregadoCupo)+cuposSeleccionados.size(), Toast.LENGTH_SHORT).show();

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

                        }


                    }, getActivity());
                    listaCupos.setAdapter(adapter);


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
                Toast.makeText(getContext(), "No se puede conectar "+error.toString()+error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString()+error.getMessage());


            }
        });
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }
    private void  addTipoCitas(){
        listaTiposCitasArray= new ArrayList<>();
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...cita");
        progress.show();


        String url=getString(R.string.obtenerDatos,getString(R.string.nameServer),"tipocita",administrador.getInstitucion(),"");


        jsonObjectRequestTipoCita=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                TipoCita tipoCita= null;
                progress.dismiss();
                JSONArray json=response.optJSONArray("usuario");

                try {

                    for (int i=0;i<json.length();i++){
                        tipoCita= new TipoCita();
                        JSONObject jsonObject=null;
                        jsonObject=json.getJSONObject(i);

                        tipoCita.setIdTipo(jsonObject.optString("idTipoCita"));
                        tipoCita.setNombreTipoCita(jsonObject.optString("nombreTipoCita"));
                        tipoCita.setDetalleTipoCita(jsonObject.optString("detalleTipoCita"));
                        tipoCita.setUrlImagen(jsonObject.optString("urlImagenCita"));
                        listaTiposCitasArray.add(tipoCita);
                    }

                    TiposCitasAdapter adapter= new TiposCitasAdapter(listaTiposCitasArray, new TiposCitasAdapter.onItemClickListener() {
                        @Override
                        public void onItemClick(final TipoCita tiposCitasAdapter, int posicion) {
                            //inicia Dialog para confirmar el tipo de cita seleccionado
                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.titleConfirmar);

                            builder.setMessage(getString(R.string.confrmarSeleccionTipoCita ,tiposCitasAdapter.getNombreTipoCita(),medico.getNombreUsuario()))
                                    .setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //si confirma la seleccion se activa un dialogo para obtener los años de experiencia
                                            NumberPicker picker = new NumberPicker(getContext());
                                            picker.setMinValue(0);
                                            picker.setMaxValue(70);

                                            FrameLayout layout = new FrameLayout(getContext());
                                            layout.addView(picker, new FrameLayout.LayoutParams(
                                                    FrameLayout.LayoutParams.WRAP_CONTENT,
                                                    FrameLayout.LayoutParams.WRAP_CONTENT,
                                                    Gravity.CENTER));

                                            new AlertDialog.Builder(getContext())
                                                    .setView(layout)
                                                    .setTitle(R.string.aniosExp)
                                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            aniosExp=String.valueOf(picker.getValue());//añade los años de experiencia a el objeto area de trabajo
                                                            cuposSeleccionados= new ArrayList<>();
                                                            nuevaArea = new AreaDeTrabajo();
                                                            nuevaArea.setTipoCita(tiposCitasAdapter);
                                                            Toast.makeText(getContext(), "Se ha añadido, por favor asigne cupos.", Toast.LENGTH_SHORT).show();
                                                            pasoTres.setVisibility(View.INVISIBLE);
                                                            pasoCuatro.setVisibility(View.VISIBLE);
                                                        }
                                                    })

                                                    .setNegativeButton(android.R.string.cancel, null)
                                                    .show();
                                        }
                                    })
                                    .setNegativeButton(R.string.denegar, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Toast.makeText(getContext(), "Se ha cancelado", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            // Create the AlertDialog object and return it
                            builder.create();
                            builder.show();

                        }
                    }, getActivity());
                    listaTiposCitas.setAdapter(adapter);


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
                Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
                System.out.println();
                Log.d("ERROR: ", error.toString());


            }
        });
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequestTipoCita);



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
                edadDoc.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);




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
            fotoCrearMedico.setImageURI(imageUri);
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                fotoCrearMedico.setImageBitmap(bitmap);
            }
        }

    }
}
