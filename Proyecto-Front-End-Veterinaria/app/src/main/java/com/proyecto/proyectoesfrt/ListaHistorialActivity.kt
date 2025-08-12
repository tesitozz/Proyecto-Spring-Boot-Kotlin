package com.proyecto.proyectoesfrt

import MedicoAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyecto.utils.ApiUtils
import com.proyecto.proyectoesfrt.adaptador.HistorialAdapter
import com.proyecto.proyectoesfrt.entidad.HistorialClinica
import com.proyecto.proyectoesfrt.entidad.Medico
import com.proyecto.proyectoesfrt.service.ApiServiceMedicos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ListaHistorialActivity : AppCompatActivity() {

    private lateinit var btnAgregarNuevaHistorialMedico: Button
    private lateinit var btnRegresarHistorialMedico: Button
    private lateinit var rvHistorialMedico: RecyclerView
    private lateinit var historialAdapter: HistorialAdapter

    private val historialClinicas = mutableListOf<HistorialClinica>() // Lista mutable para los historiales

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_historial_activity_main)

        // Configuración de márgenes para el contenido de la vista
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de vistas
        rvHistorialMedico = findViewById(R.id.rvHistorialMedico)
        btnAgregarNuevaHistorialMedico = findViewById(R.id.btnAgregarNuevaHistorialMedico)
        btnRegresarHistorialMedico = findViewById(R.id.btnRegresarHistorialMedico)

        // Configuración del RecyclerView
        rvHistorialMedico.layoutManager = LinearLayoutManager(this)
        historialAdapter = HistorialAdapter(this, historialClinicas)
        rvHistorialMedico.adapter = historialAdapter

        // Eventos de botones
        btnAgregarNuevaHistorialMedico.setOnClickListener { agregarNuevoHistorialMedico() }
        btnRegresarHistorialMedico.setOnClickListener { regresarHistorialMedico() }

        // Obtener los historiales médicos desde la API
        obtenerHistoriasClinicas()
    }

    // Función para agregar un nuevo historial
    fun agregarNuevoHistorialMedico() {
        val intent = Intent(this, AgregarHistorialActivity::class.java)
        startActivity(intent)
    }

    // Función para regresar a la actividad de historial
    fun regresarHistorialMedico() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    // Función para obtener las historias clínicas de la API
    private fun obtenerHistoriasClinicas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<List<HistorialClinica>> = ApiUtils.getApiHistorial().getAllHistoriaClinica().execute()
                if (response.isSuccessful) {
                    response.body()?.let { historias ->
                        withContext(Dispatchers.Main) {
                            // Usar el método `actualizarLista()` del adaptador para actualizar los datos
                            historialAdapter.actualizarLista(historias)
                        }
                    }
                } else {
                    // Manejo de errores
                    Log.e("API_ERROR", "Error en la respuesta de la API")
                }
            } catch (e: Exception) {
                // Manejo de excepciones
                e.printStackTrace()
            }
        }
    }

}
