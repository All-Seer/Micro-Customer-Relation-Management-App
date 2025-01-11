package com.example.phinmaedapp

import android.content.res.Configuration
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.phinmaedapp.databinding.ActivityUpangloginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityUpangloginBinding
    private lateinit var imageView: ImageView
    private lateinit var studentEditText: EditText
    private lateinit var passwordEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewBinding = ActivityUpangloginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        imageView = findViewById(R.id.logo)
        setImageBasedOnTheme()

        supportActionBar?.hide()

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


