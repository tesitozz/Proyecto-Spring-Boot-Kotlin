package com.proyecto.proyectoesfrt

import MedicoAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyecto.utils.ApiUtils
import com.proyecto.proyectoesfrt.entidad.Medico
import com.proyecto.proyectoesfrt.service.ApiServiceMedicos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ListaDoctoresActivity : AppCompatActivity() {

    private lateinit var rvDoctores: RecyclerView
    private lateinit var btnAgregarDoctor: Button
    private lateinit var btnRegresarDoctor: Button
    private lateinit var api: ApiServiceMedicos
    private lateinit var medicoAdapter: MedicoAdapter
    private var doctoresList: MutableList<Medico> = mutableListOf()

    companion object {
        private const val REQUEST_CODE = 100 // Código de solicitud para el Intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_doctores_activity_main)

        // Configuración de márgenes para el contenido de la vista
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvDoctores = findViewById(R.id.rvDoctores)
        btnAgregarDoctor = findViewById(R.id.btnAgregarDoctor)
        btnRegresarDoctor = findViewById(R.id.btnRegresarDoctor)

        // Configuración del RecyclerView y su adaptador
        rvDoctores.layoutManager = LinearLayoutManager(this)
        medicoAdapter = MedicoAdapter(this, doctoresList)
        rvDoctores.adapter = medicoAdapter
        // Cargar datos desde la API
        cargarDoctorDesdeApi()

        // Configurar el botón para agregar un nuevo animal
        btnAgregarDoctor.setOnClickListener {
            agregarNuevoDoctor()
        }

        btnRegresarDoctor.setOnClickListener {
            regresarDoctor()
        }

        api = ApiUtils.getApiDoctor()

    }

    fun regresarDoctor(){
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)

    }



    private fun  agregarNuevoDoctor(){
        //Cambiar esta parte
        var intent= Intent(this,AgregarDoctoresActivity::class.java)
        startActivity(intent)
    }

    private fun cargarDoctorDesdeApi() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response: Response<List<Medico>> = withContext(Dispatchers.IO) {
                    api.getAllMedicos() // Llamada suspendida
                }

                if (response.isSuccessful && response.body() != null) {
                    doctoresList.clear()
                    doctoresList.addAll(response.body()!!)
                    medicoAdapter.notifyDataSetChanged()
                } else {
                    // Manejo de errores si la respuesta no es exitosa
                }
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            cargarDoctorDesdeApi()
        }
    }
}
