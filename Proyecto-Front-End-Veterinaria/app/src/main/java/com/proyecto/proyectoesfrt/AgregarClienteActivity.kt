package com.proyecto.proyectoesfrt

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appproyecto.utils.ApiUtils
import com.proyecto.proyectoesfrt.entidad.Cliente
import com.proyecto.proyectoesfrt.service.ApiServiceCliente
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class AgregarClienteActivity : AppCompatActivity() {

    private lateinit var txtNombreClienteAgregar:TextView

    private lateinit var txtClienteApellidosAgregar:TextView

    private lateinit var txtDniClienteAgregar:TextView

    private lateinit var spnGeneroClienteAgregar:AutoCompleteTextView

    private lateinit var txtCorreoClienteAgregar:TextView

    private lateinit var txtNumeroCelularAgregar: TextView

    private lateinit var txtDireccionClienteAgregar:TextView

    private lateinit var btnRegistrarClienteAgregar:Button

    private lateinit var btnRegresarClienteAgregar:Button



    //Api
    private lateinit var api: ApiServiceCliente


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.clientes_agregar_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtNombreClienteAgregar = findViewById(R.id.txtNombreClienteAgregar)
        txtClienteApellidosAgregar = findViewById(R.id.txtClienteApellidosAgregar)
        txtDniClienteAgregar = findViewById(R.id.txtDniClienteAgregar)
        spnGeneroClienteAgregar = findViewById(R.id.spnRegistrarGeneroCliente)
        txtCorreoClienteAgregar = findViewById(R.id.txtCorreoClienteAgregar)
        txtNumeroCelularAgregar = findViewById(R.id.txtNumeroCelularAgregar)
        txtDireccionClienteAgregar = findViewById(R.id.txtDireccionClienteAgregar)
        btnRegistrarClienteAgregar = findViewById(R.id.btnRegistrarClienteAgregar)
        btnRegresarClienteAgregar = findViewById(R.id.btnRegresarClienteAgregar)

        api = ApiUtils.getApiCliente()  // Este es el método donde llamas a getApiCliente()

        btnRegistrarClienteAgregar.setOnClickListener { registrarCliente() }

        btnRegresarClienteAgregar.setOnClickListener { regresarCliente() }

    }

    fun registrarCliente() {
        // Validación de campos
        val nombre = txtNombreClienteAgregar.text.toString().trim()
        val apellidos = txtClienteApellidosAgregar.text.toString().trim()
        val dni = txtDniClienteAgregar.text.toString().trim()
        val genero = spnGeneroClienteAgregar.text.toString().trim()
        val correo = txtCorreoClienteAgregar.text.toString().trim()
        val celular = txtNumeroCelularAgregar.text.toString().trim()
        val direccion = txtDireccionClienteAgregar.text.toString().trim()

        if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || celular.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear un objeto Cliente
        val cliente = Cliente(
            nombres = nombre,
            apellidos = apellidos,
            dni = dni,
            genero = genero.takeIf { it.isNotEmpty() },
            correo = correo.takeIf { it.isNotEmpty() },
            celular = celular,
            direccion = direccion.takeIf { it.isNotEmpty() }
        )

        // Realizar la llamada a la API para registrar el cliente
        lifecycleScope.launch {
            try {
                val response = api.createCliente(cliente)
                if (response.isSuccessful) {
                    // Si el registro fue exitoso
                    Toast.makeText(this@AgregarClienteActivity, "Cliente registrado con éxito", Toast.LENGTH_SHORT).show()
                    // Volver a la actividad principal o lista de clientes
                    regresarCliente()
                } else {
                    // Si la respuesta fue un error
                    Toast.makeText(this@AgregarClienteActivity, "Error al registrar cliente", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Manejo de excepciones (como problemas de red)
                Toast.makeText(this@AgregarClienteActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para regresar a la pantalla anterior
    fun regresarCliente() {
        val intent = Intent(this, ListaClientesActivity::class.java)
        startActivity(intent)
    }



}