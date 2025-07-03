package com.market.inventarioapp.repositorio

import com.google.firebase.firestore.FirebaseFirestore
import com.market.inventarioapp.carpproveedores.Proveedor

object ProveedorFirestoreRepositorio {

    //Creamos una instancia de Proveedores de FireStore
    private val coleccion = FirebaseFirestore.getInstance().collection("proveedores")

    //Guarda un documento con el id proveedor, con el set lo escribe en FireStore
    fun agregarProveedor(proveedor: Proveedor, onComplete: (Boolean) -> Unit) {
        coleccion.document(proveedor.id)
            .set(proveedor)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    //Consulta todos los documentos donde usuarioId sea igual al del usuario actual.
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

    //Borra el documento con el ID especificado.
    fun eliminarProveedor(id: String, onComplete: (Boolean) -> Unit) {
        coleccion.document(id)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    //Vuelve a escribir el documento con nuevos datos.
    fun actualizarProveedor(proveedor: Proveedor, onComplete: (Boolean) -> Unit) {
        coleccion.document(proveedor.id)
            .set(proveedor)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}
