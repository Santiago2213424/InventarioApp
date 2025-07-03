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

//Esta clase hereda de AppCompatActivity, es una pantalla en la app
class RegisterActivity : AppCompatActivity() {

    //Declara dos objetos
    //Nos sirve para autenticación con Firebase (crear usuarios).
    private lateinit var auth: FirebaseAuth

    //Guardar datos del Usuario en la base de datos
    private lateinit var db: FirebaseFirestore

    //Inicia la actividad, y usa el archivo xml como interfaz grafica
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Inializa las instancias
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        //Acccede a los edit y botones para manejar su contenido
        val nombre = findViewById<EditText>(R.id.editNombre)
        val apellido = findViewById<EditText>(R.id.editApellido)
        val correo = findViewById<EditText>(R.id.editCorreo)
        val password = findViewById<TextInputEditText>(R.id.editPassword)
        val repeatPassword = findViewById<TextInputEditText>(R.id.editRepeatPassword)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)


        btnRegistrarse.setOnClickListener {
            //Obtiene los que escribio el usuario en los campos, y elimana los espacios
            val name = nombre.text.toString().trim()
            val lastName = apellido.text.toString().trim()
            val email = correo.text.toString().trim()
            val pass = password.text.toString().trim()
            val repeatPass = repeatPassword.text.toString().trim()


            //Validaciones
            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != repeatPass) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Si pasa la validacion, crea el usuario  con firebase, si la creacion fue exitosa
            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        //Obtiene el uid usuario recien creado,con la info y lo guarda en firebase
                        val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                        val userMap = hashMapOf(
                            "nombre" to name,
                            "apellido" to lastName,
                            "correo" to email
                        )

                        //Guardas los datos en la colección usuarios en documento con el uid del usuario
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
