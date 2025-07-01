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
import com.market.inventarioapp.InicioActivity
import com.market.inventarioapp.R
import com.market.inventarioapp.carpproductos.StockActivity
import com.market.inventarioapp.repositorio.ProductoFirestoreRepositorio

class AltoStockActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alto_stock)

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarAltoStock)
        setSupportActionBar(toolbar)

        // UID del usuario logueado
        uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerAltoStock)
        recyclerView.layoutManager = LinearLayoutManager(this)

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
                        cargarProductos()
                    } else {
                        Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            mostrarCategoria = true
        )

        recyclerView.adapter = adapter

        // Botón atrás
        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            startActivity(Intent(this, StockActivity::class.java))
            finish()
        }

        cargarProductos()
    }

    private fun cargarProductos() {
        ProductoFirestoreRepositorio.obtenerPorUsuario(uid) { productos ->
            val filtrados = productos.filter { it.cantidad > 50 }
            adapter.actualizarLista(filtrados)
            if (filtrados.isEmpty()) {
                Toast.makeText(this, "No hay productos con Alto stock", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
