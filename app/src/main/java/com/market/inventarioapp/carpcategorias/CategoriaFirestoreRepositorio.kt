package com.market.inventarioapp.repositorio

import com.google.firebase.firestore.FirebaseFirestore
import com.market.inventarioapp.carpcategorias.Categoria

// Singleton para manejar todas las operaciones de Firestore relacionadas con categorías
object CategoriaFirestoreRepositorio {

    //Conecta con la coneccion en firebase
    private val firestore = FirebaseFirestore.getInstance()
    private val coleccion = firestore.collection("categorias")

    // Guarda el documento con el id como clave
    fun agregarCategoria(categoria: Categoria, onComplete: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("categorias")
            .document(categoria.id)
            .set(categoria)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }


    // Trae todas las categorías filtradas por usuarioId
    fun obtenerCategoriasPorUsuario(
        usuarioId: String,
        onComplete: (List<Categoria>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        coleccion.whereEqualTo("usuarioId", usuarioId)
            .get()
            .addOnSuccessListener { result ->
                val lista = result.documents.mapNotNull { it.toObject(Categoria::class.java) }
                onComplete(lista)
            }
            .addOnFailureListener { onError(it) }
    }

    // Usan id para acceder directamente al documento
    fun eliminarCategoria(categoria: Categoria, onComplete: (Boolean) -> Unit) {
        coleccion.document(categoria.id)
            .delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    // Actualizar una categoría
    fun actualizarCategoria(categoria: Categoria, onComplete: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("categorias")
            .document(categoria.id)
            .set(categoria)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}
