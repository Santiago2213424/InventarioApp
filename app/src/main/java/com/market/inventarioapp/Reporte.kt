package com.market.inventarioapp

data class Reporte(
    val nombre: String,
    val fecha: String,
    val monto: Double,
    val esIngreso: Boolean // true = ganancia, false = gasto
)