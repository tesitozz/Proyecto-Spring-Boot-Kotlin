package com.proyecto.proyectoesfrt

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appproyecto.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import com.proyecto.proyectoesfrt.api.RetrofitClient
import com.proyecto.proyectoesfrt.entidad.Animal
import com.proyecto.proyectoesfrt.entidad.Cliente
import com.proyecto.proyectoesfrt.entidad.HistorialClinica
import com.proyecto.proyectoesfrt.entidad.Medico
import com.proyecto.proyectoesfrt.service.ApiServiceAnimal
import com.proyecto.proyectoesfrt.service.ApiServiceCliente
import com.proyecto.proyectoesfrt.service.ApiServiceMedicos
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.security.auth.callback.Callback

class AgregarHistorialActivity : AppCompatActivity() {
    private lateinit var txtFechaRegistoHistorial: TextInputEditText
    private lateinit var txtHoraRegistroHistorial: TextInputEditText

    private lateinit var txtDiagnosticoHistorial:TextView
    private lateinit var txtTratamientoHistorial:TextView
    private lateinit var txtVacunasAplicadasHistorial:TextView
    private lateinit var txtObservacionesHistorial:TextView

    // Duenio
    private lateinit var spnDuenioListarHistorial: AutoCompleteTextView
    private lateinit var txtApellidoDuenioRegistroHistorial: TextView
    private lateinit var txtDniDuenoRegistroHistorial: TextView
    private lateinit var txtCorreoDuenioRegistroHistorial: TextView
    private lateinit var txtTelefonoDuenioRegistroHistorial: TextView
    private lateinit var txtDireccionDuenoRegistroHistorial: TextView

    // Animal
    private lateinit var spnAnimalRegistrar: AutoCompleteTextView
    private lateinit var txtTipoAnimalRegistrar: TextView
    private lateinit var txtGeneroAnimalHistorialRegistrar: TextView
    private lateinit var txtEdadAnimalRegistrarHistorial: TextView
    private lateinit var txtPesoAnimalHistorial: TextView
    private lateinit var txtRazaAnimalRegistrarHistorial: TextView
    private lateinit var txtColorAnimalRegistrar: TextView

    // Doctor
    private lateinit var spnMedicoHistorialRegistrar: AutoCompleteTextView
    private lateinit var txtNombresyApellidosMedicoHistorial: TextView
    private lateinit var txtCorreoMedicoHistorial: TextView
    private lateinit var txtTelefonoMedicoHistorial: TextView

    // Botones
    private lateinit var btnRegistrarHistorialMedico: Button
    private lateinit var btnVolverRegistrarHistorial: Button

    // API
    private lateinit var api: ApiServiceMedicos
    private lateinit var apis: ApiServiceAnimal
    private lateinit var api1: ApiServiceCliente

