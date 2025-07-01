package com.market.inventarioapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ReporteAdapter(private val reportes: MutableList<Reporte>) :
    RecyclerView.Adapter<ReporteAdapter.ReporteViewHolder>() {

    class ReporteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreReporte)
        val txtFecha: TextView = itemView.findViewById(R.id.txtFechaReporte)
        val txtMonto: TextView = itemView.findViewById(R.id.txtMontoReporte)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReporteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reporte, parent, false)
        return ReporteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReporteViewHolder, position: Int) {
        val reporte = reportes[position]
        holder.txtNombre.text = "Producto: ${reporte.nombre}"
        holder.txtFecha.text = "Fecha: ${reporte.fecha}"
        holder.txtMonto.text = "Monto: $${String.format("%.2f", reporte.monto)}"

        val color = if (reporte.esIngreso)
            ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark)
        else
            ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_dark)

        holder.txtMonto.setTextColor(color)
    }

    override fun getItemCount(): Int = reportes.size

    // Este método permite actualizar la lista cuando se filtra
    fun actualizarLista(nuevaLista: List<Reporte>) {
        reportes.clear()
        reportes.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}