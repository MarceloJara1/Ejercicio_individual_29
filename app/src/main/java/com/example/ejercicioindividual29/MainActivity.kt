package com.example.ejercicioindividual29

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.ejercicioindividual29.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val conversionRates = mapOf(
        "CLP" to mapOf("CLP" to 1.0, "USD" to 0.0012, "EUR" to 0.0011),
        "USD" to mapOf("CLP" to 805.15, "USD" to 1.0, "EUR" to 0.91),
        "EUR" to mapOf("CLP" to 881.69, "USD" to 1.10, "EUR" to 1.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        valoresSpinners()
        convertButton()
        resetButton()
        swapButton()
    }

    private fun valoresSpinners(){
        val currencies = arrayOf("CLP", "USD", "EUR")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.currencyDropdown.adapter = adapter
        binding.currencyDropdown1.adapter = adapter

    }

    private fun convertButton(){
        binding.btnConvert.setOnClickListener {
            val cantidad = binding.editTextImporte.text.toString().toDoubleOrNull()
            if (cantidad != null){
                val selectedCurrencyFrom = binding.currencyDropdown.selectedItem.toString()
                val selectedCurrencyTo = binding.currencyDropdown1.selectedItem.toString()

                val conversionRate = conversionRates[selectedCurrencyFrom]?.get(selectedCurrencyTo)
                if (conversionRate!= null){
                    val cantidadConvert = cantidad * conversionRate

                    val result = String.format("%.0f %s =", cantidad, selectedCurrencyFrom)
                    val result2 = String.format("%.0f %s",cantidadConvert, selectedCurrencyTo)
                    binding.txtView1.text = result
                    binding.txtView2.text = result2
                }
            }else{
                Snackbar.make(binding.root, "Ingrese un valor valido al importe", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun resetButton(){
        binding.btnReset.setOnClickListener {
            binding.editTextImporte.text?.clear()
            binding.currencyDropdown.setSelection(0)
            binding.currencyDropdown1.setSelection(0)
            binding.txtView1.text = ""
            binding.txtView2.text = ""
        }
    }

    private fun swapButton(){
        binding.iconButton.setOnClickListener {
            val selectedCurrencyFrom = binding.currencyDropdown.selectedItemPosition
            val selectedCurrencyTo = binding.currencyDropdown1.selectedItemPosition

            binding.currencyDropdown.setSelection(selectedCurrencyTo)
            binding.currencyDropdown1.setSelection(selectedCurrencyFrom)
        }
    }
}