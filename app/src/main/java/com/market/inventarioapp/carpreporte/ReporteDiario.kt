package com.market.inventarioapp.carpreporte

data class ReporteDiario(
    val id: String = "",
    val fecha: String = "", // formato "YYYY-MM-DD"
    val totalGanancias: Double = 0.0,
    val totalGastos: Double = 0.0,
    val detalle: List<Transaccion> = listOf()
)
