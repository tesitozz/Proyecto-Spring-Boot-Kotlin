package com.proyecto.proyectoesfrt.service

import com.proyecto.proyectoesfrt.entidad.Animal
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceAnimal {
    @GET("/api/animales/listar")
    suspend fun getAllAnimals(): Response<List<Animal>>

    @POST("/api/animales/crear")
    suspend fun createAnimal(@Body animal: Animal): Response<Animal>

    @PUT("/api/animales/actualizar")
    suspend fun updateAnimal(@Body animal: Animal): Response<Animal>

    @GET("/api/animales/buscar-por-id/{id}")
    suspend fun getAnimalById(@Path("id") id: Long): Response<Animal>

    @DELETE("/api/animales/eliminar-por-id/{id}")
    suspend fun deleteAnimal(@Path("id") id: Long): Response<Void>

    @GET("/api/animales/buscar-por-nombre/{nombre}")
    suspend fun getAnimalsByName(@Path("nombre") name: String): Response<List<Animal>>

}