package com.market.inventarioapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

class ReporteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportes)

        val showDatePickerButton: Button = findViewById(R.id.btnCalendar)
        val selectedDateText: EditText = findViewById(R.id.editfecha)

        showDatePickerButton.setOnClickListener {
            showDatePicker(selectedDateText)
        }
    }

    private fun showDatePicker(textViewToUpdate: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Note: month is 0-indexed, so add 1 for display
                val date = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                textViewToUpdate.setText(date)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()

        val fabAtras = findViewById<FloatingActionButton>(R.id.fabAtras)
        fabAtras.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}