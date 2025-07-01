package com.market.inventarioapp.carpcategorias

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.market.inventarioapp.InicioActivity
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.CategoriaFirestoreRepositorio

class CategoriasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoriaAdapter
    private val listaCategorias = mutableListOf<Categoria>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        val toolbar = findViewById<Toolbar>(R.id.toolbarCategorias)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerCategorias)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CategoriaAdapter(this, listaCategorias)
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAgregarCategoria).setOnClickListener {
            startActivity(Intent(this, AgregarCategoriaActivity::class.java))
        }

        findViewById<FloatingActionButton>(R.id.fabAtras).setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
            finish()
        }

        // Cargar categorías al iniciar
        cargarCategorias()
    }

    override fun onResume() {
        super.onResume()
        cargarCategorias()
    }

    private fun cargarCategorias() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        CategoriaFirestoreRepositorio.obtenerCategoriasPorUsuario(
            usuarioId = uid,
            onComplete = { categorias ->
                listaCategorias.clear()
                listaCategorias.addAll(categorias)
                adapter.notifyDataSetChanged()
            },
            onError = { error ->
                Toast.makeText(this, "Error al obtener categorías: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
