package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.market.inventarioapp.CategoriasActivity

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)


        val cardSalir = findViewById<CardView>(R.id.cardSalir)
        val cardinventario = findViewById<CardView>(R.id.cardinventario)
        val cardStock = findViewById<CardView>(R.id.cardStock)
        val cardProveedor = findViewById<CardView>(R.id.cardProveedor)
        val cardReporte = findViewById<CardView>(R.id.cardReporte)

        cardSalir.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        cardinventario.setOnClickListener {
            startActivity(Intent(this, CategoriasActivity::class.java))
        }
        cardStock.setOnClickListener {
            startActivity(Intent(this, StockActivity::class.java))
        }
        cardProveedor.setOnClickListener {
            startActivity(Intent(this, ProveedorActivity::class.java ))
        }
        cardReporte.setOnClickListener {
            startActivity(Intent(this, ReporteActivity::class.java))
        }
    }
}