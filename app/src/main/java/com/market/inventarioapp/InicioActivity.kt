package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.time.Instant

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)


        val cardSalir = findViewById<CardView>(R.id.cardSalir)
        val cardinventario = findViewById<CardView>(R.id.cardinventario)

        cardSalir.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        cardinventario.setOnClickListener {
            startActivity(Intent(this, InventarioActivity::class.java))
        }
    }
}