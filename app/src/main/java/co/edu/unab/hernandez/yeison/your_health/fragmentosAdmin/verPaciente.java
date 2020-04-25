package co.edu.unab.hernandez.yeison.your_health.fragmentosAdmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.unab.hernandez.yeison.your_health.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link verPaciente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class verPaciente extends Fragment {

    public verPaciente() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_paciente, container, false);
    }
}
