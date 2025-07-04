package com.market.inventarioapp.carpreporte

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.market.inventarioapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AgregarReporteActivity : AppCompatActivity() {

    //Variables
    private lateinit var etNombre: EditText
    private lateinit var etMonto: EditText
    //Grupo de botones de opción para elegir si es ingreso o gasto
    private lateinit var rgTipo: RadioGroup
    private lateinit var btnGuardar: Button
    //Se crea una instancia de Firebase
    private val db = FirebaseFirestore.getInstance()
    //Se obtiene el UID del usuario actualmente autenticado en Firebase
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_reporte)

        //Conectan los elementos visuales de la interfaz XML
        etNombre = findViewById(R.id.etNombre)
        etMonto = findViewById(R.id.etMonto)
        rgTipo = findViewById(R.id.rgTipo)
        btnGuardar = findViewById(R.id.btnGuardar)

        //Captura datos del formulario ingresados por usuario
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val monto = etMonto.text.toString().toDoubleOrNull()
            //Determina si el botón seleccionado es el de ingreso, y devuelve true o false
            val esIngreso = rgTipo.checkedRadioButtonId == R.id.rbIngreso
            //Se obtiene la fecha actual con formato
            val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            //Validacion
            if (nombre.isEmpty() || monto == null) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Se crea una instancia de la clase Reporte con los datos ingresados
            val reporte = Reporte(
                nombre = nombre,
                monto = monto,
                fecha = fecha,
                esIngreso = esIngreso,
                uid = uid
            )

            //Se accede a la colección "reportes" en Firestore y se agrega el objeto reporte
            db.collection("reportes")
                .add(reporte)
                .addOnSuccessListener {
                    Toast.makeText(this, "Reporte guardado", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
        }
    }
}