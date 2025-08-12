        package com.proyecto.proyectoesfrt.api

        import com.google.gson.GsonBuilder
        import com.proyecto.proyectoesfrt.adaptador.LocalDateAdapter
        import com.proyecto.proyectoesfrt.adaptador.LocalTimeAdapter
        import okhttp3.OkHttpClient
        import okhttp3.logging.HttpLoggingInterceptor
        import retrofit2.Retrofit
        import retrofit2.converter.gson.GsonConverterFactory
        import com.proyecto.proyectoesfrt.service.ApiServiceAnimal
        import com.proyecto.proyectoesfrt.service.ApiServiceCliente
        import com.proyecto.proyectoesfrt.service.ApiServiceHistorial
        import com.proyecto.proyectoesfrt.service.ApiServiceMedicos
        import com.proyecto.proyectoesfrt.service.ApiServiceLogeo
        import java.time.LocalDate
        import java.time.LocalTime
        import java.util.concurrent.TimeUnit

        object RetrofitClient {
            private const val BASE_URL = "http://10.0.2.2:8080/" // URL base para tu API

            // Configurar Gson para manejar LocalDate y LocalTime
            private val gson = GsonBuilder()
                .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())  // Registrar el adaptador de LocalDate
                .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())  // Registrar el adaptador de LocalTime
                .create()

            // Configuración de OkHttpClient para agregar logs y tiempo de espera
            private val client: OkHttpClient by lazy {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY // Cambia a NONE para desactivar logs
                }

                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS) // Tiempo de conexión
                    .readTimeout(30, TimeUnit.SECONDS)    // Tiempo de lectura
                    .writeTimeout(30, TimeUnit.SECONDS)   // Tiempo de escritura
                    .build()
            }

            // Instancia de Retrofit
            private val retrofit: Retrofit by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // Asignar cliente OkHttp configurado
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Usar Gson con adaptadores
                    .build()
            }

            // Servicio para animales
            val apiService: ApiServiceAnimal by lazy {
                retrofit.create(ApiServiceAnimal::class.java)
            }

            // Servicio para clientes
            val apiServiceCliente: ApiServiceCliente by lazy {
                retrofit.create(ApiServiceCliente::class.java)
            }

            // Servicio para médicos
            val apiServiceDoctor: ApiServiceMedicos by lazy {
                retrofit.create(ApiServiceMedicos::class.java)
            }

            // Servicio para logeo
            val apiLogeoUsuario: ApiServiceLogeo by lazy {
                retrofit.create(ApiServiceLogeo::class.java)
            }

            // Servicio para historial clínico
            val apiServiceHistorial: ApiServiceHistorial by lazy {
                retrofit.create(ApiServiceHistorial::class.java)
            }
        }
