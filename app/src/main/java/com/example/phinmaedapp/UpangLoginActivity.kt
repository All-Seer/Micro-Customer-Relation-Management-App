package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.phinmaedapp.databinding.ActivityUpangloginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpangloginBinding
    private lateinit var imageView: ImageView
    private lateinit var studentEditText: EditText
    private lateinit var passwordEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpangloginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        imageView = findViewById(R.id.logo)

        supportActionBar?.hide()

        binding.btSignin.setOnClickListener {
            val username = binding.etStudUsername.text.toString()
            val password = binding.etStudPassword.text.toString()

            if (username == "student" && password == "student") {
                startActivity(Intent(this, UpangMainActivity::class.java))

            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
            }

        }
    }
}


