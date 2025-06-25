package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        val btnEditar = findViewById<ImageView>(R.id.btnEditar)
        val btnEliminar = findViewById<ImageView>(R.id.btnEliminar)

        fun irAInventario(categoria: String) {
            btnEditar.visibility = View.GONE
            btnEliminar.visibility = View.GONE
            Toast.makeText(this, "Agrega tus productos", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, InventarioActivity::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }

        cardCereales.setOnClickListener {
            irAInventario("Cereales")
        }

        cardCereales.setOnLongClickListener {
            btnEditar.visibility = View.VISIBLE
            btnEliminar.visibility = View.VISIBLE
            true
        }

        btnEditar.setOnClickListener {
            startActivity(Intent(this, EditarCategoriaActivity::class.java))
            Toast.makeText(this, "Editar categoría", Toast.LENGTH_SHORT).show()
            // Aquí puedes abrir una nueva Activity o mostrar un diálogo de edición
        }

        btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Eliminar categoría")
            builder.setMessage("¿Estás seguro de que deseas eliminar la categoría 'Cereales'?")
            builder.setPositiveButton("Sí") { dialog, _ ->
                Toast.makeText(this, "Categoría eliminada", Toast.LENGTH_SHORT).show()
                btnEditar.visibility = View.GONE
                btnEliminar.visibility = View.GONE
                dialog.dismiss()
                // Aquí puedes eliminar realmente la categoría si es necesario
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }
    }
}