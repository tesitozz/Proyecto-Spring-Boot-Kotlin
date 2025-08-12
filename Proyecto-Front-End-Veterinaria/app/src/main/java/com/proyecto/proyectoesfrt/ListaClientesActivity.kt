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
import com.proyecto.proyectoesfrt.adaptador.ClienteAdapter
import com.proyecto.proyectoesfrt.api.RetrofitClient
import com.proyecto.proyectoesfrt.entidad.Cliente
import kotlinx.coroutines.launch

class ListaClientesActivity : AppCompatActivity() {

    private lateinit var rvClientes: RecyclerView
    private lateinit var btnNuevoCliente: Button
    private lateinit var btnRegresaCliente: Button

    companion object {
        private const val REQUEST_CODE = 100 // Código de solicitud para el Intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_clientes_activity_main)

        // Configuración de márgenes para el contenido de la vista
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvClientes = findViewById(R.id.rvClientes)
        btnNuevoCliente = findViewById(R.id.btnAgregarNuevoCliente)
        btnRegresaCliente = findViewById(R.id.btnRegresarCliente)


        // Cargar datos desde la API
        cargarClienteDesdeApi()

        // Configurar el botón para agregar un nuevo animal
        btnNuevoCliente.setOnClickListener {
            agregarNuevoCliente()
        }

        btnRegresaCliente.setOnClickListener {
            regresarCliente()
        }
    }

    fun regresarCliente(){
        var intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun  agregarNuevoCliente(){
        //Cambiar esta parte
        var intent= Intent(this,AgregarClienteActivity::class.java)
        startActivity(intent)
    }

    private fun cargarClienteDesdeApi() {
        lifecycleScope.launch {
            try {
                // Asegúrate de usar el nombre correcto del servicio
                val response = RetrofitClient.apiServiceCliente.getAllClientes() // Llamada al API
                if (response.isSuccessful) {
                    val clientes = response.body() ?: emptyList() // Si la respuesta es exitosa, obtenemos la lista de clientes
                    configurarRecyclerView(clientes) // Configuramos el RecyclerView con los clientes
                } else {
                    Toast.makeText(this@ListaClientesActivity, "Error al cargar clientes", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ListaClientesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            cargarClienteDesdeApi()
        }
    }

    private fun configurarRecyclerView(clientes: List<Cliente>) {
        // Crear el adaptador
        val clienteAdapter = ClienteAdapter(this, clientes)

        // Configurar el RecyclerView
        rvClientes.layoutManager = LinearLayoutManager(this)
        rvClientes.adapter = clienteAdapter
    }

}
