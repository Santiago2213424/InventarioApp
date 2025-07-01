package com.market.inventarioapp.carpproductos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.ProductoFirestoreRepositorio

import java.util.*

class AgregarProductosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_productos)

        val categoria = intent.getStringExtra("categoria") ?: ""
        val usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        val edtNombre = findViewById<EditText>(R.id.edtNombreProducto)
        val edtCantidad = findViewById<EditText>(R.id.edtCantidadProducto)
        val edtPrecio = findViewById<EditText>(R.id.edtPrecioProducto)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarProducto)
        val btnCancelar = findViewById<Button>(R.id.btnCancelarProducto)

        btnGuardar.setOnClickListener {
            val nombre = edtNombre.text.toString().trim()
            val cantidad = edtCantidad.text.toString().toIntOrNull() ?: 0
            val precio = edtPrecio.text.toString().toDoubleOrNull() ?: 0.0

            if (nombre.isEmpty()) {
                Toast.makeText(this, "Nombre requerido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val producto = Producto(
                id = UUID.randomUUID().toString(),
                nombre = nombre,
                cantidad = cantidad,
                precio = precio,
                categoria = categoria,
                usuarioId = usuarioId
            )

            ProductoFirestoreRepositorio.agregarProducto(producto) { exito ->
                if (exito) {
                    Toast.makeText(this, "Producto guardado", Toast.LENGTH_SHORT).show()
                    finish() // Regresa a InventarioActivity
                } else {
                    Toast.makeText(this, "Error al guardar producto", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnCancelar.setOnClickListener {
            finish() // Simplemente cierra y vuelve a la pantalla anterior
        }
    }
}
