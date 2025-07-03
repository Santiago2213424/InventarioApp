package com.market.inventarioapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

//Esta clase hereda de AppCompatActivity, es una pantalla en la app
class LoginActivity : AppCompatActivity() {

    //Clase principal de Firebase que permite manejar login,lagout,registro,etc
    private lateinit var auth: FirebaseAuth

    //Inicia la actividad, y usa el archivo xml como interfaz grafica
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Inicializa Firebase Authentication, lo usamos para autenticar usuarios
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
            //Toma los textos Ingresados por el usuario y borra espacios en blanco
            val correo = txtCorreo.text.toString().trim()
            val contraseña = txtContraseña.text.toString().trim()


            if (correo.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Este método autentica al usuario con los datos en Firebase
            auth.signInWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener { task ->
                    //Si las credenciales estan registradas y son las correctas
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Iniciaste sesión", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, InicioActivity::class.java))
                        finish()
                    } else {
                        //Captura los mensajes de error firebase y pasa a la funcion traducirErrorFirebase
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
            else -> "Error al iniciar sesión"
        }
    }
}
