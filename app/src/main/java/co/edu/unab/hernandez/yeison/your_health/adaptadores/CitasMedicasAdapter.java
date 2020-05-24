package co.edu.unab.hernandez.yeison.your_health.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.CitaMedica;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;

public class CitasMedicasAdapter extends RecyclerView.Adapter {
    ArrayList<CitaMedica> citaMedicaArrayList;
    onItemClickListener miListener;
    Context context;

    public CitasMedicasAdapter(ArrayList<CitaMedica> citaMedicaArrayList, onItemClickListener miListener, Context context) {
        this.citaMedicaArrayList = citaMedicaArrayList;
        this.miListener = miListener;
        this.context = context;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tipocita,fechacita,horacita,lugar,estado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipocita= itemView.findViewById(R.id.textTipoCita);
            fechacita= itemView.findViewById(R.id.textFechaCita);
            horacita= itemView.findViewById(R.id.textHora);
            lugar= itemView.findViewById(R.id.textLugar);
            estado= itemView.findViewById(R.id.textEstado);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mivista= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcitaspaciente,parent,false);
        return new ViewHolder(mivista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        CitasMedicasAdapter.ViewHolder viewHolder =(CitasMedicasAdapter.ViewHolder) holder;
        final CitaMedica citaMedica= citaMedicaArrayList.get(position);
        viewHolder.tipocita.setText(citaMedica.getTipoCita().getNombreTipoCita());
        viewHolder.fechacita.setText(citaMedica.getFechaCita()+ " "+citaMedica.getCupo().getFecha());
        viewHolder.horacita.setText(context.getString(R.string.textMedico)+": "+citaMedica.getMedico().getNombreUsuario());
        viewHolder.lugar.setText(citaMedica.getCupo().getLugar());
        viewHolder.estado.setText(citaMedica.getEstadoCita());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miListener.onItemClick(citaMedica,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return citaMedicaArrayList.size();
    }
    public interface onItemClickListener{ // Puede tener cualquier nombre
        void onItemClick(CitaMedica citaMedica, int posicion);

    }
}
