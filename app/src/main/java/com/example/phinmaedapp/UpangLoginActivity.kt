package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class UpangLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upang_login)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Check if user is already logged in and verified
        auth.currentUser?.let { user ->
            checkUserVerification(user)
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
                login(email, password, rootLayout) // Pass rootLayout here
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

    private fun login(email: String, password: String, rootLayout: View) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.email == "admin@admin.com" || user?.isEmailVerified == true) {
                        // Update verification status in Firestore
                        updateUserVerificationStatus(user.uid, true)
                        navigateToMain()
                    } else {
                        // Show verification required
                        navigateToVerification(email)
                        auth.signOut()
                    }
                } else {
                    handleLoginError(task.exception, rootLayout)
                }
            }
    }
    private fun navigateToMain() {
        startActivity(Intent(this, UpangMainActivity::class.java))
        finish()
    }

    private fun navigateToVerification(email: String) {
        startActivity(Intent(this, EmailVerificationActivity::class.java).apply {
            putExtra("email", email)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun checkUserVerification(user: FirebaseUser) {
        user.reload().addOnCompleteListener { reloadTask ->
            if (reloadTask.isSuccessful) {
                if (user.isEmailVerified) {
                    // User is verified, proceed to main activity
                    updateUserVerificationStatus(user.uid, true)
                    startActivity(Intent(this, UpangMainActivity::class.java))
                    finish()
                } else {
                    // Email not verified, show verification screen
                    val intent = Intent(this, EmailVerificationActivity::class.java).apply {
                        putExtra("email", user.email)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    finish()
                }
            } else {
                Snackbar.make(
                    findViewById(R.id.rootlayout),
                    "Error checking verification status: ${reloadTask.exception?.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateUserVerificationStatus(userId: String, isVerified: Boolean) {
        db.collection("users").document(userId)
            .update("verified", isVerified)
            .addOnSuccessListener {
                Log.d("Firestore", "User verification status updated")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error updating verification status", e)
            }
    }

    private fun handleLoginError(exception: Exception?, rootLayout: View) {
        val errorMessage = when (exception) {
            is FirebaseAuthInvalidUserException -> "User not found. Please sign up."
            is FirebaseAuthInvalidCredentialsException -> "Invalid email or password."
            is FirebaseNetworkException -> "Network error. Please check your connection."
            else -> "Login failed: ${exception?.message}"
        }
        Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_LONG).show()
        Log.e("UpangLoginActivity", "Login failed", exception)
    }

    private fun sendPasswordResetEmail(email: String, rootLayout: View) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(rootLayout, "Password reset email sent to $email", Snackbar.LENGTH_LONG).show()
                } else {
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthInvalidUserException -> "User not found."
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email format."
                        is FirebaseNetworkException -> "Network error. Please check your connection."
                        else -> "Failed to send reset email: ${task.exception?.message}"
                    }
                    Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_LONG).show()
                    Log.e("UpangLoginActivity", "Failed to send reset email", task.exception)
                }
            }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(findViewById(R.id.rootlayout), "Please fill in all fields", Snackbar.LENGTH_SHORT).show()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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