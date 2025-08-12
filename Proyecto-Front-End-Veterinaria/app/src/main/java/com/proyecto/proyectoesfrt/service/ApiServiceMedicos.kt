package com.proyecto.proyectoesfrt.service

import com.proyecto.proyectoesfrt.entidad.Medico
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceMedicos {

    @GET("/api/medicos/listar")
    suspend fun getAllMedicos(): Response<List<Medico>>

    @POST("/api/medicos/crear")
    suspend fun createMedico(@Body medico: Medico): Response<Medico>

    @PUT("/api/medicos/actualizar")
    suspend fun updateMedico(@Body medico: Medico): Response<Medico>

    @GET("/api/medicos/buscar-por-id/{id}")
    suspend fun getMedicoById(@Path("id") id: Long): Response<Medico>

    @DELETE("/api/medicos/eliminar-por-id/{id}")
    suspend fun deleteMedico(@Path("id") id: Long): Response<Void>

    @GET("/api/medicos/buscar-por-dni/{dni}")
    suspend fun getMedicoByDni(@Path("dni") dni: String): Response<Medico>


}