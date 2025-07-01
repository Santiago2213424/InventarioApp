package com.market.inventarioapp.carpproductos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.market.inventarioapp.R

class ProductoAdapter(
    private var lista: MutableList<Producto>,
    private val onEditar: (Producto) -> Unit,
    private val onEliminar: (Producto) -> Unit,
    private val mostrarCategoria: Boolean = false // Por defecto no mostrar categoría
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtCantidad: TextView = itemView.findViewById(R.id.txtCantidad)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
        val txtCategoria: TextView = itemView.findViewById(R.id.txtCategoria) // NUEVO
        val btnEditar: ImageView = itemView.findViewById(R.id.btnEditarProducto)
        val btnEliminar: ImageView = itemView.findViewById(R.id.btnEliminarProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = lista[position]

        holder.txtNombre.text = producto.nombre
        holder.txtCantidad.text = producto.cantidad.toString()
        holder.txtPrecio.text = "S/ ${producto.precio}"

        // Solo mostrar categoría si se indica
        if (mostrarCategoria) {
            holder.txtCategoria.visibility = View.VISIBLE
            holder.txtCategoria.text = producto.categoria
        } else {
            holder.txtCategoria.visibility = View.GONE
        }

        // Mostrar botones con long click
        holder.btnEditar.visibility = View.GONE
        holder.btnEliminar.visibility = View.GONE

        holder.itemView.setOnClickListener {
            holder.btnEditar.visibility = View.GONE
            holder.btnEliminar.visibility = View.GONE
        }

        holder.itemView.setOnLongClickListener {
            holder.btnEditar.visibility = View.VISIBLE
            holder.btnEliminar.visibility = View.VISIBLE
            true
        }

        holder.btnEditar.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Edita tu producto", Toast.LENGTH_SHORT).show()
            onEditar(producto)
        }

        holder.btnEliminar.setOnClickListener {
            onEliminar(producto)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<Producto>) {
        lista = nuevaLista.toMutableList()
        notifyDataSetChanged()
    }
}
