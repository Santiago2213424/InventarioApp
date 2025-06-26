package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AgregarProductosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_productos)

        val categoria = intent.getStringExtra("categoria") ?: ""

        val btnCancelarProducto = findViewById<Button>(R.id.btnCancelarProducto)
        val btnGuardarProducto = findViewById<Button>(R.id.btnGuardarProducto)

        btnGuardarProducto.setOnClickListener {
            val intent = Intent(this, InventarioActivity::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
            finish()
        }

        btnCancelarProducto.setOnClickListener {
            val intent = Intent(this, InventarioActivity::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
            finish()
        }
    }
}