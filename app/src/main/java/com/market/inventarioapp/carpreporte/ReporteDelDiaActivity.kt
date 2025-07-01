package com.market.inventarioapp.carpreporte

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.market.inventarioapp.R
import java.text.SimpleDateFormat
import java.util.*

class ReporteDelDiaActivity : AppCompatActivity() {

    private lateinit var recyclerReportes: RecyclerView
    private lateinit var txtTotales: TextView
    private lateinit var adapter: ReporteAdapter

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val fechaHoy = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reporte_dia)

        // Inicializar vistas
        recyclerReportes = findViewById(R.id.recyclerReporteDia)
        txtTotales = findViewById(R.id.txtTotalesReporteDia)

        // Configurar RecyclerView
        recyclerReportes.layoutManager = LinearLayoutManager(this)
        adapter = ReporteAdapter(mutableListOf())
        recyclerReportes.adapter = adapter

        // Cargar datos
        cargarReportesDelDia()
    }

    private fun cargarReportesDelDia() {
        db.collection("reportes")
            .whereEqualTo("uid", uid)
            .whereEqualTo("fecha", fechaHoy)
            .get()
            .addOnSuccessListener { docs ->
                val lista = docs.mapNotNull { it.toObject(Reporte::class.java) }

                if (lista.isEmpty()) {
                    Toast.makeText(this, "No hay reportes para hoy", Toast.LENGTH_SHORT).show()
                }

                adapter.actualizarLista(lista)

                val totalGanancias = lista.filter { it.esIngreso }.sumOf { it.monto }
                val totalGastos = lista.filter { !it.esIngreso }.sumOf { it.monto }

                txtTotales.text = "Hoy - Ganancias: S/%.2f | Gastos: S/%.2f".format(totalGanancias, totalGastos)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al obtener los reportes", Toast.LENGTH_SHORT).show()
            }

        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            startActivity(Intent(this, RegistrarReporteActivity::class.java))
            finish() // Cierra la pantalla actual
        }
    }
}
