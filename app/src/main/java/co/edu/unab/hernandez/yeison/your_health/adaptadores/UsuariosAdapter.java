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
import co.edu.unab.hernandez.yeison.your_health.modelos.Usuario;
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
        Glide.with(context).load(usuario.getFotoPerfil()).dontAnimate().placeholder(new ColorDrawable(Color.WHITE)).into(viewHolder.foto);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miListener.onItemClick(usuario,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
    public interface onItemClickListener{ // Puede tener cualquier nombre
        void onItemClick(Usuario usuario, int posicion);

    }
}
