package com.proyecto.proyectoesfrt.adaptador

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.proyectoesfrt.HistorialDetalleActivity
import com.proyecto.proyectoesfrt.R
import com.proyecto.proyectoesfrt.entidad.HistorialClinica

class HistorialAdapter(private val context: Context, private val lista: MutableList<HistorialClinica>) :
    RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    inner class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvApiNombreAnimalItem: TextView = itemView.findViewById(R.id.tvApiNombreAnimalItem)
        val tvApiNombreClienteItem: TextView = itemView.findViewById(R.id.tvApiNombreClienteItem)

        init {
            itemView.setOnClickListener {
                val historial = lista[adapterPosition]
                val intent = Intent(context, HistorialDetalleActivity::class.java).apply {
                    // Usamos putExtra para pasar el objeto HistorialClinica como Parcelable
                    putExtra("historial", historial)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val historial = lista[position]

        // Manejo de valores nulos: Se asegura de que no haya nulls
        val nombreAnimal = historial.animal?.nombre ?: "Nombre no disponible"  // Valor predeterminado si es null
        val nombreCliente = "${historial.cliente?.nombres ?: "Nombre no disponible"} ${historial.cliente?.apellidos ?: "Apellido no disponible"}"  // Valor predeterminado si es null

        // Asignar los valores a los TextView
        holder.tvApiNombreAnimalItem.text = nombreAnimal
        holder.tvApiNombreClienteItem.text = nombreCliente
    }

    override fun getItemCount(): Int {
        return lista.size // Tamaño de la lista mutable
    }

    // Método para actualizar la lista cuando recibes nuevos datos
    fun actualizarLista(nuevaLista: List<HistorialClinica>) {
        lista.clear() // Limpiar la lista actual
        lista.addAll(nuevaLista) // Agregar los nuevos datos
        notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
    }
}