    private var dueniosList = mutableListOf<Cliente>()
    private var animalesList = mutableListOf<Animal>()
    private var medicosList = mutableListOf<Medico>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.historial_agregar_main)


        // Inicialización de vistas
        txtFechaRegistoHistorial = findViewById(R.id.txtFechaRegistoHistorial)
        txtHoraRegistroHistorial = findViewById(R.id.txtHoraRegistroHistorial)
        txtDiagnosticoHistorial = findViewById(R.id.txtDiagnosticoHistorial)
        txtTratamientoHistorial = findViewById(R.id.txtTratamientoHistorial)
        txtVacunasAplicadasHistorial = findViewById(R.id.txtVacunasAplicadasHistorial)
        txtObservacionesHistorial = findViewById(R.id.txtObservacionesHistorial)
        spnDuenioListarHistorial = findViewById(R.id.spnDuenioListarHistorial)
        txtApellidoDuenioRegistroHistorial = findViewById(R.id.txtApellidoDuenioRegistroHistorial)
        txtDniDuenoRegistroHistorial = findViewById(R.id.txtDniDuenoRegistroHistorial)
        txtCorreoDuenioRegistroHistorial = findViewById(R.id.txtCorreoDuenioRegistroHistorial)
        txtTelefonoDuenioRegistroHistorial = findViewById(R.id.txtTelefonoDuenioRegistroHistorial)
        txtDireccionDuenoRegistroHistorial = findViewById(R.id.txtDireccionDuenoRegistroHistorial)
        spnAnimalRegistrar = findViewById(R.id.spnAnimalRegistrar)
        txtTipoAnimalRegistrar = findViewById(R.id.txtTipoAnimalRegistrar)
        txtGeneroAnimalHistorialRegistrar = findViewById(R.id.txtGeneroAnimalHistorialRegistrar)
        txtEdadAnimalRegistrarHistorial = findViewById(R.id.txtEdadAnimalRegistrarHistorial)
        txtPesoAnimalHistorial = findViewById(R.id.txtPesoAnimalHistorial)
        txtRazaAnimalRegistrarHistorial = findViewById(R.id.txtRazaAnimalRegistrarHistorial)
        txtColorAnimalRegistrar = findViewById(R.id.txtColorAnimalRegistrar)
        spnMedicoHistorialRegistrar = findViewById(R.id.spnMedicoHistorialRegistrar)
        txtNombresyApellidosMedicoHistorial = findViewById(R.id.txtNombresyApellidosMedicoHistorial)
        txtCorreoMedicoHistorial = findViewById(R.id.txtCorreoMedicoHistorial)
        txtTelefonoMedicoHistorial = findViewById(R.id.txtTelefonoMedicoHistorial)

        btnRegistrarHistorialMedico = findViewById(R.id.btnRegistrarHistorialMedico)
        btnVolverRegistrarHistorial = findViewById(R.id.btnVolverRegistrarHistorial)

        api = ApiUtils.getApiDoctor()
        apis = ApiUtils.getApiAnimal()
        api1 = ApiUtils.getApiCliente()

        obtenerDatos()

        btnRegistrarHistorialMedico.setOnClickListener { registrarHistorialMedico() }
        btnVolverRegistrarHistorial.setOnClickListener { volverHistorial() }

        txtFechaRegistoHistorial.setOnClickListener {
            fechaRegistroHistorial()
        }

        txtHoraRegistroHistorial.setOnClickListener {
            horaRegistroHistorial()
        }

        spnDuenioListarHistorial.setOnItemClickListener { _, _, position, _ ->
            val clienteSeleccionado = dueniosList[position]
            // Usa clienteSeleccionado
        }

        spnAnimalRegistrar.setOnItemClickListener { _, _, position, _ ->
            val animalSeleccionado = animalesList[position]
            // Usa animalSeleccionado
        }

        spnMedicoHistorialRegistrar.setOnItemClickListener { _, _, position, _ ->
            val medicoSeleccionado = medicosList[position]
        }

    }

    fun fechaRegistroHistorial() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view, selectedYear, selectedMonth, selectedDay ->
                // Formato de fecha: YYYY-MM-DD
                val fechaSeleccionada = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                txtFechaRegistoHistorial.setText(fechaSeleccionada) // Mostrar la fecha seleccionada
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    fun horaRegistroHistorial() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { view, selectedHour, selectedMinute ->
                // Formato de hora: HH:MM
                val horaSeleccionada = String.format("%02d:%02d", selectedHour, selectedMinute)
                txtHoraRegistroHistorial.setText(horaSeleccionada) // Mostrar la hora seleccionada
            },
            hour, minute, true // true para formato de 24 horas
        )
        timePickerDialog.show()
    }

    private fun obtenerDatos() {
        lifecycleScope.launch {
            try {
                // Llamadas a las APIs para obtener las listas de datos
                val responseDuenios = api1.getAllClientes()
                val responseAnimales = apis.getAllAnimals()
                val responseMedicos = api.getAllMedicos()

                // Verificar si las respuestas fueron exitosas
                if (responseDuenios.isSuccessful && responseAnimales.isSuccessful && responseMedicos.isSuccessful) {
                    val dueniosResponse = responseDuenios.body()
                    val animalesResponse = responseAnimales.body()
                    val medicosResponse = responseMedicos.body()

                    // Verifica que las respuestas no sean nulas
                    if (dueniosResponse != null && animalesResponse != null && medicosResponse != null) {
                        dueniosList = dueniosResponse.toMutableList()
                        animalesList = animalesResponse.toMutableList()
                        medicosList = medicosResponse.toMutableList()

                        // Configurar los adaptadores para cada AutoCompleteTextView
                        val duenioAdapter = ArrayAdapter(
                            this@AgregarHistorialActivity,
                            android.R.layout.simple_dropdown_item_1line,
                            dueniosList.map { it.nombres } // Ajusta el campo que deseas mostrar
                        )
                        spnDuenioListarHistorial.setAdapter(duenioAdapter)

                        val animalAdapter = ArrayAdapter(
                            this@AgregarHistorialActivity,
                            android.R.layout.simple_dropdown_item_1line,
                            animalesList.map { it.nombre } // Ajusta el campo que deseas mostrar
                        )
                        spnAnimalRegistrar.setAdapter(animalAdapter)

                        val medicoAdapter = ArrayAdapter(
                            this@AgregarHistorialActivity,
                            android.R.layout.simple_dropdown_item_1line,
                            medicosList.map { it.nombres } // Ajusta el campo que deseas mostrar
                        )
                        spnMedicoHistorialRegistrar.setAdapter(medicoAdapter)

                        // Listener para Duenio
                        spnDuenioListarHistorial.setOnItemClickListener { parent, view, position, id ->
                            val cliente = dueniosList[position]
                            txtApellidoDuenioRegistroHistorial.text = cliente.apellidos
                            txtDniDuenoRegistroHistorial.text = cliente.dni
                            txtCorreoDuenioRegistroHistorial.text = cliente.correo
                            txtTelefonoDuenioRegistroHistorial.text = cliente.celular
                            txtDireccionDuenoRegistroHistorial.text = cliente.direccion
                        }

                        // Listener para Animal
                        spnAnimalRegistrar.setOnItemClickListener { parent, view, position, id ->
                            val animal = animalesList[position]
                            txtTipoAnimalRegistrar.text = animal.tipo
                            txtGeneroAnimalHistorialRegistrar.text = animal.genero
                            txtEdadAnimalRegistrarHistorial.text = animal.edad.toString()
                            txtPesoAnimalHistorial.text = animal.peso.toString()
                            txtRazaAnimalRegistrarHistorial.text = animal.raza
                            txtColorAnimalRegistrar.text = animal.color
                        }

                        // Listener para Medico
                        spnMedicoHistorialRegistrar.setOnItemClickListener { parent, view, position, id ->
                            val medico = medicosList[position]
                            // Concatenamos nombres y apellidos
                            txtNombresyApellidosMedicoHistorial.text = "${medico.nombres} ${medico.apellidos}"
                            txtCorreoMedicoHistorial.text = medico.correo
                            txtTelefonoMedicoHistorial.text = medico.celular
                        }


                    } else {
                        Toast.makeText(this@AgregarHistorialActivity, "Error: Datos incompletos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@AgregarHistorialActivity, "Error al obtener datos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AgregarHistorialActivity, "Excepción al obtener datos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun registrarHistorialMedico() {
        // Validación de campos requeridos
        if (txtFechaRegistoHistorial.text.isNullOrEmpty() ||
            txtHoraRegistroHistorial.text.isNullOrEmpty() ||
            txtDiagnosticoHistorial.text.isNullOrEmpty() ||
            txtTratamientoHistorial.text.isNullOrEmpty() ||
            txtVacunasAplicadasHistorial.text.isNullOrEmpty() ||
            txtObservacionesHistorial.text.isNullOrEmpty() ||
            spnDuenioListarHistorial.text.isNullOrEmpty() ||
            spnAnimalRegistrar.text.isNullOrEmpty() ||
            spnMedicoHistorialRegistrar.text.isNullOrEmpty()
        ) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Obtener los datos de las vistas
        val fechaRegistro = txtFechaRegistoHistorial.text.toString()
        val horaRegistro = txtHoraRegistroHistorial.text.toString()
        val diagnostico = txtDiagnosticoHistorial.text.toString()
        val tratamiento = txtTratamientoHistorial.text.toString()
        val vacunasAplicadas = txtVacunasAplicadasHistorial.text.toString()
        val observaciones = txtObservacionesHistorial.text.toString()

        // Obtener los datos del dueño seleccionado
        val duenioSeleccionado = dueniosList.find { it.nombres == spnDuenioListarHistorial.text.toString() }
        val animalSeleccionado = animalesList.find { it.nombre == spnAnimalRegistrar.text.toString() }
        val medicoSeleccionado = medicosList.find { it.nombres == spnMedicoHistorialRegistrar.text.toString() }

        // Verificar que se haya seleccionado un dueño, un animal y un médico
        if (duenioSeleccionado == null || animalSeleccionado == null || medicoSeleccionado == null) {
            Toast.makeText(this, "No se pudo encontrar el dueño, animal o médico seleccionado", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear el objeto HistorialClinica con los datos obtenidos
        val historialClinica = HistorialClinica(
            fechaDeRegistro = fechaRegistro,
            horaDeRegistro = horaRegistro,
            diagnostico = diagnostico,
            tratamiento = tratamiento,
            vacunasAplicadas = vacunasAplicadas,
            observaciones = observaciones,
            cliente = duenioSeleccionado,
            animal = animalSeleccionado,
            medico = medicoSeleccionado
        )
        lifecycleScope.launch {
            try {
                // Llamada a la API para registrar el historial
                val response = RetrofitClient.apiServiceHistorial.createHistoriaClinica(historialClinica)

                if (response.isSuccessful) {
                    Toast.makeText(this@AgregarHistorialActivity, "Historial registrado exitosamente", Toast.LENGTH_SHORT).show()
                    // Opcional: Volver a la pantalla anterior o limpiar los campos
                    finish()
                } else {
                    Toast.makeText(this@AgregarHistorialActivity, "Error al registrar el historial", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AgregarHistorialActivity, "Excepción: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun volverHistorial() {
        finish()
    }
}
