package co.edu.unab.hernandez.yeison.your_health.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medicamento;

public class MedicamentosAdapter extends RecyclerView.Adapter {
    ArrayList<Medicamento> medicamentos;
    onItemClickListener miListener;
    Context context;

    public MedicamentosAdapter(ArrayList<Medicamento> medicamentos, onItemClickListener miListener, Context context) {
        this.medicamentos = medicamentos;
        this.miListener = miListener;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre,detalle, cantidad;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.itemnombremedicamento);
            detalle= itemView.findViewById(R.id.itemdetallemedicamento);
            cantidad= itemView.findViewById(R.id.itemcantidadmedicamento);

        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mivista= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemmedicamento,parent,false);
        return new ViewHolder(mivista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder =(ViewHolder)holder;
        final Medicamento medicamento= medicamentos.get(position);
        viewHolder.nombre.setText(viewHolder.nombre.getText().toString()+ " "+medicamento.getNombreMedicamento());
        viewHolder.detalle.setText(medicamento.getDetalleMedicamento());
        viewHolder.cantidad.setText(viewHolder.cantidad.getText().toString() +" "+medicamento.getCantidadMedicamento());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miListener.onItemClick(medicamento,position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return medicamentos.size();
    }
    public interface onItemClickListener{ // Puede tener cualquier nombre
         void onItemClick(Medicamento medicamento, int posicion);

    }
}
