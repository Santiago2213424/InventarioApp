package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CategoriasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        val toolbar = findViewById<Toolbar>(R.id.toolbarCategorias)
        setSupportActionBar(toolbar)

        val fabAgregarCategoria = findViewById<FloatingActionButton>(R.id.fabAgregarCategoria)
        fabAgregarCategoria.setOnClickListener {
            val intent = Intent(this, AgregarCategoriaActivity::class.java)
            startActivity(intent)
        }

        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            finish()
        }

        val cardCereales = findViewById<CardView>(R.id.cardCereales)
        val cardLacteos = findViewById<CardView>(R.id.cardLacteos)
        val cardBebidas = findViewById<CardView>(R.id.cardBebidas)
        val cardSnacks = findViewById<CardView>(R.id.cardSnacks)
        val cardVerduras = findViewById<CardView>(R.id.cardVerduras)

        fun irAInventario(categoria: String) {
            Toast.makeText(this, "Agrega tus productos", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, InventarioActivity::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }

        cardCereales.setOnClickListener { irAInventario("Cereales") }
        cardLacteos.setOnClickListener { irAInventario("Lácteos") }
        cardBebidas.setOnClickListener { irAInventario("Bebidas") }
        cardSnacks.setOnClickListener { irAInventario("Snacks") }
        cardVerduras.setOnClickListener { irAInventario("Verduras") }
    }
}
