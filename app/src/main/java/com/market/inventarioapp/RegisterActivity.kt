package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        btnRegistrarse.setOnClickListener {
            Toast.makeText(this,"Registro Exitoso", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
        }

        btnCancelar.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}