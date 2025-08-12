package com.proyecto.proyectoesfrt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.proyectoesfrt.adaptador.AnimalesAdapter
import com.proyecto.proyectoesfrt.api.RetrofitClient
import com.proyecto.proyectoesfrt.entidad.Animal
import kotlinx.coroutines.launch

class ListaAnimalesActivity : AppCompatActivity() {

    private lateinit var rvAnimales: RecyclerView
    private lateinit var btnNuevoAnimal: Button
    private lateinit var btnRegresar: Button

    companion object {
        private const val REQUEST_CODE = 100 // Código de solicitud para el Intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_animales_activity_main)

        // Configuración de márgenes para el contenido de la vista
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvAnimales = findViewById(R.id.rvAnimal)
        btnNuevoAnimal = findViewById(R.id.btnAgregarNuevoAnimal)
        btnRegresar = findViewById(R.id.btnRegresarDoctor)

        // Cargar datos desde la API
        cargarAnimalesDesdeApi()

        // Configurar el botón para agregar un nuevo animal
        btnNuevoAnimal.setOnClickListener {
            agregarNuevoAnimal()
        }

        btnRegresar.setOnClickListener {
            regresarAnimales()
        }
    }

    fun regresarAnimales(){
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)

    }
    private fun  agregarNuevoAnimal(){
        var intent= Intent(this,AgregarAnimalesActivity::class.java)
        startActivity(intent)
    }

    private fun cargarAnimalesDesdeApi() {
        // Realiza la solicitud para obtener la lista de animales
        lifecycleScope.launch {
            try {
                // Supón que tienes un método en tu cliente Retrofit para obtener todos los animales
                val response = RetrofitClient.apiService.getAllAnimals()  // Este método debe estar en tu cliente Retrofit
                if (response.isSuccessful && response.body() != null) {
                    // Si la respuesta es exitosa, obtenemos la lista de animales
                    val animales = response.body()!!

                    // Actualiza el RecyclerView con la lista de animales
                    actualizarRecyclerView(animales)
                } else {
                    Toast.makeText(this@ListaAnimalesActivity, "Error al cargar la lista de animales", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Maneja errores de red u otros problemas
                Toast.makeText(this@ListaAnimalesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Volver a cargar la lista de animales si se eliminó uno
            cargarAnimalesDesdeApi()
        }
    }

    // Método que actualiza el RecyclerView en la actividad
    private fun actualizarRecyclerView(animales: List<Animal>) {
        rvAnimales.layoutManager = LinearLayoutManager(this)
        rvAnimales.adapter = AnimalesAdapter(this, animales)
    }
}
