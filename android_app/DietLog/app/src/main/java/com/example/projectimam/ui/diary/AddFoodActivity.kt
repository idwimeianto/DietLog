package com.example.projectimam.ui.diary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.example.projectimam.DatePickerHelper
import com.example.projectimam.R
import com.example.projectimam.databinding.ActivityAddFoodBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import java.time.LocalDate
import java.util.*

class AddFoodActivity : AppCompatActivity() {
    private lateinit var datePicker: DatePickerHelper
    private lateinit var binding: ActivityAddFoodBinding
    private lateinit var foodTime: String
    @RequiresApi(Build.VERSION_CODES.O)
    private var selectedDate = Date()

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
                    foodTime = time[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    foodTime = "Breakfast"
                }
            }

//            btnScanAMeal.setOnClickListener {
//                val intent = Intent(this@AddFoodActivity, ScanMealActivity::class.java)
//                startActivity(intent)
//            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_food_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = Firebase.firestore

        when (item.itemId){
            R.id.add_food_confirm -> {
                val data = hashMapOf(
                    "calories" to binding.etAddCalories.text.toString().toInt(),
                    "carbohydrates" to binding.etAddCarbo.text.toString().toInt(),
                    "date" to selectedDate,
                    "fat" to binding.etAddFat.text.toString().toInt(),
                    "name" to binding.etAddFoodName.text.toString(),
                    "protein" to binding.etAddProtein.text.toString().toInt(),
                    "size" to binding.etAddSugars.text.toString().toInt(),
                    "sugars" to binding.etAddSugars.text.toString().toInt(),
                    "times" to foodTime,
                    "uid" to Firebase.auth.currentUser?.uid
                )

                db.collection("foods").document().set(data)
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            @RequiresApi(Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0$dayofMonth" else "$dayofMonth"
                val mon = month + 1
                val monthStr = if (mon < 10) "0$mon" else "$mon"
                binding.btnAddDate.text = "${dayStr}-${monthStr}-${year}"
                selectedDate = Date(year, mon, dayofMonth)
            }
        })
    }
}