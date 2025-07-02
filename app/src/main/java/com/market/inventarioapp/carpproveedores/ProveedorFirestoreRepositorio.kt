package com.market.inventarioapp.repositorio

import com.google.firebase.firestore.FirebaseFirestore
import com.market.inventarioapp.carpproveedores.Proveedor

object ProveedorFirestoreRepositorio {

    private val coleccion = FirebaseFirestore.getInstance().collection("proveedores")

    fun agregarProveedor(proveedor: Proveedor, onComplete: (Boolean) -> Unit) {
        coleccion.document(proveedor.id)
            .set(proveedor)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun obtenerProveedoresPorUsuario(
        usuarioId: String,
        onComplete: (List<Proveedor>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        coleccion.whereEqualTo("usuarioId", usuarioId)
            .get()
            .addOnSuccessListener { result ->
                val lista = result.documents.mapNotNull { it.toObject(Proveedor::class.java) }
                onComplete(lista)
            }
            .addOnFailureListener { onError(it) }
    }

    fun eliminarProveedor(id: String, onComplete: (Boolean) -> Unit) {
        coleccion.document(id)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun actualizarProveedor(proveedor: Proveedor, onComplete: (Boolean) -> Unit) {
        coleccion.document(proveedor.id)
            .set(proveedor)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}
