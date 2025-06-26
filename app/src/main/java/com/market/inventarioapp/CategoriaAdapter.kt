package com.market.inventarioapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class CategoriaAdapter(
    private val context: Context,
    private val listaCategorias: List<Categoria>
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCategoria: TextView = itemView.findViewById(R.id.txtCategoria)
        val btnEditar: ImageView = itemView.findViewById(R.id.btnEditar)
        val btnEliminar: ImageView = itemView.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = listaCategorias[position]
        holder.txtCategoria.text = categoria.nombre

        // Clic corto: ir a Inventario
        holder.itemView.setOnClickListener {
            holder.btnEditar.visibility = View.GONE
            holder.btnEliminar.visibility = View.GONE
            Toast.makeText(context, "Agrega tus productos", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, InventarioActivity::class.java)
            intent.putExtra("categoria", categoria.nombre)
            context.startActivity(intent)
        }

        // Clic largo: mostrar botones
        holder.itemView.setOnLongClickListener {
            holder.btnEditar.visibility = View.VISIBLE
            holder.btnEliminar.visibility = View.VISIBLE
            true
        }

        // Botón Editar
        holder.btnEditar.setOnClickListener {
            Toast.makeText(context, "Editar categoría", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, EditarCategoriaActivity::class.java)
            context.startActivity(intent)
        }

        // Botón Eliminar
        holder.btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar categoría")
            builder.setMessage("¿Estás seguro de eliminar la categoría '${categoria.nombre}'?")
            builder.setPositiveButton("Sí") { dialog, _ ->
                Toast.makeText(context, "Categoría eliminada", Toast.LENGTH_SHORT).show()
                holder.btnEditar.visibility = View.GONE
                holder.btnEliminar.visibility = View.GONE
                dialog.dismiss()
                // Aquí puedes agregar lógica real de eliminación si haces la lista mutable
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }
    }

    override fun getItemCount(): Int = listaCategorias.size
}