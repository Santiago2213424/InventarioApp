package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReporteActivity : AppCompatActivity() {

    private lateinit var recyclerReportes: RecyclerView
    private lateinit var adapter: ReporteAdapter
    private lateinit var listaReportes: List<Reporte>
    private lateinit var etFechaFiltro: EditText
    private lateinit var txtTotales: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportes)

        // Inicializar views
        recyclerReportes = findViewById(R.id.recyclerReportes)
        etFechaFiltro = findViewById(R.id.etFechaFiltro)
        txtTotales = findViewById(R.id.txtTotales)
        val btnFiltrar = findViewById<Button>(R.id.btnFiltrar)
        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)

        // Datos de ejemplo
        listaReportes = listOf(
            Reporte("Galletas Oreo", "2025-06-25", 50.0, true),
            Reporte("Compra de arroz", "2025-06-24", 90.0, false),
            Reporte("Leche Gloria", "2025-06-23", 35.0, true),
            Reporte("Pago de transporte", "2025-06-22", 20.0, false),
            Reporte("Chocolates", "2025-06-21", 70.0, true)
        )

        // Configurar RecyclerView
        recyclerReportes.layoutManager = LinearLayoutManager(this)
        adapter = ReporteAdapter(listaReportes.toMutableList())
        recyclerReportes.adapter = adapter

        // Mostrar totales iniciales
        actualizarTotales(listaReportes)

        // Botón FILTRAR
        btnFiltrar.setOnClickListener {
            val fecha = etFechaFiltro.text.toString().trim()
            if (fecha.isEmpty()) {
                adapter.actualizarLista(listaReportes)
                actualizarTotales(listaReportes)
            } else {
                val filtrados = listaReportes.filter { it.fecha == fecha }
                adapter.actualizarLista(filtrados)
                actualizarTotales(filtrados)
            }
        }

        // Botón ATRÁS
        fabAtras.setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
            finish()
        }
    }

    private fun actualizarTotales(lista: List<Reporte>) {
        val ganancias = lista.filter { it.esIngreso }.sumOf { it.monto }
        val gastos = lista.filter { !it.esIngreso }.sumOf { it.monto }
        txtTotales.text = "Ganancias: $%.2f   |   Gastos: $%.2f".format(ganancias, gastos)
    }
}