package com.proyecto.proyectoesfrt.entidad

import android.os.Parcel
import android.os.Parcelable

data class Animal(
    var id: Long? = null,
    var nombre: String,
    var tipo: String,
    var genero: String,
    var edad: Int,
    var peso: Double,
    var raza: String? = null,
    var color: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        // Usando readValue para manejar Long? de forma más robusta
        parcel.readValue(Long::class.java.classLoader) as Long?,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        // Usando writeValue para manejar Long? nulo
        parcel.writeValue(id)
        parcel.writeString(nombre)
        parcel.writeString(tipo)
        parcel.writeString(genero)
        parcel.writeInt(edad)
        parcel.writeDouble(peso)
        parcel.writeString(raza)  // Puede ser null, se maneja sin problema
        parcel.writeString(color) // Puede ser null, se maneja sin problema
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Animal> {
            override fun createFromParcel(parcel: Parcel): Animal {
                return Animal(parcel)
            }

            override fun newArray(size: Int): Array<Animal?> {
                return arrayOfNulls(size)
            }
        }
    }
}
