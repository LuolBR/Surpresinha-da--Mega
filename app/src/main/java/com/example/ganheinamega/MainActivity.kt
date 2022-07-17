package com.example.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtNumber: EditText = findViewById(R.id.edt_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val savedPreference = prefs.getString("result", null)
        savedPreference?.let { txtResult.text = "Última aposta: $it" }

        btnGenerate.setOnClickListener {
            generateNumbers(edtNumber.text.toString(), txtResult)
        }
    }

    private fun generateNumbers(text: String, result: TextView) {
        //Validate if it is empty
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um valor entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        //Validate if the value is correct
        val quantity = text.toInt()
        if (quantity < 6 || quantity > 15) {
            Toast.makeText(this, "Informe um valor entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == quantity) break
        }
        result.text = numbers.sorted().joinToString(" - ")

        prefs.edit().apply {
            putString("result", result.text.toString())
            apply()
        }
    }
}