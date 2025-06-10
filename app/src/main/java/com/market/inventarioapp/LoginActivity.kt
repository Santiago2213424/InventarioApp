package com.market.inventarioapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnIniciaSesion = findViewById<Button>(R.id.btnIniciaSesion)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

       btnRegistrar.setOnClickListener {
          //Mensaje flotante en la parte inferior de la pantalla
          //Toast.makeText(this,"",Toast.LENGTH_SHORT).show()
           startActivity(Intent(this,RegisterActivity::class.java))
       }

       btnIniciaSesion.setOnClickListener {
           startActivity(Intent(this, InicioActivity::class.java))
           Toast.makeText(this, "Iniciaste Sesion", Toast.LENGTH_SHORT).show()
       }
    }
}