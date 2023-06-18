package com.example.servicioweb4

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class AdaptadorElementos(val ListaElementos:ArrayList<Usuarios>): RecyclerView.Adapter<AdaptadorElementos.ViewHolder>() {

    override fun getItemCount(): Int {
        return ListaElementos.size;
    }
    class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val fUsuario = itemView.findViewById<TextView>(R.id.elemento_nombre)
        val fEstado = itemView.findViewById<TextView>(R.id.elemento_duracion)

        //set the onclick listener for the singlt list item
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.fUsuario?.text=ListaElementos[position].usuario
        holder?.fEstado?.text=ListaElementos[position].estado
        var id = ListaElementos[position].id
        var usuario = ListaElementos[position].usuario
        var clave = ListaElementos[position].clave
        var estado = ListaElementos[position].estado


        holder.itemView.setOnClickListener(){
            var llamaractividad = Intent(holder.itemView.context, verPelicula::class.java)
            llamaractividad.putExtra("id", id.toString())
            llamaractividad.putExtra("usuario", usuario.toString())
            llamaractividad.putExtra("clave", clave.toString())
            llamaractividad.putExtra("estado", estado.toString())
            holder.itemView.context.startActivity(llamaractividad)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementoslista, parent, false);
        return ViewHolder(v);
    }
}