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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.edu.unab.hernandez.yeison.your_health.R;
import co.edu.unab.hernandez.yeison.your_health.modelos.Usuario;
import co.edu.unab.hernandez.yeison.your_health.modelos.VolleySingleton;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsuariosAdapter extends RecyclerView.Adapter {
    ArrayList<Usuario> usuarios;
    onItemClickListener miListener;
    Context context;

    public UsuariosAdapter(ArrayList<Usuario> usuarios, onItemClickListener miListener, Context context) {
        this.usuarios = usuarios;
        this.miListener = miListener;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre,tipoNUmeroDoc,correo,telefono;
        CircleImageView foto ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          foto= itemView.findViewById(R.id.imageUser);
          nombre= itemView.findViewById(R.id.nombreUsuario);
          correo= itemView.findViewById(R.id.correoUsuario);
          telefono= itemView.findViewById(R.id.telefonoUsuarioU);
          tipoNUmeroDoc= itemView.findViewById(R.id.cedulayNumeroUsuario);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemusuario,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder =(ViewHolder)holder;
        final Usuario usuario= usuarios.get(position);
        viewHolder.nombre.setText(usuario.getNombreUsuario());
        viewHolder.tipoNUmeroDoc.setText(usuario.getTipoDocumento()+" "+usuario.getNumeroDocumento());
        viewHolder.correo.setText(usuario.getCorreoUsuario());
        viewHolder.telefono.setText(usuario.getTelefono());
        if (usuario.getFotoPerfil()!=null){
            //
            cargarImagenWebService(usuario.getFotoPerfil(),viewHolder);
        }else{
            viewHolder.foto.setImageResource(R.drawable.camera);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miListener.onItemClick(usuario,position);
            }
        });

    }

    private void cargarImagenWebService(String rutaImagen, final UsuariosAdapter.ViewHolder holder) {
        String urlImagen=context.getString(R.string.urlBase,context.getString(R.string.nameServer))+rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.foto.setImageBitmap(response);
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
        return usuarios.size();
    }
    public interface onItemClickListener{ // Puede tener cualquier nombre
        void onItemClick(Usuario usuario, int posicion);

    }
}
