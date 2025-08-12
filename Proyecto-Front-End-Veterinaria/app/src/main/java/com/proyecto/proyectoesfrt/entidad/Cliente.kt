        package com.proyecto.proyectoesfrt.entidad

        import android.os.Parcel
        import android.os.Parcelable

        data class Cliente(
            var id: Long? = null,
            var nombres: String,
            var apellidos: String,
            var dni: String,
            var genero: String? = null, // Campo opcional
            var correo: String? = null, // Campo opcional
            var celular: String,
            var direccion: String? = null // Campo opcional
        ) : Parcelable {
            constructor(parcel: Parcel) : this(
                parcel.readValue(Long::class.java.classLoader) as Long?,
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString(),
                parcel.readString(),
                parcel.readString() ?: "",
                parcel.readString()
            )

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeValue(id)
                parcel.writeString(nombres)
                parcel.writeString(apellidos)
                parcel.writeString(dni)
                parcel.writeString(genero)
                parcel.writeString(correo)
                parcel.writeString(celular)
                parcel.writeString(direccion)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<Cliente> {
                override fun createFromParcel(parcel: Parcel): Cliente {
                    return Cliente(parcel)
                }

                override fun newArray(size: Int): Array<Cliente?> {
                    return arrayOfNulls(size)
                }
            }
        }
