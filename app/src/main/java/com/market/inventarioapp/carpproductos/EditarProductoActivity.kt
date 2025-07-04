package com.market.inventarioapp.carpproductos

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.ProductoFirestoreRepositorio

class EditarProductoActivity : AppCompatActivity() {

    //
    private lateinit var edtNombre: TextInputEditText
    private lateinit var edtCantidad: TextInputEditText
    private lateinit var edtPrecio: TextInputEditText
    private var producto: Producto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        // Configurar toolbar, cambia su título
        val toolbar = findViewById<Toolbar>(R.id.toolbarEditarProducto)
        toolbar.title = "Editar Producto"
        setSupportActionBar(toolbar)

        // Enlazas los elementos del XML
        edtNombre = findViewById(R.id.edtNombreProductoEditar)
        edtCantidad = findViewById(R.id.edtCantidadProductoEditar)
        edtPrecio = findViewById(R.id.edtPrecioProductoEditar)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarProductoEditar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelarProductoEditar)

        // Obtener ID del producto desde el intent
        val productoId = intent.getStringExtra("productoId")

        //Validacion, si no llega ningún ID, se cierra la pantalla
        if (productoId.isNullOrEmpty()) {
            // Ya no mostramos el mensaje
            finish()
            return
        }

        // Cargar producto desde Firestore
        ProductoFirestoreRepositorio.obtenerPorId(productoId) { resultado ->
            if (resultado != null) {
                producto = resultado
                edtNombre.setText(producto!!.nombre)
                edtCantidad.setText(producto!!.cantidad.toString())
                edtPrecio.setText(producto!!.precio.toString())
            } else {
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Guardar cambios
        btnGuardar.setOnClickListener {
            val nombre = edtNombre.text.toString().trim()
            val cantidad = edtCantidad.text.toString().toIntOrNull()
            val precio = edtPrecio.text.toString().toDoubleOrNull()

            if (nombre.isEmpty() || cantidad == null || precio == null) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val actualizado = producto?.copy(
                nombre = nombre,
                cantidad = cantidad,
                precio = precio
            )

            if (actualizado != null) {
                ProductoFirestoreRepositorio.editarProducto(actualizado) { exito ->
                    if (exito) {
                        Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al actualizar producto", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Cancelar edición
        btnCancelar.setOnClickListener {
            finish()
        }
    }
}
