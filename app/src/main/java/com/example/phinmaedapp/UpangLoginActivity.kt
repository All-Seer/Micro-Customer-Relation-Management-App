package com.example.phinmaedapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

        imageView = findViewById(R.id.logo)
        setImageBasedOnTheme()

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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setImageBasedOnTheme()
    }
    private fun setImageBasedOnTheme() {
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            imageView.setImageResource(R.drawable.logodark)
        } else {
            imageView.setImageResource(R.drawable.phinmaedbanner)
        }
    }
}


