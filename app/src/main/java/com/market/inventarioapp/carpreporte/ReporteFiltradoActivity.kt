package com.market.inventarioapp.carpreporte

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.market.inventarioapp.R
import java.text.SimpleDateFormat
import java.util.*

class ReporteFiltradoActivity : AppCompatActivity() {

    private lateinit var etFechaFiltro: EditText
    private lateinit var btnFiltrar: Button
    private lateinit var recyclerReportes: RecyclerView
    private lateinit var txtTotales: TextView
    private lateinit var adapter: ReporteAdapter

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_reportes) // Usa tu XML actual

        etFechaFiltro = findViewById(R.id.etFechaFiltro)
        btnFiltrar = findViewById(R.id.btnFiltrar)
        recyclerReportes = findViewById(R.id.recyclerReportes)
        txtTotales = findViewById(R.id.txtTotales)
        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)

        recyclerReportes.layoutManager = LinearLayoutManager(this)
        adapter = ReporteAdapter(mutableListOf())
        recyclerReportes.adapter = adapter

        //  Mostrar DatePicker al tocar el EditText
        etFechaFiltro.showSoftInputOnFocus = false
        etFechaFiltro.setOnClickListener {
            mostrarDatePicker()
        }

        // Botón FILTRAR
        btnFiltrar.setOnClickListener {
            val fecha = etFechaFiltro.text.toString().trim()
            if (fecha.isEmpty()) {
                Toast.makeText(this, "Selecciona una fecha", Toast.LENGTH_SHORT).show()
            } else {
                cargarReportesPorFecha(fecha)
            }
        }

        //  Botón ATRÁS
        fabAtras.setOnClickListener {
            finish()
        }
    }

    private fun mostrarDatePicker() {
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog(this,
            { _, year, month, day ->
                val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                calendar.set(year, month, day)
                etFechaFiltro.setText(formato.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()
    }

    private fun cargarReportesPorFecha(fecha: String) {
        db.collection("reportes")
            .whereEqualTo("uid", uid)
            .whereEqualTo("fecha", fecha)
            .get()
            .addOnSuccessListener { documentos ->
                val lista = documentos.mapNotNull { it.toObject(Reporte::class.java) }
                adapter.actualizarLista(lista)

                val ingresos = lista.filter { it.esIngreso }.sumOf { it.monto }
                val gastos = lista.filter { !it.esIngreso }.sumOf { it.monto }

                txtTotales.text = "Ganancias: S/%.2f   |   Gastos: S/%.2f".format(ingresos, gastos)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar reportes", Toast.LENGTH_SHORT).show()
            }
    }
}
