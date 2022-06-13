package com.example.projectimam.ui.diary

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.projectimam.DatePickerHelper
import com.example.projectimam.R
import com.example.projectimam.databinding.ActivityAddFoodBinding
import java.util.*

class AddFoodActivity : AppCompatActivity() {
    private lateinit var datePicker: DatePickerHelper
    private lateinit var binding: ActivityAddFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datePicker = DatePickerHelper(this)

        val time = resources.getStringArray(R.array.time)

        binding.apply {
            btnAddDate.setOnClickListener {
                showDatePickerDialog()
            }

            val adapter =
                ArrayAdapter(this@AddFoodActivity, android.R.layout.simple_spinner_item, time)
            spinnerAddTime.adapter = adapter

            spinnerAddTime.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(this@AddFoodActivity, time[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

            btnScanAMeal.setOnClickListener {
//                val intent = Intent(this@AddFoodActivity, ScanMealActivity::class.java)
//                startActivity(intent)
            }
        }
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            @SuppressLint("SetTextI18n")
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0$dayofMonth" else "$dayofMonth"
                val mon = month + 1
                val monthStr = if (mon < 10) "0$mon" else "$mon"
                binding.btnAddDate.text = "${dayStr}-${monthStr}-${year}"
            }
        })
    }
}