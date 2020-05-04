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
import co.edu.unab.hernandez.yeison.your_health.fragmentosmedico.CrearFormulaMedica;
import co.edu.unab.hernandez.yeison.your_health.fragmentosmedico.PerfilMedico;
import co.edu.unab.hernandez.yeison.your_health.fragmentosmedico.VerCitas;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;

public class InicioMedico extends AppCompatActivity {
    private TabLayout tabLayout;
    private int[] tabIncons={R.drawable.doctor,R.drawable.medicinas,R.drawable.usuario};
    private int[] tabText={R.string.itemUnoTabDoctor,R.string.itemDosTabDoctor,R.string.tabPacienteOpcionCuatro};
    public Medico medico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_medico);
        getSupportActionBar().hide();
        medico = (Medico) getIntent().getSerializableExtra(getString(R.string.textMedico));
        Toast.makeText(this, ""+medico.getNumeroDocumento(), Toast.LENGTH_SHORT).show();
        tabLayout= findViewById(R.id.tabDoctor);
        ViewPager viewPager2= findViewById(R.id.paginasDoctor);
        loadViewPager(viewPager2);
        tabLayout.setupWithViewPager(viewPager2);
        tabIncons();



    }




    private void loadViewPager(ViewPager viewPager2){
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(newInstance(new VerCitas()));
        adapter.addFragment(newInstance(new CrearFormulaMedica()));
        adapter.addFragment(newInstance(new PerfilMedico()));
        viewPager2.setAdapter(adapter);

    }
    private Fragment newInstance(Fragment fragment){// metodo para psar los datos del paciente
        Bundle bundle= new Bundle();
        bundle.putSerializable(getString(R.string.textMedico),medico);
        fragment.setArguments(bundle);
        return fragment;
    }


    public  void tabIncons(){
        //https://www.flaticon.es/icono-gratis/inscripcion_2621764?term=cita%20medica&page=1&position=10 // horario
        //https://www.flaticon.es/icono-gratis/test-de-drogas_2037107?term=medicamentos&page=1&position=15  formulamedica
        for(int i=0;i<3;i++){
            tabLayout.getTabAt(i).setIcon(tabIncons[i]);
            tabLayout.getTabAt(i).setText(tabText[i]);
        }
    }

}
