package co.edu.unab.hernandez.yeison.your_health.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Cupo;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import de.hdodenhof.circleimageview.CircleImageView;

public class CuposAdapter extends RecyclerView.Adapter {
    ArrayList<Cupo> cupos;
    onItemClickListener miListener;
    Context context;

    public CuposAdapter(ArrayList<Cupo> cupos, onItemClickListener miListener, Context context) {
        this.cupos = cupos;
        this.miListener = miListener;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public EditText hora,lugar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hora= itemView.findViewById(R.id.textHora);
            lugar= itemView.findViewById(R.id.textLugar);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mivista= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhora,parent,false);
        return new ViewHolder(mivista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder =(ViewHolder)holder;
        final Cupo cupo= cupos.get(position);
        viewHolder.hora.setText(cupo.getFecha());
        viewHolder.lugar.setText(cupo.getLugar());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miListener.onItemClick(cupo,position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return cupos.size();
    }
    public interface onItemClickListener{ // Puede tener cualquier nombre
         void onItemClick(Cupo cupo, int posicion);

    }
}
