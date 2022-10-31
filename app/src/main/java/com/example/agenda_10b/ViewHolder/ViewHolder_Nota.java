package com.example.agenda_10b.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agenda_10b.R;

public class ViewHolder_Nota extends RecyclerView.ViewHolder {

    View mView;

    private ViewHolder_Nota.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position); /*SE EJECUTA AL PRESIONAR EN EL ITEM*/

        void onItemLongClick(View view, int position); /*SE EJECUTA AL MANTENER PRESIONADO EN EL ITEM*/
    }

    public void setOnClickListener(ViewHolder_Nota.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public ViewHolder_Nota(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getBindingAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getBindingAdapterPosition());
                return false;
            }
        });
    }

    public void SetearDatos(Context context, String id_nota, String uid_usuario, String correo_usuario, String fecha_hora_registro, String titulo, String descripcion, String fecha_nota, String estado){

        //DECLARAR LAS VISTAS
        TextView Id_nota_Item, Uid_Usuario_Item, Correo_usuario_Item, Fecha_hora_registro_Item, Titulo_Item, Descripcion_Item, Fecha_Item, Estado_Item;

        //ESTABLECER LA CONEXIÓN CON EL ITEM
        Id_nota_Item = mView.findViewById(R.id.Id_nota_Item);
        Uid_Usuario_Item = mView.findViewById(R.id.Uid_Usuario_Item);
        Correo_usuario_Item = mView.findViewById(R.id.Correo_usuario_Item);
        Fecha_hora_registro_Item = mView.findViewById(R.id.Fecha_hora_registro_Item);
        Titulo_Item = mView.findViewById(R.id.Titulo_Item);
        Descripcion_Item = mView.findViewById(R.id.Descripcion_Item);
        Fecha_Item = mView.findViewById(R.id.Fecha_Item);
        Estado_Item = mView.findViewById(R.id.Estado_Item);

        //SETEAR LA INFORMACION DENTRO DEL ITEM
        Id_nota_Item.setText(id_nota);
        Uid_Usuario_Item.setText(uid_usuario);
        Correo_usuario_Item.setText(correo_usuario);
        Fecha_hora_registro_Item.setText(fecha_hora_registro);
        Titulo_Item.setText(titulo);
        Descripcion_Item.setText(descripcion);
        Fecha_Item.setText(fecha_nota);
        Estado_Item.setText(estado);
    }

}
