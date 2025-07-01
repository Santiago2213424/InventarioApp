package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val nombre = findViewById<EditText>(R.id.editNombre)
        val apellido = findViewById<EditText>(R.id.editApellido)
        val correo = findViewById<EditText>(R.id.editCorreo)
        val password = findViewById<TextInputEditText>(R.id.editPassword)
        val repeatPassword = findViewById<TextInputEditText>(R.id.editRepeatPassword)

        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        btnRegistrarse.setOnClickListener {
            val name = nombre.text.toString().trim()
            val lastName = apellido.text.toString().trim()
            val email = correo.text.toString().trim()
            val pass = password.text.toString().trim()
            val repeatPass = repeatPassword.text.toString().trim()

            // Validaciones
            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != repeatPass) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Registro con Firebase Auth
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                        val userMap = hashMapOf(
                            "nombre" to name,
                            "apellido" to lastName,
                            "correo" to email
                        )

                        db.collection("usuarios").document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registro exitoso. Inicia sesión.", Toast.LENGTH_SHORT).show()
                                auth.signOut() // Cierra la sesión del registro
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error al guardar datos", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        btnCancelar.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
