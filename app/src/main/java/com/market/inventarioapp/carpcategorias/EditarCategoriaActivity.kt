package com.market.inventarioapp.carpcategorias

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.CategoriaFirestoreRepositorio

class EditarCategoriaActivity : AppCompatActivity() {

    private lateinit var edtNombreCategoria: TextInputEditText
    private var categoriaId: String? = null
    private var categoriaNombreOriginal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_categoria)

        val toolbar = findViewById<Toolbar>(R.id.toolbarEditarCategoria)
        setSupportActionBar(toolbar)

        edtNombreCategoria = findViewById(R.id.edtNombreCategoria)
        val btnCancelar = findViewById<Button>(R.id.btnCancelarCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCategoria)

        // Obtener datos del intent
        categoriaId = intent.getStringExtra("categoriaId")
        categoriaNombreOriginal = intent.getStringExtra("categoriaNombre")
        edtNombreCategoria.setText(categoriaNombreOriginal)

        btnCancelar.setOnClickListener {
            finish()
        }

        btnGuardar.setOnClickListener {
            val nuevoNombre = edtNombreCategoria.text.toString().trim()
            if (nuevoNombre.isEmpty()) {
                Toast.makeText(this, "Ingresa un nombre válido", Toast.LENGTH_SHORT).show()
            } else if (categoriaId != null) {
                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener
                val categoriaActualizada = Categoria(
                    id = categoriaId!!,
                    nombre = nuevoNombre,
                    usuarioId = uid
                )
                CategoriaFirestoreRepositorio.actualizarCategoria(categoriaActualizada) { exito ->
                    if (exito) {
                        Toast.makeText(this, "Categoría actualizada", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
