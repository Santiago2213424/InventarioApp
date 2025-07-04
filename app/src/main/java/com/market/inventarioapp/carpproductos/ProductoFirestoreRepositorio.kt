package com.market.inventarioapp.repositorio

import com.google.firebase.firestore.FirebaseFirestore
import com.market.inventarioapp.carpproductos.Producto
import java.util.*

object ProductoFirestoreRepositorio {

    private val db = FirebaseFirestore.getInstance()
    private val productosCollection = db.collection("productos")

    fun agregarProducto(producto: Producto, callback: (Boolean) -> Unit) {
        val idGenerado = UUID.randomUUID().toString()
        val productoConId = producto.copy(id = idGenerado)
        productosCollection.document(idGenerado).set(productoConId)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun obtenerPorUsuarioYCategoria(uid: String, categoria: String, callback: (List<Producto>) -> Unit) {
        productosCollection
            .whereEqualTo("usuarioId", uid)
            .whereEqualTo("categoria", categoria)
            .get()
            .addOnSuccessListener { result ->
                val productos = result.mapNotNull { doc ->
                    val producto = doc.toObject(Producto::class.java)
                    producto?.copy(id = doc.id)
                }
                callback(productos)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun obtenerPorId(id: String, callback: (Producto?) -> Unit) {
        productosCollection.document(id).get()
            .addOnSuccessListener { doc ->
                val producto = doc.toObject(Producto::class.java)
                callback(producto)
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun editarProducto(producto: Producto, callback: (Boolean) -> Unit) {
        productosCollection.document(producto.id).set(producto)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun eliminarProducto(id: String, callback: (Boolean) -> Unit) {
        productosCollection.document(id).delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun obtenerTodosPorUsuario(uid: String, callback: (List<Producto>) -> Unit) {
        productosCollection
            .whereEqualTo("usuarioId", uid)
            .get()
            .addOnSuccessListener { result ->
                val productos = result.mapNotNull { doc ->
                    doc.toObject(Producto::class.java)?.copy(id = doc.id)
                }
                callback(productos)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun obtenerPorUsuario(uid: String, callback: (List<Producto>) -> Unit) {
        productosCollection
            .whereEqualTo("usuarioId", uid)
            .get()
            .addOnSuccessListener { result ->
                val productos = result.mapNotNull { doc ->
                    val producto = doc.toObject(Producto::class.java).copy(id = doc.id)
                    producto
                }
                callback(productos)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }
}
