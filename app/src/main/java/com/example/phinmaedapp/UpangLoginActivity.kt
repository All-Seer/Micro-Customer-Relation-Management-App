package com.example.phinmaedapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.phinmaedapp.databinding.ActivityUpangloginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpangloginBinding
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpangloginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        imageView = findViewById(R.id.logo)

        supportActionBar?.hide()

        binding.btSignin.setOnClickListener {
            var isValid = true
            val username = binding.etStudUsername.text.toString()
            val password = binding.etStudPassword.text.toString()


            if (username.isEmpty()) {
                binding.userLayout.error = "Username is required"
                binding.userLayout.boxStrokeColor = Color.RED
                isValid = false
            } else {
                binding.userLayout.error = null
                binding.userLayout.boxStrokeColor = getColor(R.color.phinmaPrimary)
            }
            if (password.isEmpty()) {
                binding.passLayout.error = "Username is required"
                binding.passLayout.boxStrokeColor = Color.RED
                isValid = false
            } else {
                binding.passLayout.error = null
                binding.passLayout.boxStrokeColor = getColor(R.color.phinmaPrimary)
            }
            if (username == "student" && password == "student") {
                isValid = true
                startActivity(Intent(this, UpangMainActivity::class.java))
            } else {
                isValid = false
                binding.userLayout.error = "Invalid Username"
                binding.userLayout.boxStrokeColor = Color.RED
                binding.passLayout.error = "Invalid Password"
                binding.passLayout.boxStrokeColor = Color.RED
            }
        }
    }
}


