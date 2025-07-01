package com.market.inventarioapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class AgregarReporteActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etMonto: EditText
    private lateinit var rgTipo: RadioGroup
    private lateinit var btnGuardar: Button
    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_reporte)

        etNombre = findViewById(R.id.etNombre)
        etMonto = findViewById(R.id.etMonto)
        rgTipo = findViewById(R.id.rgTipo)
        btnGuardar = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val monto = etMonto.text.toString().toDoubleOrNull()
            val esIngreso = rgTipo.checkedRadioButtonId == R.id.rbIngreso
            val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            if (nombre.isEmpty() || monto == null) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val reporte = Reporte(
                nombre = nombre,
                monto = monto,
                fecha = fecha,
                esIngreso = esIngreso,
                uid = uid
            )

            db.collection("reportes")
                .add(reporte)
                .addOnSuccessListener {
                    Toast.makeText(this, "Reporte guardado", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
