package com.proyecto.proyectoesfrt.entidad

import android.os.Parcel
import android.os.Parcelable

data class Medico(
    var id: Long? = null,
    var nombres: String,
    var apellidos: String,
    var dni: String,
    var genero: String?,
    var correo: String?,
    var celular: String,
    var especialidad: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        // Manejo de valores nulos en Long?
        parcel.readValue(Long::class.java.classLoader) as Long?,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        // Se maneja el Long? correctamente (id puede ser nulo)
        parcel.writeValue(id)
        parcel.writeString(nombres)
        parcel.writeString(apellidos)
        parcel.writeString(dni)
        parcel.writeString(genero)
        parcel.writeString(correo)
        parcel.writeString(celular)
        parcel.writeString(especialidad)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Medico> {
            override fun createFromParcel(parcel: Parcel): Medico = Medico(parcel)
            override fun newArray(size: Int): Array<Medico?> = arrayOfNulls(size)
        }
    }
}
