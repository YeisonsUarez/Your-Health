package co.edu.unab.hernandez.yeison.your_health.pantallasinicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.ViewPagerAdapter;
import co.edu.unab.hernandez.yeison.your_health.fragmentosPaciente.CitasPaciente;
import co.edu.unab.hernandez.yeison.your_health.fragmentosPaciente.FormulasPaciente;
import co.edu.unab.hernandez.yeison.your_health.fragmentosPaciente.HorariosPaciente;
import co.edu.unab.hernandez.yeison.your_health.fragmentosPaciente.PerfilPaciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;

public class InicioPaciente extends AppCompatActivity {
    private TabLayout tabLayout;
    private int[] tabIncons={R.drawable.calendario,R.drawable.doctor,R.drawable.medicinas,R.drawable.usuario};
    private int[] tabText={R.string.tabPacienteOpcionUno,R.string.tapPacienteOpcionDos,R.string.tabPacienteOpcionTres,R.string.tabPacienteOpcionCuatro};
    public Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_paciente);
        getSupportActionBar().hide();
        paciente = (Paciente) getIntent().getSerializableExtra(getString(R.string.textPaciente));
        paciente= BuscarDatosPaciente(paciente);
        Toast.makeText(this, ""+paciente.getNumeroDocumento(), Toast.LENGTH_SHORT).show();
        tabLayout= findViewById(R.id.tabPaciente);
        ViewPager viewPager2= findViewById(R.id.paginas);
        loadViewPager(viewPager2);
        tabLayout.setupWithViewPager(viewPager2);
        tabIncons();



    }

    private Paciente BuscarDatosPaciente(Paciente paciente) {
        paciente.setFotoPerfil("https://i.pinimg.com/474x/45/f0/85/45f08581d59b28f7020843ad725319b0.jpg");
        paciente.setTelefono("3115540543");
        paciente.setCorreoUsuario("Izasa@gmail.com");
        paciente.setNombreUsuario("Juan Pablo Isaza");
        return paciente;
    }


    private void loadViewPager(ViewPager viewPager2){
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(newInstance(new HorariosPaciente()));
        adapter.addFragment(newInstance(new CitasPaciente()));
        adapter.addFragment(newInstance(new FormulasPaciente()));
        adapter.addFragment(newInstance(new PerfilPaciente()));
        viewPager2.setAdapter(adapter);

    }
    private Fragment newInstance(Fragment fragment){// metodo para psar los datos del paciente
        Bundle bundle= new Bundle();
        bundle.putSerializable(getString(R.string.textPaciente),paciente);
        fragment.setArguments(bundle);
        return fragment;
    }


    public  void tabIncons(){
        //https://www.flaticon.es/icono-gratis/inscripcion_2621764?term=cita%20medica&page=1&position=10 // horario
        //https://www.flaticon.es/icono-gratis/test-de-drogas_2037107?term=medicamentos&page=1&position=15  formulamedica
        for(int i=0;i<4;i++){
            tabLayout.getTabAt(i).setIcon(tabIncons[i]);
            tabLayout.getTabAt(i).setText(tabText[i]);
        }
    }

}
