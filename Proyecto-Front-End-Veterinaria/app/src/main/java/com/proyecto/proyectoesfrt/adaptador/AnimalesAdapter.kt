package com.proyecto.proyectoesfrt.adaptador

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.proyectoesfrt.AnimalesDetallesActivity
import com.proyecto.proyectoesfrt.R
import com.proyecto.proyectoesfrt.entidad.Animal

class AnimalesAdapter(private val context: Context, private val lista: List<Animal>) :
    RecyclerView.Adapter<AnimalesAdapter.AnimalViewHolder>() {

    inner class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvApiCodigoAnimal: TextView = itemView.findViewById(R.id.tvApiCodigoAnimal)
        val tvApiNombreAnimal: TextView = itemView.findViewById(R.id.tvApiNombreAnimal)

        init {
            itemView.setOnClickListener {
                val animal = lista[adapterPosition]
                val intent = Intent(context, AnimalesDetallesActivity::class.java).apply {
                    putExtra("id", animal.id)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_animales, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = lista[position]
        holder.tvApiCodigoAnimal.text = animal.id.toString() // Muestra el código
        holder.tvApiNombreAnimal.text = animal.nombre // Muestra el nombre
    }

    override fun getItemCount(): Int = lista.size


}

