package com.proyecto.proyectoesfrt

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.proyecto.proyectoesfrt.entidad.HistorialClinica
import com.proyecto.proyectoesfrt.api.RetrofitClient
import com.proyecto.proyectoesfrt.entidad.Animal
import com.proyecto.proyectoesfrt.entidad.Cliente
import com.proyecto.proyectoesfrt.entidad.Medico
import com.proyecto.proyectoesfrt.service.ApiServiceAnimal
import com.proyecto.proyectoesfrt.service.ApiServiceCliente
import com.proyecto.proyectoesfrt.service.ApiServiceHistorial
import com.proyecto.proyectoesfrt.service.ApiServiceMedicos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistorialDetalleActivity : AppCompatActivity() {
    private lateinit var historialClinica: HistorialClinica

    // Vistas de la actividad
    private lateinit var txtFechaRegistoHistorialDetalles: TextView
    private lateinit var txtHoraRegistroHistorialDetalles: TextView
    private lateinit var txtDiagnosticoHistorialDetalle: TextView
    private lateinit var txtTratamientoHistorialDetalle: TextView
    private lateinit var txtVacunasAplicadasHistorialDetalleDetalle: TextView
    private lateinit var txtObservacionesHistorialDetalle: TextView
    private lateinit var txtApellidoDuenioRegistroHistorialDetalles: TextView
    private lateinit var txtCorreoDuenioRegistroHistorialDetalles: TextView
    private lateinit var txtDniDuenoRegistroHistorialDetalles: TextView
    private lateinit var txtTelefonoDuenioRegistroHistorialDetalles: TextView
    private lateinit var txtDireccionDuenoRegistroHistorialDetalles: TextView
    private lateinit var txtRazaAnimalRegistroHistorialDetalles: TextView
    private lateinit var txtColorAnimalRegistroHistorialDetallesDetalles: TextView
    private lateinit var txtEspecieAnimalRegistroHistorialDetalles: TextView
    private lateinit var txtPesoAnimalRegistroHistorialDetalles: TextView
    private lateinit var spnMedicoHistorialDetalle: AutoCompleteTextView
    private lateinit var spnDuenioListarHistorialModificar: AutoCompleteTextView
    private lateinit var spnAnimalRegistrarModificarDetalles: AutoCompleteTextView

    private lateinit var txtNombresyApellidosMedicoHistorialDetalle: TextView
    private lateinit var txtCorreoMedicoHistorialDetalle: TextView
    private lateinit var txtTelefonoMedicoHistorialDetalle: TextView

    private lateinit var btnModificarRegistroHistorial: Button
    private lateinit var btnEliminarHistorial: Button
    private lateinit var btnRegresar: Button

    private var clienteSeleccionadoGlo: Cliente? = null
    private var medicoSeleccionadoGlo: Medico? = null
    private var animalSeleccionadoGlo: Animal? = null

    private val apiServiceAnimal: ApiServiceAnimal = RetrofitClient.apiService
    private val apiServiceCliente: ApiServiceCliente = RetrofitClient.apiServiceCliente
    private val apiServiceMedicos: ApiServiceMedicos = RetrofitClient.apiServiceDoctor
    private val apiServiceHistorial: ApiServiceHistorial = RetrofitClient.apiServiceHistorial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.historial_detalles_main)

        historialClinica = intent.getParcelableExtra("historial") ?: run {
            Toast.makeText(this, "No se pudo cargar el historial, intenta nuevamente.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        inicializarVistas()
        cargarDatosIniciales()
        configurarListeners()
    }

    private fun inicializarVistas() {
        txtFechaRegistoHistorialDetalles = findViewById(R.id.txtFechaRegistoHistorialDetalles)
        txtHoraRegistroHistorialDetalles = findViewById(R.id.txtHoraRegistroHistorialDetalles)
        txtDiagnosticoHistorialDetalle = findViewById(R.id.txtDiagnosticoHistorialDetalle)
        txtTratamientoHistorialDetalle = findViewById(R.id.txtTratamientoHistorialDetalle)
        txtVacunasAplicadasHistorialDetalleDetalle = findViewById(R.id.txtVacunasAplicadasHistorialDetalleDetalle)
        txtObservacionesHistorialDetalle = findViewById(R.id.txtObservacionesHistorialDetalle)
        txtApellidoDuenioRegistroHistorialDetalles = findViewById(R.id.txtApellidoDuenioRegistroHistorialDetalles)
        txtCorreoDuenioRegistroHistorialDetalles = findViewById(R.id.txtCorreoDuenioRegistroHistorialDetalles)
        txtDniDuenoRegistroHistorialDetalles = findViewById(R.id.txtDniDuenoRegistroHistorialDetalles)
        txtTelefonoDuenioRegistroHistorialDetalles = findViewById(R.id.txtTelefonoDuenioRegistroHistorialDetalles)
        txtDireccionDuenoRegistroHistorialDetalles = findViewById(R.id.txtDireccionDuenoRegistroHistorialDetalles)
        txtRazaAnimalRegistroHistorialDetalles = findViewById(R.id.txtRazaAnimalRegistroHistorialDetalles)
        txtColorAnimalRegistroHistorialDetallesDetalles = findViewById(R.id.txtColorAnimalRegistroHistorialDetallesDetalles)
        txtEspecieAnimalRegistroHistorialDetalles = findViewById(R.id.txtEspecieAnimalRegistroHistorialDetalles)
        txtPesoAnimalRegistroHistorialDetalles = findViewById(R.id.txtPesoAnimalRegistroHistorialDetalles)
        spnMedicoHistorialDetalle = findViewById(R.id.spnMedicoHistorialDetalle)
        spnDuenioListarHistorialModificar = findViewById(R.id.spnDuenioListarHistorialModificar)
        spnAnimalRegistrarModificarDetalles = findViewById(R.id.spnAnimalRegistrarModificarDetalles)
        txtNombresyApellidosMedicoHistorialDetalle = findViewById(R.id.txtNombresyApellidosMedicoHistorialDetalle)
        txtCorreoMedicoHistorialDetalle = findViewById(R.id.txtCorreoMedicoHistorialDetalle)
        txtTelefonoMedicoHistorialDetalle = findViewById(R.id.txtTelefonoMedicoHistorialDetalle)

        btnModificarRegistroHistorial = findViewById(R.id.btnModificarRegistroHistorial)
        btnEliminarHistorial = findViewById(R.id.btnEliminarHistorial)
        btnRegresar = findViewById(R.id.btnRegresar)
    }

    private fun cargarDatosIniciales() {
        cargarMedicos()
        cargarClientes()
        cargarAnimales()
        llenarCampos()
    }

    private fun configurarListeners() {
        spnMedicoHistorialDetalle.setOnItemClickListener { parent, _, position, _ ->
            medicoSeleccionadoGlo = parent.getItemAtPosition(position) as Medico
            actualizarCamposMedico(medicoSeleccionadoGlo!!)
        }

        spnDuenioListarHistorialModificar.setOnItemClickListener { parent, _, position, _ ->
            clienteSeleccionadoGlo = parent.getItemAtPosition(position) as Cliente
            actualizarCamposCliente(clienteSeleccionadoGlo!!)
        }

        spnAnimalRegistrarModificarDetalles.setOnItemClickListener { parent, _, position, _ ->
            animalSeleccionadoGlo = parent.getItemAtPosition(position) as Animal
            actualizarCamposAnimal(animalSeleccionadoGlo!!)
        }

        btnModificarRegistroHistorial.setOnClickListener { modificarHistorial() }
        btnEliminarHistorial.setOnClickListener { eliminarHistorial() }
        btnRegresar.setOnClickListener { finish() }
    }

    private fun llenarCampos() {
        txtFechaRegistoHistorialDetalles.text = historialClinica.fechaDeRegistro ?: "No disponible"
        txtHoraRegistroHistorialDetalles.text = historialClinica.horaDeRegistro ?: "No disponible"
        txtDiagnosticoHistorialDetalle.text = historialClinica.diagnostico ?: "No disponible"
        txtTratamientoHistorialDetalle.text = historialClinica.tratamiento ?: "No disponible"
        txtVacunasAplicadasHistorialDetalleDetalle.text = historialClinica.vacunasAplicadas ?: "No disponible"
        txtObservacionesHistorialDetalle.text = historialClinica.observaciones ?: "No disponible"
        txtApellidoDuenioRegistroHistorialDetalles.text = historialClinica.cliente?.apellidos ?: "No disponible"
        txtCorreoDuenioRegistroHistorialDetalles.text = historialClinica.cliente?.correo ?: "No disponible"
        txtDniDuenoRegistroHistorialDetalles.text = historialClinica.cliente?.dni ?: "No disponible"
        txtTelefonoDuenioRegistroHistorialDetalles.text = historialClinica.cliente?.celular ?: "No disponible"
        txtDireccionDuenoRegistroHistorialDetalles.text = historialClinica.cliente?.direccion ?: "No disponible"
        txtRazaAnimalRegistroHistorialDetalles.text = historialClinica.animal?.raza ?: "No disponible"
        txtColorAnimalRegistroHistorialDetallesDetalles.text = historialClinica.animal?.color ?: "No disponible"
        txtEspecieAnimalRegistroHistorialDetalles.text = historialClinica.animal?.tipo ?: "No disponible"
        txtPesoAnimalRegistroHistorialDetalles.text = historialClinica.animal?.peso?.toString() ?: "No disponible"
        txtNombresyApellidosMedicoHistorialDetalle.text = "${historialClinica.medico?.nombres ?: "No disponible"} ${historialClinica.medico?.apellidos ?: "No disponible"}"
        txtCorreoMedicoHistorialDetalle.text = historialClinica.medico?.correo ?: "No disponible"
        txtTelefonoMedicoHistorialDetalle.text = historialClinica.medico?.celular ?: "No disponible"
    }

    private fun actualizarCamposMedico(medico: Medico) {
        txtNombresyApellidosMedicoHistorialDetalle.text = "${medico.nombres} ${medico.apellidos}"
        txtCorreoMedicoHistorialDetalle.text = medico.correo
        txtTelefonoMedicoHistorialDetalle.text = medico.celular
    }

    private fun actualizarCamposCliente(cliente: Cliente) {
        txtApellidoDuenioRegistroHistorialDetalles.text = cliente.apellidos
        txtCorreoDuenioRegistroHistorialDetalles.text = cliente.correo
        txtDniDuenoRegistroHistorialDetalles.text = cliente.dni
        txtTelefonoDuenioRegistroHistorialDetalles.text = cliente.celular
        txtDireccionDuenoRegistroHistorialDetalles.text = cliente.direccion
    }

    private fun actualizarCamposAnimal(animal: Animal) {
        txtRazaAnimalRegistroHistorialDetalles.text = animal.raza
        txtColorAnimalRegistroHistorialDetallesDetalles.text = animal.color
        txtEspecieAnimalRegistroHistorialDetalles.text = animal.tipo
        txtPesoAnimalRegistroHistorialDetalles.text = animal.peso.toString()
    }

    private fun cargarMedicos() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiServiceMedicos.getAllMedicos()
                if (response.isSuccessful) {
                    val medicos = response.body() ?: emptyList()

                    // Aquí creamos el adaptador con los objetos completos.
                    val adapter = ArrayAdapter(
                        this@HistorialDetalleActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        medicos.map { "${it.nombres} ${it.apellidos}" } // Mostrar el nombre y apellido
                    )

                    // Establecer el adaptador en el spinner
                    spnMedicoHistorialDetalle.setAdapter(adapter)

                    // Usamos un adaptador personalizado para mantener los objetos completos en el Spinner
                    spnMedicoHistorialDetalle.setOnItemClickListener { parent, view, position, id ->
                        val medicoSeleccionado = medicos[position] // Obtener el objeto completo
                        medicoSeleccionadoGlo = medicoSeleccionado
                        actualizarCamposMedico(medicoSeleccionado)
                    }
                } else {
                    Toast.makeText(this@HistorialDetalleActivity, "Error al cargar los médicos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HistorialDetalleActivity, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarClientes() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiServiceCliente.getAllClientes()
                if (response.isSuccessful) {
                    val clientes = response.body() ?: emptyList()

                    // Aquí creamos el adaptador con los objetos completos.
                    val adapter = ArrayAdapter(
                        this@HistorialDetalleActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        clientes.map { it.dni } // Mostrar el DNI
                    )

                    // Establecer el adaptador en el spinner
                    spnDuenioListarHistorialModificar.setAdapter(adapter)

                    // Usamos un adaptador personalizado para mantener los objetos completos en el Spinner
                    spnDuenioListarHistorialModificar.setOnItemClickListener { parent, view, position, id ->
                        val clienteSeleccionado = clientes[position] // Obtener el objeto completo
                        clienteSeleccionadoGlo = clienteSeleccionado
                        actualizarCamposCliente(clienteSeleccionado)
                    }
                } else {
                    Toast.makeText(this@HistorialDetalleActivity, "Error al cargar los clientes", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HistorialDetalleActivity, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun cargarAnimales() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiServiceAnimal.getAllAnimals()
                if (response.isSuccessful) {
                    val animales = response.body() ?: emptyList()

                    // Aquí creamos el adaptador con los objetos completos.
                    val adapter = ArrayAdapter(
                        this@HistorialDetalleActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        animales.map { it.nombre } // Mostrar solo la raza
                    )

                    // Establecer el adaptador en el spinner
                    spnAnimalRegistrarModificarDetalles.setAdapter(adapter)

                    // Usamos un adaptador personalizado para mantener los objetos completos en el Spinner
                    spnAnimalRegistrarModificarDetalles.setOnItemClickListener { parent, view, position, id ->
                        val animalSeleccionado = animales[position] // Obtener el objeto completo
                        animalSeleccionadoGlo = animalSeleccionado
                        actualizarCamposAnimal(animalSeleccionado)
                    }
                } else {
                    Toast.makeText(this@HistorialDetalleActivity, "Error al cargar los animales", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HistorialDetalleActivity, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun modificarHistorial() {
        val historialActualizado = HistorialClinica(
            id = historialClinica.id,
            fechaDeRegistro = txtFechaRegistoHistorialDetalles.text.toString(),
            horaDeRegistro = txtHoraRegistroHistorialDetalles.text.toString(),
            diagnostico = txtDiagnosticoHistorialDetalle.text.toString(),
            tratamiento = txtTratamientoHistorialDetalle.text.toString(),
            vacunasAplicadas = txtVacunasAplicadasHistorialDetalleDetalle.text.toString(),
            observaciones = txtObservacionesHistorialDetalle.text.toString(),
            cliente = clienteSeleccionadoGlo,
            animal = animalSeleccionadoGlo,
            medico = medicoSeleccionadoGlo
        )

        apiServiceHistorial.updateHistoriaClinica(historialActualizado).enqueue(object : Callback<HistorialClinica> {
            override fun onResponse(call: Call<HistorialClinica>, response: Response<HistorialClinica>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@HistorialDetalleActivity, "Historial actualizado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@HistorialDetalleActivity, "Error al actualizar el historial", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<HistorialClinica>, t: Throwable) {
                Toast.makeText(this@HistorialDetalleActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    // Función para eliminar el historial
    private fun eliminarHistorial() {
        // Verificar si el id es null antes de intentar eliminar
        val historialId = historialClinica.id
        if (historialId != null) {
            apiServiceHistorial.deleteHistoriaClinica(historialId).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@HistorialDetalleActivity, "Historial eliminado correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@HistorialDetalleActivity, "Error al eliminar el historial", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@HistorialDetalleActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@HistorialDetalleActivity, "El historial no tiene un id válido", Toast.LENGTH_SHORT).show()
        }
    }
}
