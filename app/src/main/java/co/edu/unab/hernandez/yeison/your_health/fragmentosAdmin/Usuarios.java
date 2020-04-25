package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.UsuariosAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.Usuario;

/**
 * A simple {@link Fragment} subclass.
 */
public class Usuarios extends Fragment {
    private Switch tipoUsuario;
    private RecyclerView listadeUsuarios;
    private FloatingActionMenu anadirUsuarios;
    private FloatingActionButton btnPaciente,btnMedico;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Usuario> usuarios;
    private View view;
    private Switch tipoUsuarioSwitch;
    private FragmentTransaction transaction;
    Fragment crearPaciente, crearMedico, editarUsuario;
    public Usuarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_usuarios, container, false);
       asociarElementos();
        transaction= getActivity().getSupportFragmentManager().beginTransaction();
        crearPaciente= new CrearPaciente();
       operacionesDeBotones();

        return view;
    }


    private void operacionesDeBotones() {
        btnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogPaciente();
            }
        });
        btnMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Cliack en Medico", Toast.LENGTH_SHORT).show();
            }
        });
        tipoUsuarioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                 usuarios= new ArrayList<Usuario>();
                 for( int i=0; i<=5;i++){
                 Medico m= new Medico();
                 m.setTelefono("12121");
                 m.setFotoPerfil("https://ichef.bbci.co.uk/news/ws/410/amz/worldservice/live/assets/images/2015/09/07/150907151319_hannibal_624x351_nbc.jpg");
                 m.setNombreUsuario("Hannibal");
                 m.setTipoDocumento("C.C.");
                 m.setNumeroDocumento("121212");
                 m.setCorreoUsuario("hannibal@gmail.com");
                 usuarios.add(m);
                 iniciarDatos();
                 }

                }else{
                    usuarios= new ArrayList<Usuario>();
                    Paciente m= new Paciente();
                    for( int i=0; i<=5;i++){
                        m.setTelefono("12121");
                    m.setFotoPerfil("https://okdiario.com/img/series/2017/05/25/will-graham.jpg");
                    m.setNombreUsuario("Will");
                    m.setTipoDocumento("C.C.");
                    m.setNumeroDocumento("121212");
                    m.setCorreoUsuario("hannibal@gmail.com");
                    usuarios.add(m);
                    }
                    iniciarDatos();
                }
            }
        });
    }
    private void iniciarDatos(){
        UsuariosAdapter adapter= new UsuariosAdapter(usuarios, new UsuariosAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Usuario usuario, int posicion) {
                Toast.makeText(getContext(), usuario.getTipoUsuario(), Toast.LENGTH_SHORT).show();
            }
        }, getActivity());
        adapter.notifyDataSetChanged();
        listadeUsuarios.setAdapter(adapter);
    }
    public void mostrarDialogPaciente(){
        transaction.add(R.id.frameLayoutUsuarios,crearPaciente);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void asociarElementos(){
        tipoUsuario= view.findViewById(R.id.seleccionUsuario);
        listadeUsuarios= view.findViewById(R.id.listaUsuarios);
        anadirUsuarios= view.findViewById(R.id.menuButton);
        anadirUsuarios.setClosedOnTouchOutside(true);
        btnMedico= view.findViewById(R.id.btnMedico);
        btnPaciente= view.findViewById(R.id.btnPaciente);
        tipoUsuarioSwitch= view.findViewById(R.id.seleccionUsuario);
        manager = new LinearLayoutManager(getContext());

        listadeUsuarios.setLayoutManager(manager);
    }
}
