package com.example.projectimam.ui.diary

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_lv_food.view.*

class ListViewAdapter(private val food: MutableList<String>, private val calorie: MutableList<String>, val layout: Int) : BaseAdapter() {
    override fun getCount(): Int = food.size

    override fun getItem(position: Int): Any = 0

    override fun getItemId(position: Int): Long = 0

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context)
            .inflate(layout, parent, false)

        view.food_name.text = food[position]
        view.food_calorie.text = calorie[position]
        return view
    }



}