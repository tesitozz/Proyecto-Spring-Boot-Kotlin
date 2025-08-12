package com.proyecto.proyectoesfrt.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.proyectoesfrt.R

class ViewHistorial(vista: View): RecyclerView.ViewHolder(vista) {

    var tvApiNombreAnimalItem : TextView
    var tvApiNombreClienteItem : TextView

    init {
        tvApiNombreAnimalItem = vista.findViewById(R.id.tvApiNombreAnimalItem)
        tvApiNombreClienteItem = vista.findViewById(R.id.tvApiNombreClienteItem)
    }
}