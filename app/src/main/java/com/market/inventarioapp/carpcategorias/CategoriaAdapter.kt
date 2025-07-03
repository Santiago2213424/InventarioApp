package com.market.inventarioapp.carpcategorias

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
import com.market.inventarioapp.carpproductos.InventarioActivity
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.CategoriaFirestoreRepositorio

//Es el adaptador para llenar el RecyclerView con las categorias
class CategoriaAdapter(
    private val context: Context,
    private var listaCategorias: MutableList<Categoria>
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    //Asocia elementos del XML item a cada tarjeta
    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCategoria: TextView = itemView.findViewById(R.id.txtCategoria)
        val btnEditar: ImageView = itemView.findViewById(R.id.btnEditar)
        val btnEliminar: ImageView = itemView.findViewById(R.id.btnEliminar)
    }

    //Crea el dieseño de cada item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(vista)
    }

    //Coloca el nombre de la categoria en el textview
    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = listaCategorias[position]
        holder.txtCategoria.text = categoria.nombre

        // Oculta los botones y va al inventario filtrado  por categoria
        holder.itemView.setOnClickListener {
            holder.btnEditar.visibility = View.GONE
            holder.btnEliminar.visibility = View.GONE
            val intent = Intent(context, InventarioActivity::class.java)
            intent.putExtra("categoria", categoria.nombre)
            context.startActivity(intent)
        }

        // Clic largo: mostrar botones editar y eliminar
        holder.itemView.setOnLongClickListener {
            holder.btnEditar.visibility = View.VISIBLE
            holder.btnEliminar.visibility = View.VISIBLE
            true
        }

        // Botón Editar, envia los datos a la pantalla de edicion
        holder.btnEditar.setOnClickListener {
            val intent = Intent(context, EditarCategoriaActivity::class.java)
            intent.putExtra("categoriaId", categoria.id)
            intent.putExtra("categoriaNombre", categoria.nombre)
            context.startActivity(intent)
        }

        // Muestra confirmación y elimina la categoría de Firestore y de la lista local.
        holder.btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar categoría")
            builder.setMessage("¿Estás seguro de eliminar la categoría '${categoria.nombre}'?")
            builder.setPositiveButton("Sí") { dialog, _ ->
                CategoriaFirestoreRepositorio.eliminarCategoria(categoria) { exito ->
                    if (exito) {
                        val pos = holder.adapterPosition
                        if (pos != RecyclerView.NO_POSITION) {
                            listaCategorias.removeAt(pos)
                            notifyItemRemoved(pos)
                            notifyItemRangeChanged(pos, listaCategorias.size)
                        }
                        Toast.makeText(context, "Categoría eliminada", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            builder.create().show()
        }
    }

    //Cuantos elementos mostrara en el RecyclerView, devuelve el tamaño actual de esa lista
    override fun getItemCount(): Int = listaCategorias.size
}
