package com.example.projectimam.ui.diary

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.projectimam.DatePickerHelper
import com.example.projectimam.R
import com.example.projectimam.databinding.ActivityEditFoodBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class EditFoodActivity : AppCompatActivity() {

    private lateinit var datePicker: DatePickerHelper
    private lateinit var binding: ActivityEditFoodBinding
    private lateinit var foodTime: String

    @RequiresApi(Build.VERSION_CODES.O)
    private var selectedDate = Date()
    private val db = Firebase.firestore
    private lateinit var docsId: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, intent.getStringExtra("id"), Toast.LENGTH_SHORT).show()
        docsId = intent.getStringExtra("id").toString()

        datePicker = DatePickerHelper(this)

        val time = resources.getStringArray(R.array.time)

        binding.apply {

            db.collection("foods").document(docsId).get()
                .addOnSuccessListener { document ->
                    etEditCalories.setText(document.data?.get("calories").toString())
                    etEditFat.setText(document.data?.get("fat").toString())
                    etEditCarbo.setText(document.data?.get("carbohydrates").toString())
                    etEditFoodName.setText(document.data?.get("name").toString())
                    etEditProtein.setText(document.data?.get("protein").toString())
                    etEditSugars.setText(document.data?.get("sugars").toString())
                    etEditServingSize.setText(document.data?.get("size").toString())
                }

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
                    foodTime = time[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    foodTime = "Breakfast"
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
        when (item.itemId) {
            R.id.edit_food_confirm -> {
                val data = hashMapOf(
                    "calories" to binding.etEditCalories.text.toString().toInt(),
                    "carbohydrates" to binding.etEditCarbo.text.toString().toInt(),
                    "date" to selectedDate,
                    "fat" to binding.etEditFat.text.toString().toInt(),
                    "name" to binding.etEditFoodName.text.toString(),
                    "protein" to binding.etEditProtein.text.toString().toInt(),
                    "size" to binding.etEditSugars.text.toString().toInt(),
                    "sugars" to binding.etEditSugars.text.toString().toInt(),
                    "times" to foodTime,
                    "uid" to Firebase.auth.currentUser?.uid
                )

                db.collection("foods").document(docsId).set(data)
                onBackPressed()
            }
            R.id.edit_food_delete -> Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
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
                selectedDate = Date(year, mon, dayofMonth)
            }
        })
    }


}