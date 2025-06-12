package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProveedorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedor)

        // Configurar Toolbar si tienes uno
        val toolbar = findViewById<Toolbar>(R.id.toolbarProveedores)
        setSupportActionBar(toolbar)

        // FloatingButton para agregar proveedor
        val fabAgregarProveedor = findViewById<FloatingActionButton>(R.id.fabAgregarProveedor)
        fabAgregarProveedor.setOnClickListener {
            val intent = Intent(this, AgregarProveedorActivity::class.java)
            startActivity(intent)
        }

        // FloatingButton para regresar
        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
