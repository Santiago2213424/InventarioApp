package com.market.inventarioapp.carpproveedores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.textfield.TextInputEditText
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.ProveedorFirestoreRepositorio
import java.util.*

class AgregarProveedorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_proveedor)

        val edtNombre = findViewById<TextInputEditText>(R.id.edtNombreProveedor)
        val edtNumero = findViewById<TextInputEditText>(R.id.edtNumeroProveedor)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarProveedor)
        val btnCancelar = findViewById<Button>(R.id.btnCancelarProveedor)

        //Cierra la actividad sin guardar
        btnCancelar.setOnClickListener {
            finish()
        }


        btnGuardar.setOnClickListener {
            //Llena los campos de texto
            val nombre = edtNombre.text.toString().trim()
            val numero = edtNumero.text.toString().trim()

            if (nombre.isEmpty() || numero.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Obtiene el uid actual con FirebaseAuth.getInstance().currentUser?.uid.
            val uid = FirebaseAuth.getInstance().currentUser?.uid //FirebaseAuth proporciona el ID del usuario actualmente autenticado.
            if (uid != null) {
                val proveedor = Proveedor(
                    id = UUID.randomUUID().toString(),
                    nombre = nombre,
                    numero = numero,
                    usuarioId = uid
                )

                ProveedorFirestoreRepositorio.agregarProveedor(proveedor) { exito ->
                    if (exito) {
                        Toast.makeText(this, "Proveedor guardado", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
