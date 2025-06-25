package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProveedorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proveedor)

        val btnEditarProveedor = findViewById<Button>(R.id.btnEditarProveedor)
        val btnEliminarProveedor = findViewById<Button>(R.id.btnEliminarProveedor)

        // Botón EDITAR
        btnEditarProveedor.setOnClickListener {
            startActivity(Intent(this, EditarProveedorActivity::class.java))
        }

        // Botón ELIMINAR
        btnEliminarProveedor.setOnClickListener {
            // Mostrar cuadro de confirmación
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Estás seguro que quieres eliminar este proveedor?")

            builder.setPositiveButton("Sí") { dialog, _ ->
                // Aquí iría la lógica real para eliminar el proveedor
                dialog.dismiss()
                // Por ejemplo, podrías mostrar un Toast
                android.widget.Toast.makeText(this, "Proveedor eliminado", android.widget.Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }

        // Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarProveedores)
        setSupportActionBar(toolbar)

        // FAB Agregar proveedor
        val fabAgregarProveedor = findViewById<FloatingActionButton>(R.id.fabAgregarProveedor)
        fabAgregarProveedor.setOnClickListener {
            val intent = Intent(this, AgregarProveedorActivity::class.java)
            startActivity(intent)
        }

        // FAB Atrás
        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
