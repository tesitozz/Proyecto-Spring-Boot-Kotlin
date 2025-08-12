    package com.proyecto.proyectoesfrt

    import android.content.Intent
    import android.os.Bundle
    import android.widget.ArrayAdapter
    import android.widget.AutoCompleteTextView
    import android.widget.Button
    import android.widget.Spinner
    import android.widget.TextView
    import android.widget.Toast
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import androidx.lifecycle.lifecycleScope
    import com.example.appproyecto.utils.ApiUtils
    import com.google.android.material.textfield.TextInputEditText
    import com.proyecto.proyectoesfrt.entidad.Animal
    import com.proyecto.proyectoesfrt.service.ApiServiceAnimal
    import kotlinx.coroutines.launch

    class AgregarAnimalesActivity : AppCompatActivity() {

       private lateinit var  txtNombreAnimalAgregar:TextView

       private lateinit var txtEdadAnimalAgregar:TextView

       private lateinit var txtPesoAnimalAgregar:TextView

        private lateinit var spnColorAnimalAgregar: AutoCompleteTextView

        private lateinit var spnTipoAnimalRegistrar: AutoCompleteTextView

        private lateinit var spnRazaAnimalAgregar: AutoCompleteTextView

        private lateinit var btnRegistrarAnimalAgregar : Button

        private lateinit var btnRegresarAnimalAgregar:Button


        //Api
        private lateinit var api: ApiServiceAnimal


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.animales_agregar_main)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            txtNombreAnimalAgregar = findViewById(R.id.txtNombreAnimalAgregar)
            txtEdadAnimalAgregar = findViewById(R.id.txtEdadAnimalAgregar)
            txtPesoAnimalAgregar = findViewById(R.id.txtPesoAnimalAgregar)
            spnColorAnimalAgregar = findViewById(R.id.spnColorAnimalAgregar)
            spnTipoAnimalRegistrar = findViewById(R.id.spnTipoAnimalRegistrar)
            spnRazaAnimalAgregar = findViewById(R.id.spnRazaAnimalAgregar)
            btnRegresarAnimalAgregar = findViewById(R.id.btnRegresarAnimalAgregar)
            btnRegistrarAnimalAgregar = findViewById(R.id.btnRegistrarAnimalAgregar)


            btnRegistrarAnimalAgregar.setOnClickListener { registrarAnimalApi() }
            btnRegresarAnimalAgregar.setOnClickListener { regresarAnimal() }


            api = ApiUtils.getApiAnimal()


        }

        private fun registrarAnimalApi() {
            // Obtener los datos de los campos del formulario
            val nombre = txtNombreAnimalAgregar.text.toString()
            val edad = txtEdadAnimalAgregar.text.toString().toIntOrNull()
            val peso = txtPesoAnimalAgregar.text.toString().toDoubleOrNull()
            val color = spnColorAnimalAgregar.text.toString()
            val tipo = spnTipoAnimalRegistrar.text.toString()
            val raza = spnRazaAnimalAgregar.text.toString()

            // Verificar que los campos obligatorios no estén vacíos
            if (nombre.isEmpty() || edad == null || peso == null || color.isEmpty() || tipo.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return
            }

            // Crear el objeto Animal
            val animal = Animal(
                id = null, // El ID será generado por el backend
                nombre = nombre,
                tipo = tipo,
                genero = "", // Si es necesario agregar genero, obtenerlo de otro campo
                edad = edad,
                peso = peso,
                raza = raza,
                color = color
            )

            // Llamar a la API para registrar el animal
            lifecycleScope.launch {
                try {
                    // Realizar la solicitud a la API
                    val response = api.createAnimal(animal)

                    // Verificar la respuesta de la API
                    if (response.isSuccessful) {
                        val registradoAnimal = response.body()
                        if (registradoAnimal != null) {
                            // Animal registrado exitosamente
                            Toast.makeText(this@AgregarAnimalesActivity, "Animal registrado correctamente", Toast.LENGTH_SHORT).show()
                            regresarAnimal() // Regresar a la lista de animales
                        }
                    } else {
                        // Error en la respuesta
                        Toast.makeText(this@AgregarAnimalesActivity, "Error al registrar el animal", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    // Capturar cualquier error en la solicitud
                    Toast.makeText(this@AgregarAnimalesActivity, "Error en la solicitud: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun regresarAnimal(){
            var intent= Intent(this,ListaAnimalesActivity::class.java)
            startActivity(intent)
        }




    }