package com.proyecto.proyectoesfrt.entidad

import android.os.Parcel
import android.os.Parcelable

data class HistorialClinica(
    val id: Long? = null,
    val fechaDeRegistro: String,  // Usamos String en lugar de LocalDate
    val horaDeRegistro: String,   // Usamos String en lugar de LocalTime
    val diagnostico: String? = null,
    val tratamiento: String? = null,
    val vacunasAplicadas: String? = null,
    val observaciones: String? = null,
    var animal: Animal?,           // Cambié a Animal? porque puede ser null
    var cliente: Cliente?,         // Cambié a Cliente? porque puede ser null
    var medico: Medico?            // Cambié a Medico? porque puede ser null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as Long?,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Animal::class.java.classLoader),
        parcel.readParcelable(Cliente::class.java.classLoader),
        parcel.readParcelable(Medico::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(fechaDeRegistro)
        parcel.writeString(horaDeRegistro)
        parcel.writeString(diagnostico)
        parcel.writeString(tratamiento)
        parcel.writeString(vacunasAplicadas)
        parcel.writeString(observaciones)
        parcel.writeParcelable(animal, flags)
        parcel.writeParcelable(cliente, flags)
        parcel.writeParcelable(medico, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HistorialClinica> {
        override fun createFromParcel(parcel: Parcel): HistorialClinica {
            return HistorialClinica(parcel)
        }

        override fun newArray(size: Int): Array<HistorialClinica?> {
            return arrayOfNulls(size)
        }
    }
}
