package com.proyecto.proyectoesfrt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.proyecto.proyectoesfrt.api.RetrofitClient
import com.proyecto.proyectoesfrt.entidad.Administrador
import com.proyecto.proyectoesfrt.entidad.LoginRequest
import retrofit2.Call
import retrofit2.Response

class Inicio_Sesion_Activity : AppCompatActivity() {

    private lateinit var btnUsuarioLogin: Button
    private lateinit var txtUsuarioLogin: TextInputEditText
    private lateinit var txtClave: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.inicio_sesion_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnUsuarioLogin=findViewById(R.id.btnUsuarioLogin)
        txtUsuarioLogin=findViewById(R.id.txtUsuarioLogin)
        txtClave=findViewById(R.id.txtClave)

        btnUsuarioLogin.setOnClickListener { logearse() }

    }


    fun logearse() {
        // Obtener los valores ingresados por el usuario
        val usuario = txtUsuarioLogin.text.toString().trim()
        val password = txtClave.text.toString().trim()

        // Validar que los campos no estén vacíos
        when {
            usuario.isEmpty() -> {
                txtUsuarioLogin.error = "Por favor, ingrese su usuario"
                txtUsuarioLogin.requestFocus()
            }
            password.isEmpty() -> {
                txtClave.error = "Por favor, ingrese su contraseña"
                txtClave.requestFocus()
            }
            else -> {
                // Crear el objeto LoginRequest
                val loginRequest = LoginRequest(usuario, password)

                // Llamar al servicio de login
                val apiService = RetrofitClient.apiLogeoUsuario
                val call = apiService.login(loginRequest)

                call.enqueue(object : retrofit2.Callback<Administrador> {
                    override fun onResponse(call: Call<Administrador>, response: Response<Administrador>) {
                        if (response.isSuccessful) {
                            // Login exitoso
                            val administrador = response.body()
                            Log.d("Login", "Usuario autenticado: ${administrador?.usuario}")

                            // Redirigir a la siguiente actividad
                            val intent = Intent(this@Inicio_Sesion_Activity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Manejar errores de autenticación
                            mostrarError("Credenciales inválidas. Por favor, intente de nuevo.")
                            Log.e("Login", "Error de autenticación: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<Administrador>, t: Throwable) {
                        // Manejar errores de conexión u otros problemas
                        mostrarError("Error al conectar con el servidor: ${t.message}")
                        Log.e("Login", "Error de conexión", t)
                    }
                })
            }
        }
    }



    private fun mostrarError(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show()
    }

}