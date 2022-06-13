package com.example.projectimam.ui.diary

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.projectimam.DatePickerHelper
import com.example.projectimam.R
import com.example.projectimam.databinding.ActivityEditFoodBinding
import java.util.*

class EditFoodActivity : AppCompatActivity() {

    private lateinit var datePicker: DatePickerHelper
    private lateinit var binding: ActivityEditFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, intent.getStringExtra("id"), Toast.LENGTH_SHORT).show()

        datePicker = DatePickerHelper(this)

        val time = resources.getStringArray(R.array.time)

        binding.apply {
            btnEditDate.setOnClickListener {
                showDatePickerDialog()
            }

            val adapter =
                ArrayAdapter(this@EditFoodActivity, android.R.layout.simple_spinner_item, time)
            spinnerEditTime.adapter = adapter

            spinnerEditTime.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(this@EditFoodActivity, time[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.edit_food_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.edit_food_confirm -> Toast.makeText(this,"Confirm",Toast.LENGTH_SHORT).show()
            R.id.edit_food_delete -> Toast.makeText(this,"Delete",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
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
                binding.btnEditDate.text = "${dayStr}-${monthStr}-${year}"
            }
        })
    }


}