package com.proyecto.proyectoesfrt.service

import com.proyecto.proyectoesfrt.entidad.Administrador
import com.proyecto.proyectoesfrt.entidad.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceLogeo {

    @POST("/api/administradores/login")
    fun login(@Body loginRequest: LoginRequest): Call<Administrador>


}

