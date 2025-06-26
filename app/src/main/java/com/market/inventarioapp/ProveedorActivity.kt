package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProveedorActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProveedorAdapter
    private val listaProveedores = mutableListOf<Proveedor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedor)

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarProveedores)
        setSupportActionBar(toolbar)

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerProveedores)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Datos de prueba
        listaProveedores.addAll(
            listOf(
                Proveedor("Distribuidora ABC"),
                Proveedor("Mayorista XYZ"),
                Proveedor("Almacenes 123")
            )
        )

        // Adapter con acciones
        adapter = ProveedorAdapter(listaProveedores,
            onEditarClick = { proveedor ->
                startActivity(Intent(this, EditarProveedorActivity::class.java))
            },
            onEliminarClick = { proveedor ->
                val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Estás seguro que quieres eliminar al proveedor ${proveedor.nombre}?")
                    .setPositiveButton("Sí") { _, _ ->
                        listaProveedores.remove(proveedor)
                        adapter.notifyDataSetChanged()
                        Toast.makeText(this, "Proveedor eliminado: ${proveedor.nombre}", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No", null)
                    .create()

                dialog.show()
            }
        )

        recyclerView.adapter = adapter

        // FAB Agregar proveedor
        val fabAgregarProveedor = findViewById<FloatingActionButton>(R.id.fabAgregarProveedor)
        fabAgregarProveedor.setOnClickListener {
            startActivity(Intent(this, AgregarProveedorActivity::class.java))
        }

        // FAB Atrás
        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
            finish()
        }
    }
}
