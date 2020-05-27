package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrearCitasYCupos extends Fragment {
    private CardView perfil, crearCupo, registrarTipoCita;
    private View view;
    private FragmentTransaction transaction;
    private Administrador admin;
    private AlertDialog alert, alterTipoCita;
    private Fragment perfilfragment;
    private int mHour, mMinute;
    private static final int PICK_IMAGE = 100;
    private static  final int TAKE_PHOTO=200;
    private ImageView fotoTipo;
    Uri imageUri;
    Bitmap bitmap;

    ProgressDialog progreso;
    // RequestQueue request;

    StringRequest stringRequest;

    Context context;

    StringRequest stringRequestCupo;

    public CrearCitasYCupos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_crear_citas_y_cupos, container, false);
        context= getActivity();
        admin= (Administrador) getArguments().getSerializable(getString(R.string.idAdmin));
        Toast.makeText(context, "Institucion"+admin.getInstitucion(), Toast.LENGTH_LONG).show();
        asociarElementos();
        operacionesClick();
        return view;
    }




    public void  asociarElementos(){
        perfil= view.findViewById(R.id.cardPerfil);
        crearCupo= view.findViewById(R.id.cardCrearCupo);
        registrarTipoCita= view.findViewById(R.id.cardCrearTipoCita);
    }

    public void operacionesClick(){
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragmentPerfil();
            }
        });
        crearCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogCupo();
            }
        });

        registrarTipoCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialogTipoCita();
            }
        });
    }

    public void abrirFragmentPerfil(){
        transaction= getActivity().getSupportFragmentManager().beginTransaction();
        if(perfilfragment!=null){
            transaction.replace(R.id.framecrear,perfilfragment).addToBackStack(null).commit();
        }else{
            perfilfragment= new PerfilAdmin();
            Bundle bundle= new Bundle();
            bundle.putSerializable(getString(R.string.idAdmin),admin);
            perfilfragment.setArguments(bundle);
            transaction.add(R.id.framecrear,perfilfragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void mostrarDialogCupo(){
        LayoutInflater inflater = getLayoutInflater();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View dialoglayout = inflater.inflate(R.layout.itemhora, null);

        final EditText hora,lugar;
        Button crearC;
        crearC= dialoglayout.findViewById(R.id.crearCupo);
        crearC.setVisibility(View.VISIBLE);
        crearC.setEnabled(true);
        hora= dialoglayout.findViewById(R.id.textHora);
        lugar= dialoglayout.findViewById(R.id.textLugar);
        hora.setFocusable(true);
        lugar.setFocusable(true);
        lugar.setEnabled(true);


        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                hora.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        crearC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hora.getText().toString().isEmpty() || lugar.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), getContext().getString(R.string.errorDatosVacios), Toast.LENGTH_SHORT).show();
                }else{
                    guardarCupo(hora.getText().toString(),lugar.getText().toString());
                }
                alert.dismiss();
            }
        });


        builder.setView(dialoglayout);
        alert = builder.create();
        alert.show();


    }

    public void abrirDialogTipoCita(){
        LayoutInflater inflaterTipo = getLayoutInflater();
        final AlertDialog.Builder builderTipo = new AlertDialog.Builder(getContext());
        final View dialoglayoutTipo = inflaterTipo.inflate(R.layout.itemtipocita, null);
        final EditText nombreTipo,detalleTipo;
        Button crearTipo;
        ImageButton tomarFotoTipo;
        nombreTipo= dialoglayoutTipo.findViewById(R.id.nombreTipo);
        detalleTipo= dialoglayoutTipo.findViewById(R.id.detalleTipoCita);
        nombreTipo.setFocusable(true);
        nombreTipo.setEnabled(true);
        detalleTipo.setEnabled(true);
        detalleTipo.setFocusable(true);
        fotoTipo=dialoglayoutTipo.findViewById(R.id.fototipo);
        fotoTipo.setImageDrawable(getResources().getDrawable(R.drawable.camera));
        fotoTipo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        tomarFotoTipo= dialoglayoutTipo.findViewById(R.id.tomarFotoTipo);
        crearTipo= dialoglayoutTipo.findViewById(R.id.crearTipoCita);
        tomarFotoTipo.setVisibility(View.VISIBLE);
        tomarFotoTipo.setFocusable(true);
        crearTipo.setVisibility(View.VISIBLE);
        crearTipo.setFocusable(true);
        crearTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detalleTipo.getText().toString().isEmpty() || nombreTipo.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), getContext().getString(R.string.errorDatosVacios), Toast.LENGTH_SHORT).show();
                }else{
                    guardarTipoCita(nombreTipo.getText().toString(),detalleTipo.getText().toString());

                }
                alterTipoCita.dismiss();
            }
        });

        tomarFotoTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        dialoglayoutTipo.setBackgroundColor(Color.TRANSPARENT);
        builderTipo.setView(dialoglayoutTipo);
        alterTipoCita = builderTipo.create();
        alterTipoCita.show();

    }

    public void guardarCupo(final String horaCupo, final String lugarCupo){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();


        String url= context.getString(R.string.urlRegistroTipoCitaYCupo,getString(R.string.nameServer));

        stringRequestCupo=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progreso.dismiss();

                if (response.trim().equalsIgnoreCase("registra")){
                    Toast.makeText(getContext(),"Se ha registrado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No se ha registrado "+response,Toast.LENGTH_LONG).show();
                    Log.i("RESPUESTA: ",""+response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.dismiss();
                Log.i("ERRORVOLLEY: ",""+error.getMessage());
                Toast.makeText(getContext(),"No se ha podido conectar"+error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(),"No se ha podido conectar"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String datoTres=getString(R.string.noDisponible);
                String tipoConsulta="cupo";
                String datoUno =lugarCupo;
                String datoDos=horaCupo;
                String institucion=admin.getInstitucion();
                Map<String,String> parametros=new HashMap<>();
                parametros.put("datoUno",datoUno);
                parametros.put("datoDos",datoDos);
                parametros.put("datoTres",datoTres);
                parametros.put("institucion",institucion);
                parametros.put("tipoConsulta",tipoConsulta);
                return parametros;
            }

        };
        stringRequestCupo.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequestCupo);


    }

    public void guardarTipoCita(final String nombre, final String detalle){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();


        String url= context.getString(R.string.urlRegistroTipoCitaYCupo,getString(R.string.nameServer));

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progreso.dismiss();

                if (response.trim().equalsIgnoreCase("registra")){
                    Toast.makeText(getContext(),"Se ha registrado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No se ha registrado "+response,Toast.LENGTH_LONG).show();
                    Log.i("RESPUESTA: ",""+response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.dismiss();
                Log.i("ERRORVOLLEY: ",""+error.getMessage());
                Toast.makeText(getContext(),"No se ha podido conectar"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String datoTres=convertirImgString(bitmap);
                String tipoConsulta="tipoCita";
                String datoUno =nombre;
                String datoDos=detalle;
                String institucion=admin.getInstitucion();
                Map<String,String> parametros=new HashMap<>();
                parametros.put("datoUno",datoUno);
                parametros.put("datoDos",datoDos);
                parametros.put("datoTres",datoTres);
                parametros.put("institucion",institucion);
                parametros.put("tipoConsulta",tipoConsulta);
                return parametros;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);

    }



    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            fotoTipo.setImageURI(imageUri);
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                fotoTipo.setImageBitmap(bitmap);
            }
        }

    }
}
