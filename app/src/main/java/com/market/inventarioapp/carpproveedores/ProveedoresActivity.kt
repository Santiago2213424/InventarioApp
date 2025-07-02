package com.market.inventarioapp.carpproveedores

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.ProveedorFirestoreRepositorio

class ProveedoresActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProveedorAdapter
    private val lista = mutableListOf<Proveedor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedores)

        recycler = findViewById(R.id.recyclerProveedores)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = ProveedorAdapter(this, lista)
        recycler.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAgregarProveedor).setOnClickListener {
            startActivity(Intent(this, AgregarProveedorActivity::class.java))
        }

        findViewById<FloatingActionButton>(R.id.fabAtras).setOnClickListener {
            finish()
        }

        cargarProveedores()
    }

    override fun onResume() {
        super.onResume()
        cargarProveedores()
    }

    private fun cargarProveedores() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        ProveedorFirestoreRepositorio.obtenerProveedoresPorUsuario(
            usuarioId = uid,
            onComplete = { proveedores ->
                lista.clear()
                lista.addAll(proveedores)
                adapter.notifyDataSetChanged()
            },
            onError = { e ->
                Toast.makeText(this, "Error al obtener proveedores: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
