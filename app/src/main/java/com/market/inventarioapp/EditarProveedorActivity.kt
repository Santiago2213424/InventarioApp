package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EditarProveedorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_proveedor)

        val btnCancelarProveedor = findViewById<Button>(R.id.btnCancelarProveedor)
        val btnGuardarProveedor = findViewById<Button>(R.id.btnGuardarProveedor)

        btnCancelarProveedor.setOnClickListener {
            startActivity(Intent(this, ProveedorActivity::class.java))
        }

        btnGuardarProveedor.setOnClickListener {
            startActivity(Intent(this, ProveedorActivity::class.java))
        }

    }

}