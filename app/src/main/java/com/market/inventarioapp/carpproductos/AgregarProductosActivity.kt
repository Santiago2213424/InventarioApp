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

    //Crea la pantalla que se ejecutara
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_productos)


        //Obtiene el nombre de la categoria enviada desde la pantalla anteriror con el intent
        val categoria = intent.getStringExtra("categoria") ?: ""
        //Obtiene el UID del usuario auntenticado con firebase. Si no hay sesion queda ""
        val usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        //Busca los elementos de Layout
        val edtNombre = findViewById<EditText>(R.id.edtNombreProducto)
        val edtCantidad = findViewById<EditText>(R.id.edtCantidadProducto)
        val edtPrecio = findViewById<EditText>(R.id.edtPrecioProducto)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarProducto)
        val btnCancelar = findViewById<Button>(R.id.btnCancelarProducto)


        //Captura datos del xml
        btnGuardar.setOnClickListener {
            val nombre = edtNombre.text.toString().trim()
            val cantidad = edtCantidad.text.toString().toIntOrNull() ?: 0
            val precio = edtPrecio.text.toString().toDoubleOrNull() ?: 0.0

            //Validacion
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Nombre requerido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Crea objeto productos con los datos ingresados
            val producto = Producto(
                id = UUID.randomUUID().toString(),
                nombre = nombre,
                cantidad = cantidad,
                precio = precio,
                categoria = categoria,
                usuarioId = usuarioId
            )

            //LLama al metodo del repositorio firebase
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
