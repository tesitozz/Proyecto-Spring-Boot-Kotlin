package com.proyecto.proyectoesfrt

import android.content.Intent
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
import com.proyecto.proyectoesfrt.entidad.Medico
import com.proyecto.proyectoesfrt.service.ApiServiceMedicos
import kotlinx.coroutines.launch

class AgregarDoctoresActivity : AppCompatActivity() {

    private lateinit var txtNombreDoctoresRegistrar : TextView
    private lateinit var txtDoctoresApellidosRegistrar : TextView
    private lateinit var txtDNIDoctoresRegistrar : TextView
    private lateinit var txtCorreoDoctoresRegistrar:TextView
    private lateinit var txtTelefonoDoctoresRegistrar:TextView
    private lateinit var spnGeneroMedicoRegistrar:AutoCompleteTextView
    private lateinit var spnEspecialidadDocRegistrar : AutoCompleteTextView
    private lateinit var btnGrabarDoctorRegistrar : Button
    private lateinit var btnRegresarDoctorRegistrar  : Button


    //Api
    private lateinit var api: ApiServiceMedicos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.doctores_agregar_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtNombreDoctoresRegistrar=findViewById(R.id.txtNombreDoctoresRegistrar)
        txtDoctoresApellidosRegistrar=findViewById(R.id.txtDoctoresApellidosRegistrar)
        txtDNIDoctoresRegistrar=findViewById(R.id.txtDNIDoctoresRegistrar)
        txtCorreoDoctoresRegistrar=findViewById(R.id.txtCorreoDoctoresRegistrar)
        txtTelefonoDoctoresRegistrar=findViewById(R.id.txtTelefonoDoctoresRegistrar)
        spnGeneroMedicoRegistrar=findViewById(R.id.spnGeneroMedicoRegistrar)
        spnEspecialidadDocRegistrar=findViewById(R.id.spnEspecialidadDocRegistrar)
        btnGrabarDoctorRegistrar = findViewById(R.id.btnGrabarDoctorRegistrar)
        btnRegresarDoctorRegistrar = findViewById(R.id.btnRegresarDoctorRegistrar)

        api = ApiUtils.getApiDoctor()

        btnGrabarDoctorRegistrar.setOnClickListener { registrarMedico() }
        btnRegresarDoctorRegistrar.setOnClickListener { regresarMedico() }

    }

    fun registrarMedico() {
        val nombre = txtNombreDoctoresRegistrar.text.toString()
        val apellidos = txtDoctoresApellidosRegistrar.text.toString()
        val dni = txtDNIDoctoresRegistrar.text.toString()
        val correo = txtCorreoDoctoresRegistrar.text.toString()
        val celular = txtTelefonoDoctoresRegistrar.text.toString()
        val genero = spnGeneroMedicoRegistrar.text.toString()
        val especialidad = spnEspecialidadDocRegistrar.text.toString()

        // Validate the inputs
        if (nombre.isBlank() || apellidos.isBlank() || dni.isBlank() || correo.isBlank() || celular.isBlank() || genero.isBlank() || especialidad.isBlank()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val medico = Medico(
            nombres = nombre,
            apellidos = apellidos,
            dni = dni,
            genero = genero,
            correo = correo,
            celular = celular,
            especialidad = especialidad
        )

        // Make the API call to register the doctor
        lifecycleScope.launch {
            try {
                val response = api.createMedico(medico)

                if (response.isSuccessful) {
                    Toast.makeText(this@AgregarDoctoresActivity, "Médico registrado correctamente", Toast.LENGTH_SHORT).show()
                    // Redirect to the list of doctors
                    regresarMedico()
                } else {
                    Toast.makeText(this@AgregarDoctoresActivity, "Error al registrar el médico: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AgregarDoctoresActivity, "Error en la conexión: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }



    fun regresarMedico(){
        val intent = Intent(this, ListaDoctoresActivity::class.java)
        startActivity(intent)

    }
}