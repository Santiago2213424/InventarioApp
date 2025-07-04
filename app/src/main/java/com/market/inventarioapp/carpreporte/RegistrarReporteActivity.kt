package com.market.inventarioapp.carpreporte

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.market.inventarioapp.InicioActivity
import com.market.inventarioapp.R
import com.market.inventarioapp.carpreporte.RepositorioReporte
import java.text.SimpleDateFormat
import java.util.*

class RegistrarReporteActivity : AppCompatActivity() {

    //Variables de interfaz
    private lateinit var etNombre: EditText
    private lateinit var etMonto: EditText
    private lateinit var rgTipo: RadioGroup
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_reporte) // Tu layout actual

        // Asigna los elementos del XML a las variables
        etNombre = findViewById(R.id.etNombre)
        etMonto = findViewById(R.id.etMonto)
        rgTipo = findViewById(R.id.rgTipo)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Botón Guardar
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val montoTexto = etMonto.text.toString().trim()

            //Validacion
            if (nombre.isEmpty() || montoTexto.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Intenta convertir el texto del monto a número double
            //Si falla o si es menor o igual a cero, muestra error
            val monto = montoTexto.toDoubleOrNull()
            if (monto == null || monto <= 0) {
                Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Verifica si el usuario marcó el botón de Ingreso sino asume que es gasto
            val esIngreso = rgTipo.checkedRadioButtonId == R.id.rbIngreso
            registrarReporte(nombre, monto, esIngreso)
        }

        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
        }

        findViewById<Button>(R.id.btnVerHoy).setOnClickListener {
            startActivity(Intent(this, ReporteDelDiaActivity::class.java))
        }

        findViewById<Button>(R.id.btnHistorial).setOnClickListener {
            startActivity(Intent(this, ReporteFiltradoActivity::class.java))
        }
    }

    //Se encarga de crear y guardar el reporte en Firebase
    private fun registrarReporte(nombre: String, monto: Double, esIngreso: Boolean) {
        //Se obtiene el UID del usuario autenticado
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        //Genera una cadena con la fecha de hoy
        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        //Crea el objeto reporte
        val nuevoReporte = Reporte(
            nombre = nombre,
            fecha = fechaActual,
            monto = monto,
            esIngreso = esIngreso,
            uid = uid
        )

        RepositorioReporte.guardarReporte(nuevoReporte) { exito ->
            if (exito) {
                Toast.makeText(this, "Reporte guardado", Toast.LENGTH_SHORT).show()

                // Limpiar campos
                etNombre.text.clear()
                etMonto.text.clear()
                rgTipo.check(R.id.rbIngreso)

                // Redirigir a lista de reportes del día
                val intent = Intent(this, ReporteDelDiaActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
