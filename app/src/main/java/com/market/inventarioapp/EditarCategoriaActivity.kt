package com.market.inventarioapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EditarCategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_categoria)

        val toolbar = findViewById<Toolbar>(R.id.toolbarEditarCategoria)
        setSupportActionBar(toolbar)

        val edtNombreCategoria = findViewById<EditText>(R.id.edtNombreCategoria)
        val btnCancelar = findViewById<Button>(R.id.btnCancelarCategoria)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarCategoria)

        // Puedes recibir el nombre actual de la categoría si lo mandas por intent
        val nombreActual = intent.getStringExtra("nombreCategoria")
        edtNombreCategoria.setText(nombreActual)

        btnCancelar.setOnClickListener {
            finish() // Cierra la pantalla
        }

        btnGuardar.setOnClickListener {
            val nuevoNombre = edtNombreCategoria.text.toString().trim()
            if (nuevoNombre.isEmpty()) {
                Toast.makeText(this, "Ingresa un nombre válido", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí puedes guardar los cambios (actualizar en base de datos, etc.)
                Toast.makeText(this, "Categoría actualizada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
