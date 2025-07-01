package com.market.inventarioapp.carpreporte

data class Transaccion(
    val tipo: String = "", // "venta" o "gasto"
    val nombre: String = "",
    val cantidad: Double = 0.0,
    val hora: String = "" // formato "HH:mm"
)
