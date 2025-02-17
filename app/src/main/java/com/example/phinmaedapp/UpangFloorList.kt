package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.phinmaedapp.databinding.ActivityUpangFloorListBinding


class UpangFloorList : AppCompatActivity() {
    private lateinit var binding : ActivityUpangFloorListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpangFloorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1stFloor.setOnClickListener {
            val intent = Intent(this, UpangFirstFloor::class.java)
            startActivity(intent)
        }
        binding.btn2ndFloor.setOnClickListener {
            val intent = Intent(this, UpangSecondFloor::class.java)
            startActivity(intent)
        }
        binding.btn3rdFloor.setOnClickListener {
            val intent = Intent(this, UpangThirdFloor::class.java)
            startActivity(intent)
        }
        binding.btn4thFloor.setOnClickListener {
            val intent = Intent(this, UpangFourthFloor::class.java)
            startActivity(intent)
        }
        binding.btn5thFloor.setOnClickListener {
            val intent = Intent(this, UpangFifthFloor::class.java)
            startActivity(intent)
        }
    }
}