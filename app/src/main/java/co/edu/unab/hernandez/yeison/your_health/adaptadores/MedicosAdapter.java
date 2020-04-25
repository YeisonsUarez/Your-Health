package co.edu.unab.hernandez.yeison.your_health.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import de.hdodenhof.circleimageview.CircleImageView;

public class MedicosAdapter extends RecyclerView.Adapter {
    ArrayList<Medico> medicos;
    onItemClickListener miListener;
    Context context;

    public MedicosAdapter(ArrayList<Medico> medicos, onItemClickListener miListener, Context context) {
        this.medicos = medicos;
        this.miListener = miListener;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre,text_detalle;
        private CircleImageView fotoDoc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.nombreitemDoc);
            text_detalle= itemView.findViewById(R.id.detalleItemDoc);
            fotoDoc= itemView.findViewById(R.id.fotoitemDoc);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mivista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_paciente,parent,false);
        return new ViewHolder(mivista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder =(ViewHolder)holder;
        final Medico medico= medicos.get(position);
        viewHolder.nombre.setText(medico.getNombreUsuario());
        viewHolder.text_detalle.setText(medico.getDescripcion());
        Glide.with(context).load(medico.getFotoPerfil()).dontAnimate().placeholder(new ColorDrawable(Color.WHITE)).into(viewHolder.fotoDoc);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miListener.onItemClick(medico,position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return medicos.size();
    }
    public interface onItemClickListener{ // Puede tener cualquier nombre
         void onItemClick(Medico medico, int posicion);

    }
}
