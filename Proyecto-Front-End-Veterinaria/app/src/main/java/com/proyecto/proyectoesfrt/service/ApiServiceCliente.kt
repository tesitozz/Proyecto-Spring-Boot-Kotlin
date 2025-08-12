package com.proyecto.proyectoesfrt.service

import com.proyecto.proyectoesfrt.entidad.Animal
import com.proyecto.proyectoesfrt.entidad.Cliente
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceCliente {
    @GET("/api/clientes/listar")
    suspend fun getAllClientes(): Response<List<Cliente>>

    @POST("/api/clientes/crear")
    suspend fun createCliente(@Body cliente: Cliente): Response<Cliente>

    @PUT("/api/clientes/actualizar")
    suspend fun updateCliente(@Body cliente: Cliente): Response<Cliente>

    @GET("/api/clientes/buscar-por-id/{id}")
    suspend fun getClienteById(@Path("id") id: Long): Response<Cliente>

    @DELETE("/api/clientes/eliminar-por-id/{id}")
    suspend fun deleteCliente(@Path("id") id: Long): Response<Void>

    @GET("/api/clientes/buscar-por-dni/{dni}")
    suspend fun getClienteByDni(@Path("dni") dni: String): Response<Cliente>

    @GET("/api/clientes/buscar-por-nombre/{nombres}")
    suspend fun findByNombresCli(@Path("nombres") name: String): Response<List<Cliente>>


}