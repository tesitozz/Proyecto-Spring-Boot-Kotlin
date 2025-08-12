package com.proyecto.proyectoesfrt.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.proyectoesfrt.R

class ViewMedico(vista: View): RecyclerView.ViewHolder(vista) {

    var tvApiCodigoDoctor : TextView
    var tvApiNombreDoctor : TextView
    var tvApiDNIDoctor : TextView

    init {
        tvApiCodigoDoctor = vista.findViewById(R.id.tvApiCodigoDoctor)
        tvApiNombreDoctor = vista.findViewById(R.id.tvApiNombreDoctor)
        tvApiDNIDoctor = vista.findViewById(R.id.tvApiDNIDoctor)
    }
}