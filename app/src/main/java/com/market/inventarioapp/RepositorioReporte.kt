package com.market.inventarioapp

import com.google.firebase.firestore.FirebaseFirestore
import com.market.inventarioapp.carpreporte.Reporte

object RepositorioReporte {

    private val db = FirebaseFirestore.getInstance()

    fun guardarReporte(reporte: Reporte, onResultado: (Boolean) -> Unit) {
        db.collection("reportes")
            .add(reporte)
            .addOnSuccessListener { onResultado(true) }
            .addOnFailureListener { onResultado(false) }
    }
}