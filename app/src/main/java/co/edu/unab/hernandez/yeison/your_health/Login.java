package co.edu.unab.hernandez.yeison.your_health;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.Usuario;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;
import co.edu.unab.hernandez.yeison.your_health.pantallasinicio.InicioAdministrador;
import co.edu.unab.hernandez.yeison.your_health.pantallasinicio.InicioMedico;
import co.edu.unab.hernandez.yeison.your_health.pantallasinicio.InicioPaciente;

import static java.security.AccessController.getContext;

public class Login extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    private ImageView imageLogin;
    private EditText cedula, contrasena;
    private Button btnLogin;
    private Spinner spinnerTipoDoc;
    SharedPreferences LocalStorage;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    JSONArray jsonArray=null;
    JSONObject jsonObject= null;
    RequestQueue requestQueue;

    private Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        LocalStorage= getSharedPreferences(getString(R.string.nameStorage),MODE_PRIVATE);
        Boolean status= LocalStorage.getBoolean(getString(R.string.strStatus),false);
        String user= LocalStorage.getString(getString(R.string.idUser),"");
        String tipoDoc=LocalStorage.getString(getString(R.string.idTipoDoc),"");
        String contrasena= LocalStorage.getString(getString(R.string.textPassword),"");
        usuario= new Usuario();
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);



        if(status){
            progreso=new ProgressDialog(Login.this);
            progreso.setMessage(getString(R.string.cargando));
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setCancelable(true);
            progreso.show();
            iniciarValidacion(tipoDoc,user,contrasena);
        }


        asignarElementos();
        anadirDatosSpinner();

        Glide.with(this).load(getString(R.string.str_image_login)).placeholder(new ColorDrawable(Color.WHITE)).into(imageLogin);

        operacionesBotones();
    }
    public void  asignarElementos(){
        imageLogin= findViewById(R.id.imageLogin);
        cedula= findViewById(R.id.editCedula);
        contrasena= findViewById(R.id.editPass);
        btnLogin= findViewById(R.id.btnLogin);
        spinnerTipoDoc= findViewById(R.id.spinnerTipoDoc);
    }
    private void operacionesBotones(){

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progreso=new ProgressDialog(Login.this);
                progreso.setMessage(getString(R.string.cargando));
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setCancelable(true);
                progreso.show();

                if(spinnerTipoDoc.getSelectedItemId()!=0 && !cedula.getText().toString().isEmpty() && !contrasena.getText().toString().isEmpty()){
                    iniciarValidacion(spinnerTipoDoc.getSelectedItem().toString(),cedula.getText().toString(),contrasena.getText().toString());

                }else{
                    progreso.dismiss();
                    Toast.makeText(Login.this, getString(R.string.tectErrorCredenciales), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void anadirDatosSpinner(){
        Resources res= getResources();
        final String[] datos = res.getStringArray(R.array.tiposDocumentos);

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, datos);
        spinnerTipoDoc.setAdapter(adaptador);
    }
    private void iniciarValidacion(String tipoDocumento, String numeroDocumento,String contrasena){

        String urlLogin=getString(R.string.urlLogin, getString(R.string.nameServer),tipoDocumento,numeroDocumento,contrasena);
        urlLogin= urlLogin.replace(" ","%20"); //cambia los espacios en blanco por porcentaje 20 para que el servidor los lea
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, urlLogin, null,this,this);
        VolleySingleton.getIntanciaVolley(Login.this).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        Toast.makeText(Login.this, getString(R.string.tectErrorCredenciales), Toast.LENGTH_LONG).show();
        cedula.setText("");
        contrasena.setText("");
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.dismiss();
        jsonArray= response.optJSONArray("usuario");
        Resources res= getResources();
        final String[] atributosUsuario = res.getStringArray(R.array.atributosUsuario);

        try {

            jsonObject=jsonArray.getJSONObject(0);
            usuario.setNumeroDocumento(jsonObject.optString(atributosUsuario[0]));
            usuario.setTipoDocumento(jsonObject.optString(atributosUsuario[1]));
            usuario.setNombreUsuario(jsonObject.optString(atributosUsuario[2]));
            usuario.setFechaNacimientoUsuario(jsonObject.optString(atributosUsuario[3]));
            usuario.setSexo(jsonObject.optString(atributosUsuario[4]));
            usuario.setCorreoUsuario(jsonObject.optString(atributosUsuario[5]));
            usuario.setContrasenauUsuario(jsonObject.optString(atributosUsuario[6]));
            usuario.setTelefono(jsonObject.optString(atributosUsuario[7]));
            usuario.setInstitucion(jsonObject.optString(atributosUsuario[8]));
            usuario.setFotoPerfil(jsonObject.optString(atributosUsuario[9]));
            usuario.setTipoUsuario(jsonObject.optString(atributosUsuario[10]));
            SharedPreferences.Editor edit= LocalStorage.edit();
            edit.putBoolean(getString(R.string.strStatus),true);
            edit.putString(getString(R.string.idUser),usuario.getNumeroDocumento());
            edit.putString(getString(R.string.idTipoDoc),usuario.getTipoDocumento());
            edit.putString(getString(R.string.textPassword),usuario.getContrasenauUsuario());
            edit.apply();
            Toast.makeText(Login.this, getString(R.string.bienvenido, usuario.getNombreUsuario()+usuario.getTipoUsuario()), Toast.LENGTH_SHORT).show();
            if(usuario.getTipoUsuario()!=null){

                switch (usuario.getTipoUsuario()){
                    case "Paciente":

                        Paciente paciente= new Paciente();
                        paciente.setTipoDocumento(usuario.getTipoDocumento());
                        paciente.setNumeroDocumento(usuario.getNumeroDocumento());
                        paciente.setNombreUsuario(usuario.getNombreUsuario());
                        paciente.setInstitucion(usuario.getInstitucion());
                        paciente.setFotoPerfil(usuario.getFotoPerfil());
                        paciente.setCorreoUsuario(usuario.getCorreoUsuario());
                        paciente.setContrasenauUsuario(usuario.getContrasenauUsuario());
                        paciente.setFechaNacimientoUsuario(usuario.getFechaNacimientoUsuario());
                        paciente.setSexo(usuario.getSexo());
                        paciente.setTelefono(usuario.getTelefono());
                        paciente.setTipoUsuario(usuario.getTipoUsuario());
                        Intent intent= new Intent(Login.this, InicioPaciente.class);
                        intent.putExtra(getString(R.string.textPaciente),paciente);
                        startActivity(intent);
                        finish();

                        break;
                    case "Medico":
                        Medico medico= new Medico();
                        medico.setTipoDocumento(usuario.getTipoDocumento());
                        medico.setNumeroDocumento(usuario.getNumeroDocumento());
                        medico.setNombreUsuario(usuario.getNombreUsuario());
                        medico.setInstitucion(usuario.getInstitucion());
                        medico.setFotoPerfil(usuario.getFotoPerfil());
                        medico.setCorreoUsuario(usuario.getCorreoUsuario());
                        medico.setContrasenauUsuario(usuario.getContrasenauUsuario());
                        medico.setFechaNacimientoUsuario(usuario.getFechaNacimientoUsuario());
                        medico.setSexo(usuario.getSexo());
                        medico.setTelefono(usuario.getTelefono());
                        medico.setTipoUsuario(usuario.getTipoUsuario());
                        Intent intentM= new Intent(Login.this, InicioMedico.class);
                        intentM.putExtra(getString(R.string.textMedico),medico);
                        startActivity(intentM);
                        finish();
                        break;
                    case "Administrador":
                        Administrador administrador=  new Administrador();
                        administrador.setTipoDocumento(usuario.getTipoDocumento());
                        administrador.setNumeroDocumento(usuario.getNumeroDocumento());
                        administrador.setNombreUsuario(usuario.getNombreUsuario());
                        administrador.setInstitucion(usuario.getInstitucion());
                        administrador.setFotoPerfil(usuario.getFotoPerfil());
                        administrador.setCorreoUsuario(usuario.getCorreoUsuario());
                        administrador.setContrasenauUsuario(usuario.getContrasenauUsuario());
                        administrador.setFechaNacimientoUsuario(usuario.getFechaNacimientoUsuario());
                        administrador.setSexo(usuario.getSexo());
                        administrador.setTelefono(usuario.getTelefono());
                        administrador.setTipoUsuario(usuario.getTipoUsuario());
                        Intent intentA= new Intent(Login.this, InicioAdministrador.class);
                        intentA.putExtra(getString(R.string.idAdmin),administrador);
                        startActivity(intentA);
                        finish();
                        break;
                }


            }

        } catch (JSONException e) {
            progreso.dismiss();
            Toast.makeText(Login.this, getString(R.string.tectErrorCredenciales), Toast.LENGTH_LONG).show();
            cedula.setText("");
            contrasena.setText("");
        }


    }

}
