package com.market.inventarioapp.carpproductos

data class Producto(
    var id: String = "",
    var nombre: String = "",
    var cantidad: Int = 0,
    var precio: Double = 0.0,
    var categoria: String = "",
    var usuarioId: String = ""
)
