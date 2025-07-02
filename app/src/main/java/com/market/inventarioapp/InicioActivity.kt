package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.market.inventarioapp.carpcategorias.CategoriasActivity
import com.market.inventarioapp.carpproductos.StockActivity
import com.market.inventarioapp.carpproveedores.ProveedoresActivity
import com.market.inventarioapp.carpreporte.RegistrarReporteActivity

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val txtBienvenido = findViewById<TextView>(R.id.txtBienvenida)
        val txtCorreo = findViewById<TextView>(R.id.txtCorreo)

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            val usuariosRef = db.collection("usuarios").document(uid)

            usuariosRef.get()
                .addOnSuccessListener { documento ->
                    if (documento != null && documento.exists()) {
                        val nombre = documento.getString("nombre") ?: "Usuario"
                        val correo = documento.getString("correo") ?: "Correo no disponible"

                        txtBienvenido.text = "Bienvenido $nombre"
                        txtCorreo.text = "Correo: $correo"
                    } else {
                        txtBienvenido.text = "Bienvenido"
                        txtCorreo.text = "Correo no disponible"
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al obtener los datos", Toast.LENGTH_SHORT).show()
                }
        }

        val cardSalir = findViewById<CardView>(R.id.cardSalir)
        val cardinventario = findViewById<CardView>(R.id.cardinventario)
        val cardStock = findViewById<CardView>(R.id.cardStock)
        val cardProveedor = findViewById<CardView>(R.id.cardProveedor)
        val cardReporte = findViewById<CardView>(R.id.cardReporte)

        cardSalir.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        cardinventario.setOnClickListener {
            startActivity(Intent(this, CategoriasActivity::class.java))
        }

        cardStock.setOnClickListener {
            startActivity(Intent(this, StockActivity::class.java))
        }

        cardProveedor.setOnClickListener {
            startActivity(Intent(this, ProveedoresActivity::class.java))
        }

        cardReporte.setOnClickListener {
            startActivity(Intent(this, RegistrarReporteActivity::class.java))
        }
    }
}
