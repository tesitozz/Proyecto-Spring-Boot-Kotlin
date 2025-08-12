package com.proyecto.proyectoesfrt.entidad



data class Administrador(
    var id: Long? = null,
    var usuario: String,
    var correo: String,
    var password: String
)