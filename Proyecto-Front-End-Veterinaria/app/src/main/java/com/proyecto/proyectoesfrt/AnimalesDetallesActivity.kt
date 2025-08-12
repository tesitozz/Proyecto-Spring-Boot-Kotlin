package com.proyecto.proyectoesfrt

 import android.content.Intent
            import android.os.Bundle
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
            import androidx.lifecycle.lifecycleScope
            import com.example.appproyecto.utils.ApiUtils
            import com.proyecto.proyectoesfrt.entidad.Animal
            import com.proyecto.proyectoesfrt.service.ApiServiceAnimal
            import kotlinx.coroutines.launch

            class AnimalesDetallesActivity : AppCompatActivity() {

                private lateinit var txtNombreAnimalDetalles: TextView
                private lateinit var txtEdadAnimalDetalle: TextView
                private lateinit var txtPesoAnimalDetalles: TextView
                private lateinit var spnColorAnimalDetalles: AutoCompleteTextView
                private lateinit var spnTipoAnimalDetalles: AutoCompleteTextView
                private lateinit var spnRazaAnimalDetalles: AutoCompleteTextView
                private lateinit var btnRegistrarAnimalModificar: Button
                private lateinit var btnRegistrarAnimalBorrar: Button

                private lateinit var btnRegresarAnimalAgregar:Button


                //Api
                private lateinit var api: ApiServiceAnimal

                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    enableEdgeToEdge()
                    setContentView(R.layout.animales_detalles_main)

                    // Configurar la vista
                    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                        insets
                    }

                    // Inicialización de vistas
                    txtNombreAnimalDetalles = findViewById(R.id.txtNombreAnimalDetalles)
                    txtEdadAnimalDetalle = findViewById(R.id.txtEdadAnimalDetalle)
                    txtPesoAnimalDetalles = findViewById(R.id.txtPesoAnimalDetalles)
                    spnColorAnimalDetalles = findViewById(R.id.spnColorAnimalDetalles)
                    spnTipoAnimalDetalles = findViewById(R.id.spnTipoAnimalDetalles)
                    spnRazaAnimalDetalles = findViewById(R.id.spnRazaAnimalDetalles)

                    btnRegistrarAnimalModificar = findViewById(R.id.btnRegistrarAnimalModificar)
                    btnRegistrarAnimalBorrar = findViewById(R.id.btnRegistrarAnimalBorrar)
                    btnRegresarAnimalAgregar = findViewById(R.id.btnRegresarAnimalAgregar)



                    // Inicializar API
                    api = ApiUtils.getApiAnimal()


                    // Obtener el ID del animal
                    val animalId = intent.getLongExtra("id", -1)

                    if (animalId != -1L) {
                        cargarDatosAnimal(animalId)
                    }

                    // Configuración de botones
                    btnRegistrarAnimalBorrar.setOnClickListener { borrarAnimal(animalId) }
                    btnRegistrarAnimalModificar.setOnClickListener { modificarAnimal(animalId) }
                    btnRegresarAnimalAgregar.setOnClickListener { volverListado() }
                }


                private fun cargarDatosAnimal(animalId: Long) {
                    lifecycleScope.launch {
                        try {
                            val response = api.getAnimalById(animalId)
                            if (response.isSuccessful) {
                                val animal = response.body()
                                if (animal != null) {
                                    // Rellenar los campos con los datos del animal
                                    txtNombreAnimalDetalles.text = animal.nombre
                                    txtEdadAnimalDetalle.text = animal.edad.toString()
                                    txtPesoAnimalDetalles.text = animal.peso.toString()

                                    // Establecer el valor de los AutoCompleteTextView
                                    spnColorAnimalDetalles.setText(animal.color ?: "", false)
                                    spnTipoAnimalDetalles.setText(animal.tipo ?: "", false)
                                    spnRazaAnimalDetalles.setText(animal.raza ?: "", false)
                                }
                            } else {
                                Toast.makeText(this@AnimalesDetallesActivity, "Error al cargar los detalles del animal", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this@AnimalesDetallesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }





                private fun modificarAnimal(animalId: Long) {
                    // Obtener los valores de los campos
                    val nombre = txtNombreAnimalDetalles.text.toString()
                    val edad = txtEdadAnimalDetalle.text.toString().toIntOrNull()
                    val peso = txtPesoAnimalDetalles.text.toString().toDoubleOrNull()
                    val color = spnColorAnimalDetalles.text.toString()
                    val tipo = spnTipoAnimalDetalles.text.toString()
                    val raza = spnRazaAnimalDetalles.text.toString()

                    // Validar que los campos no estén vacíos
                    if (nombre.isEmpty() || edad == null || peso == null || color.isEmpty() || tipo.isEmpty()) {
                        Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                        return
                    }

                    // Crear un objeto Animal con los datos modificados
                    val animal = Animal(
                        id = animalId,
                        nombre = nombre,
                        tipo = tipo,
                        genero = "", // Agregar campo de género si es necesario
                        edad = edad,
                        peso = peso,
                        raza = raza,
                        color = color
                    )

                    // Realizar la solicitud PUT para modificar el animal
                    lifecycleScope.launch {
                        try {
                            val response = api.updateAnimal(animal)
                            if (response.isSuccessful) {
                                Toast.makeText(this@AnimalesDetallesActivity, "Animal modificado con éxito", Toast.LENGTH_SHORT).show()
                                volverListado() // Regresar a la lista de animales
                            } else {
                                Toast.makeText(this@AnimalesDetallesActivity, "Error al modificar el animal", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this@AnimalesDetallesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }



                private fun borrarAnimal(animalId: Long) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Confirmar eliminación")
                    builder.setMessage("¿Estás seguro de que deseas eliminar este animal?")

                    builder.setPositiveButton("Sí") { _, _ ->
                        lifecycleScope.launch {
                            try {
                                val response = api.deleteAnimal(animalId) // Método para eliminar el animal
                                if (response.isSuccessful) {
                                    Toast.makeText(this@AnimalesDetallesActivity, "Animal eliminado con éxito", Toast.LENGTH_SHORT).show()
                                    volverListado() // Regresar a la lista de animales
                                } else {
                                    Toast.makeText(this@AnimalesDetallesActivity, "Error al eliminar el animal", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(this@AnimalesDetallesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                    builder.show()
                }


                private fun volverListado() {
                    val intent = Intent(this, ListaAnimalesActivity::class.java)
                    startActivity(intent)
                }



            }
