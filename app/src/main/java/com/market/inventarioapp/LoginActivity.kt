package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val txtCorreo = findViewById<TextInputEditText>(R.id.txtCorreo)
        val txtContraseña = findViewById<TextInputEditText>(R.id.txtContraseña)
        val btnIniciaSesion = findViewById<Button>(R.id.btnIniciaSesion)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnIniciaSesion.setOnClickListener {
            val correo = txtCorreo.text.toString().trim()
            val contraseña = txtContraseña.text.toString().trim()

            if (correo.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Iniciaste sesión", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, InicioActivity::class.java))
                        finish()
                    } else {
                        val errorTraducido = traducirErrorFirebase(task.exception?.message)
                        Toast.makeText(this, errorTraducido, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    // Función para traducir errores comunes de Firebase
    private fun traducirErrorFirebase(error: String?): String {
        return when {
            error?.contains("There is no user record", ignoreCase = true) == true ->
                "El correo no está registrado."
            error?.contains("The password is invalid", ignoreCase = true) == true ->
                "La contraseña es incorrecta."
            error?.contains("A network error", ignoreCase = true) == true ->
                "Error de red. Revisa tu conexión a Internet."
            error?.contains("The email address is badly formatted", ignoreCase = true) == true ->
                "El formato del correo es inválido."
            else -> "Error al iniciar sesión: $error"
        }
    }
}
