package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.phinmaedapp.databinding.ActivityUpangloginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpangloginBinding
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpangloginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        imageView = findViewById(R.id.logo)

        binding.btSignin.setOnClickListener {
                startActivity(Intent(this, UpangMainActivity::class.java))
        }
    }
}


