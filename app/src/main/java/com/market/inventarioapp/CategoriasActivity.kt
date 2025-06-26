package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CategoriasActivity : AppCompatActivity() {

    private lateinit var recyclerCategorias: RecyclerView
    private lateinit var categoriaAdapter: CategoriaAdapter

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

        // PASO CLAVE: Configurar RecyclerView
        recyclerCategorias = findViewById(R.id.recyclerCategorias)
        recyclerCategorias.layoutManager = LinearLayoutManager(this)

        // Datos de prueba
        val listaCategorias = listOf(
            Categoria("Cereales"),
            Categoria("Bebidas"),
            Categoria("Lácteos"),
            Categoria("Verduras"),
            Categoria("Embasados"),
            Categoria("Galletas"),
            Categoria("Carnes"),
            Categoria("Pelotas"),
            Categoria("Verduras")
        )

        categoriaAdapter = CategoriaAdapter(this, listaCategorias)
        recyclerCategorias.adapter = categoriaAdapter
    }
}