package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)


        val cardSalir = findViewById<CardView>(R.id.cardSalir)
        val cardinventario = findViewById<CardView>(R.id.cardinventario)
        val cardIngresos = findViewById<CardView>(R.id.cardIngresos)
        val cardProveedor = findViewById<CardView>(R.id.cardProveedor)

        cardSalir.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        cardinventario.setOnClickListener {
            startActivity(Intent(this, CategoriasActivity::class.java))
        }

        cardIngresos.setOnClickListener {
            startActivity(Intent(this, IngresosActivity::class.java))
        }
        cardProveedor.setOnClickListener {
            startActivity(Intent(this, ProveedorActivity::class.java ))
        }
    }
}