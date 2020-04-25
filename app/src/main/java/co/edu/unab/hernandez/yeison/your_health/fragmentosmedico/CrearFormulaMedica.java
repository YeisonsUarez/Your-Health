package co.edu.unab.hernandez.yeison.your_health.fragmentosmedico;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.unab.hernandez.yeison.your_health.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrearFormulaMedica extends Fragment {

    public CrearFormulaMedica() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crear_formula_medica, container, false);
    }
}
