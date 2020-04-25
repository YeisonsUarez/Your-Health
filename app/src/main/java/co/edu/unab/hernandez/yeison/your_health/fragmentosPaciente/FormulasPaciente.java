package co.edu.unab.hernandez.yeison.your_health.fragmentosPaciente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.unab.hernandez.yeison.your_health.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormulasPaciente extends Fragment {
    private View view;

    public FormulasPaciente() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_formulas_paciente, container, false);
        return view;
    }
}
