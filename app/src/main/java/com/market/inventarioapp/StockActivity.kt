package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)


        val cardBajoStock = findViewById<CardView>(R.id.cardBajoStock)
        val cardAltoStock = findViewById<CardView>(R.id.cardAltoStock)

        cardBajoStock.setOnClickListener {
            val intent = Intent(this, BajoStockActivity::class.java)
            startActivity(intent)
        }

        cardAltoStock.setOnClickListener {
            val intent = Intent(this, AltoStockActivity::class.java)
            startActivity(intent)
        }


       val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}