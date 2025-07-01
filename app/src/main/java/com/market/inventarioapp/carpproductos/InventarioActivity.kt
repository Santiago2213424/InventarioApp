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
import com.market.inventarioapp.carpcategorias.CategoriasActivity
import com.market.inventarioapp.repositorio.ProductoFirestoreRepositorio

class InventarioActivity : AppCompatActivity() {

    private lateinit var recyclerProductos: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private var categoria: String? = null
    private var uid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        categoria = intent.getStringExtra("categoria")
        uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        recyclerProductos = findViewById(R.id.recyclerProductos)
        recyclerProductos.layoutManager = LinearLayoutManager(this)

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
            }
        )

        recyclerProductos.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAgregar).setOnClickListener {
            val intent = Intent(this, AgregarProductosActivity::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }

        findViewById<FloatingActionButton>(R.id.fabAtras).setOnClickListener {
            startActivity(Intent(this, CategoriasActivity::class.java))
            finish()
        }

        cargarProductos()
    }

    override fun onResume() {
        super.onResume()
        cargarProductos()
    }

    private fun cargarProductos() {
        ProductoFirestoreRepositorio.obtenerPorUsuarioYCategoria(uid, categoria ?: "") { lista ->
            adapter.actualizarLista(lista)
        }
    }
}
