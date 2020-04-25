package co.edu.unab.hernandez.yeison.your_health.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.TipoCita;

public class TiposCitasAdapter extends RecyclerView.Adapter {
    ArrayList<TipoCita> tiposCitas;
    onItemClickListener miListener;
    Context context;

    public TiposCitasAdapter(ArrayList<TipoCita> tiposCitas, onItemClickListener miListener, Context context) {
        this.tiposCitas = tiposCitas;
        this.miListener = miListener;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre,detalle;
        ImageView foto ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.nombreTipo);
            detalle= itemView.findViewById(R.id.detalleTipoCita);
            foto= itemView.findViewById(R.id.fototipo);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mivista= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemtipocita,parent,false);
        return new ViewHolder(mivista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder =(ViewHolder)holder;

        final TipoCita tipoCita= tiposCitas.get(position);
        viewHolder.nombre.setText(tipoCita.getNombreTipoCita());
        viewHolder.detalle.setText(tipoCita.getDetalleTipoCita());
        Glide.with(context).load(tipoCita.getUrlImagen()).dontAnimate().placeholder(new ColorDrawable(Color.WHITE)).into(viewHolder.foto);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miListener.onItemClick(tipoCita,position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return tiposCitas.size();
    }
    public interface onItemClickListener{ // Puede tener cualquier nombre
         void onItemClick(TipoCita tiposCitasAdapter, int posicion);

    }
}
