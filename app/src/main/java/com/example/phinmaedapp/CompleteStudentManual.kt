package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.phinmaedapp.databinding.ActivityCompleteStudentManualBinding

class CompleteStudentManual : AppCompatActivity() {
    private lateinit var binding: ActivityCompleteStudentManualBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteStudentManualBinding.inflate(layoutInflater)

        val pdfFragment = pdf()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, pdfFragment)
            .addToBackStack(null)
            .commit()
    }
}