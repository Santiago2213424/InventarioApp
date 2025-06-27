package com.market.inventarioapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProveedorAdapter(
    private val proveedores: List<Proveedor>,
    private val onEditarClick: (Proveedor) -> Unit,
    private val onEliminarClick: (Proveedor) -> Unit) :
    RecyclerView.Adapter<ProveedorAdapter.ProveedorViewHolder>() {

    class ProveedorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombreProveedor: TextView = view.findViewById(R.id.txtNombreProveedor)
        val btnEditar: Button = view.findViewById(R.id.btnEditarProveedor)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarProveedor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProveedorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_proveedor, parent, false)
        return ProveedorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProveedorViewHolder, position: Int) {
        val proveedor = proveedores[position]
        holder.txtNombreProveedor.text = "Proveedor: ${proveedor.nombre}"

        holder.btnEditar.setOnClickListener { onEditarClick(proveedor) }
        holder.btnEliminar.setOnClickListener { onEliminarClick(proveedor) }
    }

    override fun getItemCount(): Int = proveedores.size
}