package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AltoStockActivity : AppCompatActivity() {

    private lateinit var recyclerAltoStock: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alto_stock)

        // Configurar toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarAltoStock)
        setSupportActionBar(toolbar)

        // Configurar botón atrás
        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            val intent = Intent(this, StockActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Inicializar RecyclerView
        recyclerAltoStock = findViewById(R.id.recyclerAltoStock)
        recyclerAltoStock.layoutManager = LinearLayoutManager(this)

        // Filtrar productos con cantidad > 15 (alto stock)
        val productosAltoStock = ProductoRepositorio.productos.filter {
            it.cantidad > 15
        }

        // Asignar adaptador
        recyclerAltoStock.adapter = ProductoAdapter(productosAltoStock)
    }
}
