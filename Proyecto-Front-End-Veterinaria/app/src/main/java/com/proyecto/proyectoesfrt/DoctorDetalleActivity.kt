package com.proyecto.proyectoesfrt

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproyecto.utils.ApiUtils
import com.proyecto.proyectoesfrt.entidad.Medico
import com.proyecto.proyectoesfrt.service.ApiServiceMedicos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class DoctorDetalleActivity : AppCompatActivity() {

    private lateinit var txtCodigoDoctorActualizar: TextView
    private lateinit var txtNombreDoctoresActualizar: TextView
    private lateinit var txtDoctoresApellidosActualizar: TextView
    private lateinit var txtDNIDoctoresActualizar: TextView
    private lateinit var txtCorreoDoctoresDetalles: TextView
    private lateinit var txtTelefonoDetallesModificar: TextView
    private lateinit var spnGeneroMedicoDetalles: AutoCompleteTextView
    private lateinit var spnEspecialidadDocActualizar: AutoCompleteTextView
    private lateinit var btnActualizarDoctor: Button
    private lateinit var btnBorrarDoctor: Button
    private lateinit var btnRegresarDoctor: Button

    private lateinit var api: ApiServiceMedicos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.doctores_detalles_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar campos
        txtCodigoDoctorActualizar = findViewById(R.id.txtCodigoDoctorActualizar)
        txtNombreDoctoresActualizar = findViewById(R.id.txtNombreDoctoresActualizar)
        txtDoctoresApellidosActualizar = findViewById(R.id.txtDoctoresApellidosActualizar)
        txtDNIDoctoresActualizar = findViewById(R.id.txtDNIDoctoresActualizar)
        txtCorreoDoctoresDetalles = findViewById(R.id.txtCorreoDoctoresDetalles)
        txtTelefonoDetallesModificar = findViewById(R.id.txtTelefonoDetallesModificar)
        spnGeneroMedicoDetalles = findViewById(R.id.spnGeneroMedicoDetalles)
        spnEspecialidadDocActualizar = findViewById(R.id.spnEspecialidadDocActualizar)
        btnActualizarDoctor = findViewById(R.id.btnActualizarDoctor)
        btnBorrarDoctor = findViewById(R.id.btnBorrarDoctor)
        btnRegresarDoctor = findViewById(R.id.btnRegresarDoctor)


        // Inicializar la API
        api = ApiUtils.getApiDoctor()

        // Obtener el ID del doctor desde el Intent
        val doctorId = intent.getLongExtra("doctorId", -1)

        if (doctorId != -1L) {
            obtenerDetallesDoctor(doctorId)
        } else {
            Toast.makeText(this, "ID de doctor no válido", Toast.LENGTH_SHORT).show()
        }

        // Configurar botones
        btnRegresarDoctor.setOnClickListener { regresarDoctor() }
        btnActualizarDoctor.setOnClickListener { modificarDoctor() }
        btnBorrarDoctor.setOnClickListener { borrarDoctor() }
    }

    // Función para obtener los detalles del doctor desde la API
    private fun obtenerDetallesDoctor(doctorId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Hacer la solicitud a la API para obtener los detalles del doctor
                val response = api.getMedicoById(doctorId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val doctor = response.body()
                        if (doctor != null) {
                            // Mostrar los datos del doctor en los campos correspondientes
                            txtCodigoDoctorActualizar.text = doctor.id?.toString() ?: ""
                            txtNombreDoctoresActualizar.text = doctor.nombres
                            txtDoctoresApellidosActualizar.text = doctor.apellidos
                            txtDNIDoctoresActualizar.text = doctor.dni
                            txtCorreoDoctoresDetalles.text = doctor.correo
                            txtTelefonoDetallesModificar.text = doctor.celular
                            spnGeneroMedicoDetalles.setText(doctor.genero, false)
                            spnEspecialidadDocActualizar.setText(doctor.especialidad, false)
                        } else {
                            Toast.makeText(this@DoctorDetalleActivity, "No se encontraron detalles del doctor", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@DoctorDetalleActivity, "Error al obtener detalles del doctor", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DoctorDetalleActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun modificarDoctor() {
        // Obtener los datos desde los campos de texto
        val id = txtCodigoDoctorActualizar.text.toString().toLongOrNull()
        val nombre = txtNombreDoctoresActualizar.text.toString()
        val apellidos = txtDoctoresApellidosActualizar.text.toString()
        val dni = txtDNIDoctoresActualizar.text.toString()
        val correo = txtCorreoDoctoresDetalles.text.toString()
        val telefono = txtTelefonoDetallesModificar.text.toString()
        val genero = spnGeneroMedicoDetalles.text.toString()
        val especialidad = spnEspecialidadDocActualizar.text.toString()

        if (id != null) {
            // Crear un objeto Medico con los datos actuales
            val medico = Medico(
                id = id,
                nombres = nombre,
                apellidos = apellidos,
                dni = dni,
                genero = genero,
                correo = correo,
                celular = telefono,
                especialidad = especialidad
            )

            // Llamar a la API para actualizar el doctor
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = api.updateMedico(medico)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@DoctorDetalleActivity, "Doctor actualizado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@DoctorDetalleActivity, "Error al actualizar doctor", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@DoctorDetalleActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "ID no válido", Toast.LENGTH_SHORT).show()
        }
    }


    fun borrarDoctor() {
        val id = txtCodigoDoctorActualizar.text.toString().toLongOrNull()

        if (id != null) {
            // Llamar a la API para eliminar el doctor
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = api.deleteMedico(id)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@DoctorDetalleActivity, "Doctor eliminado", Toast.LENGTH_SHORT).show()
                            regresarDoctor()  // Regresar a la lista de doctores
                        } else {
                            Toast.makeText(this@DoctorDetalleActivity, "Error al eliminar doctor", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@DoctorDetalleActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "ID no válido", Toast.LENGTH_SHORT).show()
        }
    }


    // Función para regresar a la lista de doctores
    private fun regresarDoctor() {
        val intent = Intent(this, ListaDoctoresActivity::class.java)
        startActivity(intent)
        finish()
    }
}
