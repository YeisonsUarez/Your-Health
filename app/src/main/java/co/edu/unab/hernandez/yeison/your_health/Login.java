package co.edu.unab.hernandez.yeison.your_health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.pantallasinicio.InicioAdministrador;
import co.edu.unab.hernandez.yeison.your_health.pantallasinicio.InicioMedico;
import co.edu.unab.hernandez.yeison.your_health.pantallasinicio.InicioPaciente;

public class Login extends AppCompatActivity {
    private ImageView imageLogin;
    private EditText cedula, contrasena;
    private Button btnLogin;
    private Spinner spinnerTipoDoc;
    SharedPreferences LocalStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        LocalStorage= getSharedPreferences(getString(R.string.nameStorage),MODE_PRIVATE);
        Boolean status= LocalStorage.getBoolean(getString(R.string.strStatus),false);
        String user= LocalStorage.getString(getString(R.string.idUser),getString(R.string.nombre));
        String tipoDoc=LocalStorage.getString(getString(R.string.idTipoDoc),"");
        String rol= LocalStorage.getString(getString(R.string.textRol),"");
        if(status){
            if(rol.equalsIgnoreCase(getString(R.string.textMedico))){
                Medico medico= new Medico();
                medico.setNumeroDocumento(user);
                medico.setTipoDocumento(tipoDoc);
                Intent intent= new Intent(Login.this, InicioMedico.class);
                intent.putExtra(getString(R.string.textMedico),medico);
                startActivity(intent);
                finish();
            }else{
                if(rol.equalsIgnoreCase(getString(R.string.textPaciente))){
                    Paciente paciente= new Paciente();
                    paciente.setNumeroDocumento(user);
                    paciente.setTipoDocumento(tipoDoc);
                    Intent intent= new Intent(Login.this, InicioPaciente.class);
                    intent.putExtra(getString(R.string.textPaciente),paciente);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Login.this, getString(R.string.errorDatosVacios), Toast.LENGTH_SHORT).show();
                }
            }

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

    private boolean validar() {
        return (cedula.getText().toString().equalsIgnoreCase("1234") && contrasena.getText().toString().equalsIgnoreCase("qwerty") ) ? true:false;
    }
    private void operacionesBotones(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Login.this, InicioAdministrador.class);
                Administrador administrador= new Administrador();
                administrador.setNumeroDocumento("121212");
                administrador.setTipoDocumento("CC");
                intent.putExtra(getString(R.string.idAdmin),administrador);
                startActivity(intent);/*
                if(!spinnerTipoDoc.getSelectedItem().toString().equalsIgnoreCase("") || !cedula.getText().toString().isEmpty()|| !contrasena.getText().toString().isEmpty()){
                    if(validar()){
                        SharedPreferences.Editor edit= LocalStorage.edit();
                        edit.putBoolean(getString(R.string.strStatus),true);
                        edit.putString(getString(R.string.idUser),cedula.getText().toString());
                        edit.putString(getString(R.string.idTipoDoc),spinnerTipoDoc.getSelectedItem().toString());
                        String rol= spinnerTipoDoc.getSelectedItem().toString().equalsIgnoreCase("NIT")?getString(R.string.textMedico):getString(R.string.textPaciente);
                        edit.putString(getString(R.string.textRol),rol);
                        edit.apply();
                        if(rol.equalsIgnoreCase(getString(R.string.textMedico))){
                            Medico medico= new Medico();
                            medico.setNumeroDocumento(cedula.getText().toString());
                            medico.setTipoDocumento(spinnerTipoDoc.getSelectedItem().toString());
                            Intent intent= new Intent(Login.this, InicioMedico.class);
                            intent.putExtra(getString(R.string.textMedico),medico);
                            startActivity(intent);
                            finish();
                        }else{
                            if(rol.equalsIgnoreCase(getString(R.string.textPaciente))){
                                Paciente paciente= new Paciente();
                                paciente.setNumeroDocumento(cedula.getText().toString());
                                paciente.setTipoDocumento(spinnerTipoDoc.getSelectedItem().toString());
                                Intent intent= new Intent(Login.this, InicioPaciente.class);
                                intent.putExtra(getString(R.string.textPaciente),paciente);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(Login.this, getString(R.string.errorDatosVacios), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }else{
                        Toast.makeText(Login.this, getString(R.string.tectErrorCredenciales), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Login.this, "1234"+getString(R.string.errorDatosVacios), Toast.LENGTH_SHORT).show();
                }*/

            }
        });
    }
    private void anadirDatosSpinner(){
        final String[] datos =
                new String[]{getString(R.string.textSpinner),"Cedula de Cuidadania","Tarjeta de Identidad","NIT"};

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, datos);
        spinnerTipoDoc.setAdapter(adaptador);
    }
}
