package com.market.inventarioapp.repositorio

import com.google.firebase.firestore.FirebaseFirestore
import com.market.inventarioapp.carpcategorias.Categoria

object CategoriaFirestoreRepositorio {

    private val firestore = FirebaseFirestore.getInstance()
    private val coleccion = firestore.collection("categorias")

    // Guardar una nueva categoría
    fun agregarCategoria(categoria: Categoria, onComplete: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("categorias")
            .document(categoria.id)
            .set(categoria)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }


    // Obtener todas las categorías del usuario actual
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

    // Eliminar una categoría por ID
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
