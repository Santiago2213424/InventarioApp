package com.market.inventarioapp.carpproductos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.ProductoFirestoreRepositorio

class BajoStockActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bajo_stock)

        val toolbar = findViewById<Toolbar>(R.id.toolbarBajoStock)
        setSupportActionBar(toolbar)

        uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        recycler = findViewById(R.id.recyclerBajoStock)
        recycler.layoutManager = LinearLayoutManager(this)

        // ✅ Adapter configurado con edición, eliminación y mostrar categoría
        adapter = ProductoAdapter(
            mutableListOf(),
            onEditar = { producto ->
                val intent = Intent(this, EditarProductoActivity::class.java)
                intent.putExtra("productoId", producto.id)
                startActivity(intent)
            },
            onEliminar = { producto ->
                ProductoFirestoreRepositorio.eliminarProducto(producto.id) { exito ->
                    if (exito) {
                        Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                        cargarProductosBajoStock()
                    } else {
                        Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            mostrarCategoria = true // 👈 Aquí se muestra la categoría
        )

        recycler.adapter = adapter

        // Botón atrás
        findViewById<FloatingActionButton>(R.id.fabAtras).setOnClickListener {
            finish()
        }

        cargarProductosBajoStock()
    }

    override fun onResume() {
        super.onResume()
        cargarProductosBajoStock() // Se recarga al volver de EditarProductoActivity
    }

    private fun cargarProductosBajoStock() {
        ProductoFirestoreRepositorio.obtenerTodosPorUsuario(uid) { lista ->
            val productosBajoStock = lista.filter { it.cantidad <= 5 }
            adapter.actualizarLista(productosBajoStock)
            if (productosBajoStock.isEmpty()) {
                Toast.makeText(this, "No hay productos con bajo stock", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
