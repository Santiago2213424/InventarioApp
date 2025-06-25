package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.market.inventarioapp.CategoriasActivity

class InventarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 👉 Aquí conectas el botón flotante
        val fabAgregar = findViewById<FloatingActionButton>(R.id.fabAgregar)
        fabAgregar.setOnClickListener {
            // Aquí lanzas la nueva Activity
            val intent = Intent(this, AgregarProductosActivity::class.java)
            startActivity(intent)
        }
        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            val intent = Intent(this, CategoriasActivity::class.java)
            startActivity(intent)
            finish() // Opcional: finaliza esta actividad para no dejarla en la pila
        }
    }
}
