package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AgregarCategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_categoria)

        val btnCancelarCategoria = findViewById<Button>(R.id.btnCancelarCategoria)
        val btnGuardarCategoria = findViewById<Button>(R.id.btnGuardarCategoria)



        btnCancelarCategoria.setOnClickListener {
            startActivity(Intent(this, CategoriasActivity::class.java))
        }

        btnGuardarCategoria.setOnClickListener {
            startActivity(Intent(this, CategoriasActivity::class.java))
        }
    }
}