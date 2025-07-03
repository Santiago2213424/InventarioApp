package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
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

        //Crea dos variables, mostraran los datos del Usuario
        val txtBienvenido = findViewById<TextView>(R.id.txtBienvenida)
        val txtCorreo = findViewById<TextView>(R.id.txtCorreo)

        //Obtiene el ID unico del usuario loguado, si es null no hay sesion activa
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            //Accede a la collection Usuarios y selecciona el documento que el id sea igual uid del usuario
            val db = FirebaseFirestore.getInstance()
            val usuariosRef = db.collection("usuarios").document(uid)

            //Luego conuslta si el usuario existe
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

        //cada cardview representa un boton visual para navegar a otras pantallas
        val cardSalir = findViewById<CardView>(R.id.cardSalir)
        val cardinventario = findViewById<CardView>(R.id.cardinventario)
        val cardStock = findViewById<CardView>(R.id.cardStock)
        val cardProveedor = findViewById<CardView>(R.id.cardProveedor)
        val cardReporte = findViewById<CardView>(R.id.cardReporte)
        val btnAbrirMapa = findViewById<ImageButton>(R.id.btnAbrirMapa)


        cardSalir.setOnClickListener {
            //Cierra sesion de usuario actual -- Borra el token de la sesion local
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            //Inicio una nueva tarea luego borra las actividades anteriores en la pila de tareas
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

        btnAbrirMapa.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}
