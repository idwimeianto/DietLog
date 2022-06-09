package com.example.projectimam.ui.diary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projectimam.R
import com.example.projectimam.databinding.FragmentDairyBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_dairy.*


class DiaryFragment : Fragment() {

    private var _binding: FragmentDairyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDairyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)

        val breakfastFoods = mutableListOf<String>()
        val lunchFoods = mutableListOf<String>()
        val dinnerFoods = mutableListOf<String>()
        val snackFoods = mutableListOf<String>()

        val breakfastCalories = mutableListOf<String>()
        val lunchCalories = mutableListOf<String>()
        val dinnerCalories = mutableListOf<String>()
        val snackCalories = mutableListOf<String>()

        val breakfastId = mutableListOf<String>()
        val lunchId = mutableListOf<String>()
        val dinnerId = mutableListOf<String>()
        val snackId = mutableListOf<String>()

        var breakfastTotalCalorie = 0
        var lunchTotalCalorie = 0
        var dinnerTotalCalorie = 0
        var snackTotalCalorie = 0

        val db = Firebase.firestore
        val currentUserUid = Firebase.auth.currentUser?.uid
        val foodsRef = db.collection("foods").whereEqualTo("uid", currentUserUid)

        foodsRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                when (document.data["times"].toString()) {
                    "Breakfast" -> {
                        breakfastFoods.add(document.data["name"].toString())
                        breakfastCalories.add(document.data["calories"].toString())
                        breakfastTotalCalorie += document.data["calories"].toString().toInt()
                        breakfastId.add(document.id)
                    }
                    "Lunch" -> {
                        lunchFoods.add(document.data["name"].toString())
                        lunchCalories.add(document.data["calories"].toString())
                        lunchTotalCalorie += document.data["calories"].toString().toInt()
                        lunchId.add(document.id)
                    }
                    "Dinner" -> {
                        dinnerFoods.add(document.data["name"].toString())
                        dinnerCalories.add(document.data["calories"].toString())
                        dinnerTotalCalorie += document.data["calories"].toString().toInt()
                        dinnerId.add(document.id)
                    }
                    "Snack" -> {
                        snackFoods.add(document.data["name"].toString())
                        snackCalories.add(document.data["calories"].toString())
                        snackTotalCalorie += document.data["calories"].toString().toInt()
                        snackId.add(document.id)
                    }
                }
            }

            binding.apply {
                breakfast_total_calorie.text = breakfastTotalCalorie.toString()
                lunch_total_calorie.text = lunchTotalCalorie.toString()
                dinner_total_calorie.text = dinnerTotalCalorie.toString()
                snack_total_calorie.text = snackTotalCalorie.toString()

                lvBreakfast.adapter =
                    ListViewAdapter(breakfastFoods, breakfastCalories, R.layout.item_lv_food)
                lvLunch.adapter =
                    ListViewAdapter(lunchFoods, lunchCalories, R.layout.item_lv_food)
                lvDinner.adapter =
                    ListViewAdapter(dinnerFoods, dinnerCalories, R.layout.item_lv_food)
                lvSnack.adapter =
                    ListViewAdapter(snackFoods, snackCalories, R.layout.item_lv_food)

                lvBreakfast.setOnItemClickListener { _, _, position, _ ->
                    val intent = Intent(activity, EditFoodActivity::class.java)
                    intent.putExtra("id", breakfastId[position])
                    startActivity(intent)
                }
                lvLunch.setOnItemClickListener { _, _, position, _ ->
                    Toast.makeText(context, lunchId[position], Toast.LENGTH_SHORT).show()
                }
                lvDinner.setOnItemClickListener { _, _, position, _ ->
                    Toast.makeText(context, dinnerId[position], Toast.LENGTH_SHORT).show()
                }
                lvSnack.setOnItemClickListener { _, _, position, _ ->
                    Toast.makeText(context, snackId[position], Toast.LENGTH_SHORT).show()
                }
            }

        }.addOnFailureListener {
            Toast.makeText(activity, "Error Fetch Food Data", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.daily_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_food -> {
                val intent = Intent(activity, AddFoodActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}