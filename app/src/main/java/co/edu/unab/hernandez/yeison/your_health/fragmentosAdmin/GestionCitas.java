package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.unab.hernandez.yeison.your_health.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GestionCitas extends Fragment {
    private RecyclerView listaCitas;
    private View view;


    public GestionCitas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_gestion_citas, container, false);
        asociarElementos();
        operacionesClick();
        return view;
    }

    public void  asociarElementos(){
        listaCitas= view.findViewById(R.id.listaCitas);
    }

    public void operacionesClick(){

    }

}
