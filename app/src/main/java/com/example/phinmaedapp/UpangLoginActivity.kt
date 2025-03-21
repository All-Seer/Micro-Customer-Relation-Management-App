package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class UpangLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upang_login)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {

            startActivity(Intent(this, UpangMainActivity::class.java))
            finish()
            return
        }

        val btSignIn = findViewById<MaterialButton>(R.id.btSignin)
        val etStudUsername = findViewById<TextInputEditText>(R.id.etStudUsername)
        val etStudPassword = findViewById<TextInputEditText>(R.id.etStudPassword)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val rootLayout = findViewById<LinearLayoutCompat>(R.id.rootlayout)

        btSignIn.setOnClickListener {
            val email = etStudUsername.text.toString().trim()
            val password = etStudPassword.text.toString().trim()

            if (validateInput(email, password)) {
                login(email, password, rootLayout)
            } else {
                Snackbar.make(rootLayout, "Please fill in all fields", Snackbar.LENGTH_SHORT).show()
            }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, UpangSignUpActivity::class.java))
        }

        tvForgotPassword.setOnClickListener {
            val email = etStudUsername.text.toString().trim()
            if (email.isNotEmpty()) {
                sendPasswordResetEmail(email, rootLayout)
            } else {
                Snackbar.make(rootLayout, "Please enter your email", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String, rootLayout: android.view.View) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Snackbar.make(rootLayout, "Login successful!", Snackbar.LENGTH_SHORT).show()
                    startActivity(Intent(this, UpangMainActivity::class.java))
                    finish()
                } else {
                    val exception = task.exception
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            Snackbar.make(rootLayout, "User not found.", Snackbar.LENGTH_SHORT).show()
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Snackbar.make(rootLayout, "Invalid email or password.", Snackbar.LENGTH_SHORT).show()
                        }
                        is FirebaseNetworkException -> {
                            Snackbar.make(rootLayout, "Network error. Please check your connection.", Snackbar.LENGTH_SHORT).show()
                        }
                        else -> {
                            Snackbar.make(rootLayout, "Login failed: ${exception?.message}", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    Log.e("UpangLoginActivity", "Login failed", exception)
                }
            }
    }

    private fun sendPasswordResetEmail(email: String, rootLayout: android.view.View) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(rootLayout, "Password reset email sent", Snackbar.LENGTH_SHORT).show()
                } else {
                    val exception = task.exception
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            Snackbar.make(rootLayout, "User not found.", Snackbar.LENGTH_SHORT).show()
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Snackbar.make(rootLayout, "Invalid email format.", Snackbar.LENGTH_SHORT).show()
                        }
                        is FirebaseNetworkException -> {
                            Snackbar.make(rootLayout, "Network error. Please check your connection.", Snackbar.LENGTH_SHORT).show()
                        }
                        else -> {
                            Snackbar.make(rootLayout, "Failed to send reset email: ${exception?.message}", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    Log.e("UpangLoginActivity", "Failed to send reset email", exception)
                }
            }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(findViewById(R.id.rootlayout), "Invalid email address", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Snackbar.make(findViewById(R.id.rootlayout), "Password must be at least 6 characters", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}