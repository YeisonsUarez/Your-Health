package co.edu.unab.hernandez.yeison.your_health.fragmentosmedico;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.adaptadores.MedicamentosAdapter;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medicamento;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.Paciente;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrearFormulaMedica extends Fragment {
    private View view;
    private RecyclerView listaMedicamentos;
    private RecyclerView.LayoutManager manager;
    private Context context;
    private ArrayList<Medicamento> medicamentosArrayList;
    private Medico medico;
    JsonObjectRequest jsonObjectMedicamentos;

    public CrearFormulaMedica() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_crear_formula_medica, container, false);
        context = getActivity();
        medico = (Medico) getArguments().getSerializable(getString(R.string.textMedico));
        asociarelementos();
        cargarDatos();
        return view;
    }

    private void asociarelementos() {
        listaMedicamentos = view.findViewById(R.id.listaMedicametos);
        manager = new LinearLayoutManager(context);
        medicamentosArrayList = new ArrayList<>();

    }


    private void cargarDatos() {

        String url = getString(R.string.gestionMedicamentos, getString(R.string.nameServer), "obtenerM", medico.getNumeroDocumento());

        jsonObjectMedicamentos = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Medicamento medicamento;
                JSONArray json = response.optJSONArray("usuario");
                try {

                    for (int i = 0; i < json.length(); i++) {
                        medicamento = new Medicamento();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);

                        medicamento.setNombreMedicamento(jsonObject.optString("nombre"));
                        medicamento.setCantidadMedicamento(jsonObject.optString("cantidad"));
                        medicamento.setDetalleMedicamento(jsonObject.optString("detalle"));
                        Paciente p = new Paciente();
                        p.setNumeroDocumento(jsonObject.optString("idpaciente"));
                        medicamento.setPaciente(p);
                        Medico m = new Medico();
                        m.setNumeroDocumento(jsonObject.optString("idmedico"));
                        medicamento.setMedico(m);
                        medicamentosArrayList.add(medicamento);
                    }

                    MedicamentosAdapter adapter = new MedicamentosAdapter(medicamentosArrayList, new MedicamentosAdapter.onItemClickListener() {
                        @Override
                        public void onItemClick(Medicamento medicamento, int posicion) {
                            Toast.makeText(context, medicamento.getNombreMedicamento(), Toast.LENGTH_SHORT).show();
                        }
                    }, context);
                    listaMedicamentos.setLayoutManager(manager);
                    listaMedicamentos.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                            " " + response, Toast.LENGTH_LONG).show();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error al enviar medicamentos" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(jsonObjectMedicamentos);


    }
}
