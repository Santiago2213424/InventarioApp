package com.market.inventarioapp.carpcategorias

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.textfield.TextInputEditText
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.CategoriaFirestoreRepositorio
import java.util.*

class AgregarCategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_categoria)


        val btnCancelar = findViewById<Button>(R.id.btnCancelarCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCategoria)
        val edtNombreCategoria = findViewById<TextInputEditText>(R.id.edtNombreCategoria)

        btnCancelar.setOnClickListener {
            startActivity(Intent(this, CategoriasActivity::class.java))
            finish()
        }

        //Obtiene el texto ingresado del usuario y borra epacios vacios
        btnGuardar.setOnClickListener {
            val nombre = edtNombreCategoria.text.toString().trim()

            //Validacion
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa el nombre de la categoría", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Da error si el campo está vacío y detiene la ejecución
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                // Genera un ID único y crea el objeto Categoria
                val id = UUID.randomUUID().toString()
                val nuevaCategoria = Categoria(id = id, nombre = nombre, usuarioId = uid)

                CategoriaFirestoreRepositorio.agregarCategoria(nuevaCategoria) { exito ->
                    if (exito) {
                        Toast.makeText(this, "Categoría agregada correctamente", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, CategoriasActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error al guardar la categoría", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "No se pudo obtener el usuario actual", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
