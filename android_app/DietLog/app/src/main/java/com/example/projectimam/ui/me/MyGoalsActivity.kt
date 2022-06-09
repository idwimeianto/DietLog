package com.example.projectimam.ui.me

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectimam.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyGoalsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_goals)
    }
}