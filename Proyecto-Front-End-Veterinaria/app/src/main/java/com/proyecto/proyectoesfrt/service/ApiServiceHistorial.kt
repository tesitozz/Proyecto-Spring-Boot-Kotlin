package com.proyecto.proyectoesfrt.service

import android.telecom.Call
import com.proyecto.proyectoesfrt.entidad.Animal
import com.proyecto.proyectoesfrt.entidad.Cliente
import com.proyecto.proyectoesfrt.entidad.HistorialClinica
import com.proyecto.proyectoesfrt.entidad.Medico
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.time.LocalDate
import java.time.LocalTime

interface ApiServiceHistorial {

    @GET("/api/historias-clinicas/listar")
    fun getAllHistoriaClinica(): retrofit2.Call<List<HistorialClinica>>

    @POST("/api/historias-clinicas/crear")
    suspend fun createHistoriaClinica(@Body historiaClinica: HistorialClinica): Response<HistorialClinica>


    // Método para actualizar una historia clínica
    @PUT("/api/historias-clinicas/actualizar")
    fun updateHistoriaClinica(@Body historiaClinica: HistorialClinica): retrofit2.Call<HistorialClinica>

    @DELETE("/api/historias-clinicas/eliminar-por-id/{id}")
    fun deleteHistoriaClinica(@Path("id") id: Long): retrofit2.Call<Void>


}