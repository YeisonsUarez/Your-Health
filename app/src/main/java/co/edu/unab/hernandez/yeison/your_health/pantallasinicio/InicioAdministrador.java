package co.edu.unab.hernandez.yeison.your_health.pantallasinicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.ViewPagerAdapter;
import co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin.GestionCitas;
import co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin.PerfilAdmin;
import co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin.Usuarios;
import co.edu.unab.hernandez.yeison.your_health.modelos.Administrador;

public class InicioAdministrador extends AppCompatActivity {
    private TabLayout tabLayout;
    private int[] tabIncons={R.drawable.verusuarios,R.drawable.gestionarcitas,R.drawable.usuario};
    private int[] tabText={R.string.itemUnoTabAdmin,R.string.itemDosTabAdmin,R.string.itemTresTabAdmin};
    private Administrador admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_administrador);
        getSupportActionBar().hide();
        admin= (Administrador)getIntent().getSerializableExtra(getString(R.string.idAdmin));
        tabLayout= findViewById(R.id.tabAdmin);
        ViewPager viewPager2= findViewById(R.id.paginasAdmin);
        loadViewPager(viewPager2);
        tabLayout.setupWithViewPager(viewPager2);
        tabIncons();
    }


    public  void tabIncons(){
        //https://www.flaticon.es/icono-gratis/inscripcion_2621764?term=cita%20medica&page=1&position=10 // horario
        //https://www.flaticon.es/icono-gratis/test-de-drogas_2037107?term=medicamentos&page=1&position=15  formulamedica
        for(int i=0;i<3;i++){
            tabLayout.getTabAt(i).setIcon(tabIncons[i]);
            tabLayout.getTabAt(i).setText(tabText[i]);
        }
    }
    private void loadViewPager(ViewPager viewPager2){
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(newInstance(new Usuarios()));
        adapter.addFragment(newInstance(new GestionCitas()));
        adapter.addFragment(newInstance(new PerfilAdmin()));
        viewPager2.setAdapter(adapter);

    }

    private Fragment newInstance(Fragment fragment){// metodo para psar los datos del paciente
        Bundle bundle= new Bundle();
        bundle.putSerializable(getString(R.string.idAdmin),admin);
        fragment.setArguments(bundle);
        return fragment;
    }
}
