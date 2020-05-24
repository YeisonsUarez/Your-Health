package co.edu.unab.hernandez.yeison.your_health.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Medico;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;
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
        if (medico.getFotoPerfil()!=null){
            //
            cargarImagenWebService(medico.getFotoPerfil(),viewHolder);
        }else{
            viewHolder.fotoDoc.setImageResource(R.drawable.camera);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miListener.onItemClick(medico,position);
            }
        });

    }
    private void cargarImagenWebService(String rutaImagen, final MedicosAdapter.ViewHolder holder) {
        String urlImagen=context.getString(R.string.urlBase,context.getString(R.string.nameServer))+rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.fotoDoc.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error al cargar la imagen",Toast.LENGTH_SHORT).show();
            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(imageRequest);
    }



    @Override
    public int getItemCount() {
        return medicos.size();
    }
    public interface onItemClickListener{ // Puede tener cualquier nombre
         void onItemClick(Medico medico, int posicion);

    }
}
