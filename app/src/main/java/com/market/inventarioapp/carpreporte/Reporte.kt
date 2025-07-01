package com.market.inventarioapp.carpreporte

data class Reporte(
    val nombre: String = "",
    val fecha: String = "",
    val monto: Double = 0.0,
    val esIngreso: Boolean = true,
    val uid: String = ""
)

