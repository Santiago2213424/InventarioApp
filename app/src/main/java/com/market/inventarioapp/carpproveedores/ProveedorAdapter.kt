package com.market.inventarioapp.carpproveedores

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.market.inventarioapp.R
import com.market.inventarioapp.repositorio.ProveedorFirestoreRepositorio

class ProveedorAdapter(
    private val context: Context,
    private var lista: MutableList<Proveedor>
) : RecyclerView.Adapter<ProveedorAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreProveedor)
        val txtNumero: TextView = itemView.findViewById(R.id.txtNumeroProveedor)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditarProveedor)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminarProveedor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_proveedor, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val proveedor = lista[position]
        holder.txtNombre.text = "Proveedor: ${proveedor.nombre}"
        holder.txtNumero.text = "Número: ${proveedor.numero}"

        holder.btnEditar.setOnClickListener {
            val intent = Intent(context, EditarProveedorActivity::class.java)
            intent.putExtra("id", proveedor.id)
            intent.putExtra("nombre", proveedor.nombre)
            intent.putExtra("numero", proveedor.numero)
            context.startActivity(intent)
        }

        holder.btnEliminar.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Eliminar proveedor")
                setMessage("¿Estás seguro de eliminar a '${proveedor.nombre}'?")
                setPositiveButton("Sí") { dialog, _ ->
                    ProveedorFirestoreRepositorio.eliminarProveedor(proveedor.id) { exito ->
                        if (exito) {
                            lista.removeAt(holder.adapterPosition)
                            notifyItemRemoved(holder.adapterPosition)
                            Toast.makeText(context, "Proveedor eliminado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                        }
                    }
                    dialog.dismiss()
                }
                setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                create().show()
            }
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizarLista(nuevaLista: List<Proveedor>) {
        lista = nuevaLista.toMutableList()
        notifyDataSetChanged()
    }
}
