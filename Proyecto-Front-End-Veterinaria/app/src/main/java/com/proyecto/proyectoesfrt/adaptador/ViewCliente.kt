package com.proyecto.proyectoesfrt.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.proyectoesfrt.R

class ViewCliente(vista:View):RecyclerView.ViewHolder(vista) {

    //Atributos que irian en el Item_Cliente

    var tvApiCodigoCliente:TextView
    var tvApiNombreCliente:TextView
    var tvApiDniCliente:TextView

    init {
        tvApiCodigoCliente=vista.findViewById(R.id.tvApiCodigoCliente)
        tvApiNombreCliente=vista.findViewById(R.id.tvApiNombreCliente)
        tvApiDniCliente=vista.findViewById(R.id.tvApiDniCliente)


    }

}