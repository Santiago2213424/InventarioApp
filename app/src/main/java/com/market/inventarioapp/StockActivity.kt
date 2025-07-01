package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.market.inventarioapp.carpproductos.AltoStockActivity
import com.market.inventarioapp.carpproductos.BajoStockActivity

class StockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        // Botón Bajo Stock
        findViewById<CardView>(R.id.cardBajoStock).setOnClickListener {
            startActivity(Intent(this, BajoStockActivity::class.java))
        }

        // Botón Alto Stock
        findViewById<CardView>(R.id.cardAltoStock).setOnClickListener {
            startActivity(Intent(this, AltoStockActivity::class.java))
        }

        // Botón Atrás
        findViewById<FloatingActionButton>(R.id.fabAtras).setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
            finish()
        }
    }
}
