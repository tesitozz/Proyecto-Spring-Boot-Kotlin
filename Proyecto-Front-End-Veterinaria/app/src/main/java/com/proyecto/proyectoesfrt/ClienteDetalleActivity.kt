package com.proyecto.proyectoesfrt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproyecto.utils.ApiUtils
import com.proyecto.proyectoesfrt.entidad.Cliente
import com.proyecto.proyectoesfrt.service.ApiServiceAnimal
import com.proyecto.proyectoesfrt.service.ApiServiceCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ClienteDetalleActivity : AppCompatActivity() {

    private lateinit var txtNombreClienteDetalles: TextView
    private lateinit var txtClienteApellidosDetalles: TextView
    private lateinit var txtDniClienteDetalles: TextView
    private lateinit var txtNumeroCelularAgregarDetalle: TextView
    private lateinit var txtCorreoClienteAgregarDetalle: TextView
    private lateinit var spnRegistrarGeneroClienteDetalle: AutoCompleteTextView // Cambiar a AutoCompleteTextView
    private lateinit var txtDireccionClienteAgregarDetalle:TextView
    private lateinit var btnRegresarCliente: Button
    private lateinit var btnRegistrarClienteModificar: Button
    private lateinit var btnClienteBorrarDetalle: Button

    //Apis
    private lateinit var api: ApiServiceCliente
    private var clienteId: Long = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.clientes_detalles_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas

        txtNombreClienteDetalles = findViewById(R.id.txtNombreClienteDetalles)
        txtClienteApellidosDetalles = findViewById(R.id.txtClienteApellidosDetalles)
        txtDniClienteDetalles = findViewById(R.id.txtDniClienteDetalles)
        txtNumeroCelularAgregarDetalle = findViewById(R.id.txtNumeroCelularAgregarDetalle)
        txtCorreoClienteAgregarDetalle = findViewById(R.id.txtCorreoClienteAgregarDetalle)
        spnRegistrarGeneroClienteDetalle = findViewById(R.id.spnRegistrarGeneroClienteDetalle)
        txtDireccionClienteAgregarDetalle = findViewById(R.id.txtDireccionClienteAgregarDetalle)
        btnRegresarCliente = findViewById(R.id.btnRegresarCliente)
        btnRegistrarClienteModificar = findViewById(R.id.btnRegistrarClienteModificar)
        btnClienteBorrarDetalle = findViewById(R.id.btnClienteBorrarDetalle)



        btnRegistrarClienteModificar.setOnClickListener { modificarCliente() }

        btnClienteBorrarDetalle.setOnClickListener { borrarCliente() }

        btnRegresarCliente.setOnClickListener { regresarCliente() }

        api = ApiUtils.getApiCliente()

        // Obtener los datos del cliente desde el Intent
        clienteId = intent.getLongExtra("id", 0) // Se obtiene el id del Intent
        val nombre = intent.getStringExtra("nombres")
        val apellidos = intent.getStringExtra("apellidos")
        val dni = intent.getStringExtra("dni")
        val genero = intent.getStringExtra("genero")
        val correo = intent.getStringExtra("correo")
        val celular = intent.getStringExtra("celular")
        val direccion = intent.getStringExtra("direccion")

        // Mostrar los datos en los campos
        txtNombreClienteDetalles.text = nombre ?: "Nombre no disponible"
        txtClienteApellidosDetalles.text = apellidos ?: "Apellidos no disponibles"
        txtDniClienteDetalles.text = dni ?: "DNI no disponible"
        txtNumeroCelularAgregarDetalle.text = celular ?: "Número no disponible"
        txtCorreoClienteAgregarDetalle.text = correo ?: "Correo no disponible"
        spnRegistrarGeneroClienteDetalle.setText(genero ?: "Género no disponible", false)
        txtDireccionClienteAgregarDetalle.text = direccion ?: "Dirección no disponible"

    }

    private fun modificarCliente() {
        // Obtener los nuevos valores de los campos editables
        val nuevoNombre = txtNombreClienteDetalles.text.toString()
        val nuevoApellido = txtClienteApellidosDetalles.text.toString()
        val nuevoDni = txtDniClienteDetalles.text.toString()
        val nuevoGenero = spnRegistrarGeneroClienteDetalle.text.toString()
        val nuevoCorreo = txtCorreoClienteAgregarDetalle.text.toString()
        val nuevoCelular = txtNumeroCelularAgregarDetalle.text.toString()
        val nuevaDireccion = txtDireccionClienteAgregarDetalle.text.toString()

        // Crear un objeto Cliente con los nuevos datos
        val clienteModificado = Cliente(
            id = clienteId,
            nombres = nuevoNombre,
            apellidos = nuevoApellido,
            dni = nuevoDni,
            genero = nuevoGenero,
            correo = nuevoCorreo,
            celular = nuevoCelular,
            direccion = nuevaDireccion
        )

        // Realizar la llamada a la API para modificar los datos
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<Cliente> = api.updateCliente(clienteModificado) // Asumiendo que tienes esta función en tu ApiService
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ClienteDetalleActivity, "Cliente modificado exitosamente", Toast.LENGTH_SHORT).show()

                        // Redirigir a la actividad ListaAnimalesActivity
                        val intent = Intent(this@ClienteDetalleActivity, ListaClientesActivity::class.java)
                        startActivity(intent) // Iniciar la actividad
                        finish() // Opcional: Terminar esta actividad si no necesitas volver
                    } else {
                        Toast.makeText(this@ClienteDetalleActivity, "Error al modificar el cliente", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ClienteDetalleActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    // Función para borrar el cliente
    private fun borrarCliente() {
        // Confirmación antes de eliminar
        AlertDialog.Builder(this)
            .setTitle("Eliminar cliente")
            .setMessage("¿Estás seguro de que deseas eliminar este cliente?")
            .setPositiveButton("Sí") { dialog, which ->
                // Realizar la llamada a la API para borrar el cliente
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response: Response<Void> = api.deleteCliente(clienteId) // Llamada DELETE para borrar el cliente
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@ClienteDetalleActivity, "Cliente eliminado exitosamente", Toast.LENGTH_SHORT).show()

                                // Redirigir a la actividad ListaAnimalesActivity después de eliminar el cliente
                                val intent = Intent(this@ClienteDetalleActivity, ListaClientesActivity::class.java)
                                startActivity(intent) // Iniciar la actividad
                                finish() // Opcional: Terminar esta actividad si no necesitas regresar a ella
                            } else {
                                Toast.makeText(this@ClienteDetalleActivity, "Error al eliminar el cliente", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ClienteDetalleActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("No", null)
            .show()
    }




    fun regresarCliente(){
        val intent = Intent(this, ListaClientesActivity::class.java)
        startActivity(intent)

    }

}
