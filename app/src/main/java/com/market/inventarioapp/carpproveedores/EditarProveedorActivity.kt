package com.market.inventarioapp.carpproveedores

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.textfield.TextInputEditText
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.ProveedorFirestoreRepositorio

class EditarProveedorActivity : AppCompatActivity() {

    private lateinit var edtNombre: TextInputEditText
    private lateinit var edtNumero: TextInputEditText
    private var proveedorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_proveedor)

        edtNombre = findViewById(R.id.edtNombreProveedor)
        edtNumero = findViewById(R.id.edtNumeroProveedor)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarProveedor)
        val btnCancelar = findViewById<Button>(R.id.btnCancelarProveedor)

        proveedorId = intent.getStringExtra("id")
        edtNombre.setText(intent.getStringExtra("nombre"))
        edtNumero.setText(intent.getStringExtra("numero"))

        btnCancelar.setOnClickListener { finish() }

        btnGuardar.setOnClickListener {
            val nombre = edtNombre.text.toString().trim()
            val numero = edtNumero.text.toString().trim()
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener

            if (nombre.isEmpty() || numero.isEmpty()) {
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val proveedor = Proveedor(
                id = proveedorId!!,
                nombre = nombre,
                numero = numero,
                usuarioId = uid
            )

            ProveedorFirestoreRepositorio.actualizarProveedor(proveedor) { exito ->
                if (exito) {
                    Toast.makeText(this, "Proveedor actualizado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
