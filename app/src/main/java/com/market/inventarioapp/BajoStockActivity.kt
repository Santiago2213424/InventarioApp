package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BajoStockActivity : AppCompatActivity() {

    private lateinit var recyclerBajoStock: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bajo_stock) // usa el XML que mostraste, ya corregido

        val toolbar = findViewById<Toolbar>(R.id.toolbarBajoStock)
        setSupportActionBar(toolbar)

        // Filtrar productos con bajo stock (ejemplo: cantidad < 10)
        val productosFiltrados = ProductoRepositorio.productos.filter {
            it.cantidad < 10
        }

        // RecyclerView setup
        recyclerBajoStock = findViewById(R.id.recyclerBajoStock)
        recyclerBajoStock.layoutManager = LinearLayoutManager(this)
        recyclerBajoStock.adapter = ProductoAdapter(productosFiltrados)

        // Botón volver
        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            finish()
        }
    }
}
