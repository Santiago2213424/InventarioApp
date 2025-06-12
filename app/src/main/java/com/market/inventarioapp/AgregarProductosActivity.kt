package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AgregarProductosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_productos)

        val btnCancelarProducto = findViewById<Button>(R.id.btnCancelarProducto)
        val btnGuardarProducto = findViewById<Button>(R.id.btnGuardarProducto)


        btnGuardarProducto.setOnClickListener {
            startActivity(Intent(this, InventarioActivity::class.java))

        }

        btnCancelarProducto.setOnClickListener {
            startActivity(Intent(this, InventarioActivity::class.java))
        }
    }
}
