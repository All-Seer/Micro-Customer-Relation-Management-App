package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.phinmaedapp.databinding.ActivityUpangSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpangSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpangSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpangSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = Firebase.firestore

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btSignin.setOnClickListener {
            val email = binding.etStudUsername.text.toString().trim()
            val password = binding.etStudPassword.text.toString().trim()
            val confirmPassword = binding.etStudConfirmPassword.text.toString().trim()

            if (validateInput(email, password, confirmPassword)) {
                signUp(email, password)
            }
        }

        binding.tvLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun signUp(email: String, password: String) {
        showLoading(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.email != "admin@admin.com") {
                        sendVerificationEmail(user)
                    } else {
                        // Skip verification for admin
                        saveUserDetails(user.uid, email, true)
                        navigateToMain()
                    }
                } else {
                    showLoading(false)
                    handleSignUpError(task.exception)
                }
            }
    }

    private fun sendVerificationEmail(user: FirebaseUser?) {
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://phinmaedapp.firebaseapp.com/__/auth/action")
            .setAndroidPackageName(
                packageName,
                true,
                null
            )
            .setHandleCodeInApp(true)
            .build()

        user?.sendEmailVerification(actionCodeSettings)
            ?.addOnCompleteListener { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    saveUserDetails(user.uid, user.email ?: "", false)
                    navigateToVerification(user.email ?: "")
                } else {
                    user.delete()
                    showError("Failed to send verification email")
                }
            }
    }

    private fun saveUserDetails(userId: String, email: String, isVerified: Boolean) {
        val userData = hashMapOf(
            "email" to email,
            "verified" to isVerified,
            "isAdmin" to (email == "admin@admin.com"),
            "createdAt" to FieldValue.serverTimestamp()
        )

        db.collection("users").document(userId)
            .set(userData)
            .addOnFailureListener { e ->
                Log.e("SignUp", "Error saving user data", e)
            }
    }

    private fun navigateToVerification(email: String) {
        startActivity(Intent(this, EmailVerificationActivity::class.java).apply {
            putExtra("email", email)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, UpangLoginActivity::class.java))
        finish()
    }

    private fun navigateToMain() {
        startActivity(Intent(this, UpangMainActivity::class.java))
        finish()
    }

    private fun handleSignUpError(exception: Exception?) {
        val error = when (exception) {
            is FirebaseAuthUserCollisionException -> "Email already registered"
            is FirebaseAuthWeakPasswordException -> "Password must be 6+ characters"
            else -> "Signup failed: ${exception?.message}"
        }
        showError(error)
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        return when {
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showError("Invalid email format")
                false
            }
            password.length < 6 -> {
                showError("Password must be 6+ characters")
                false
            }
            password != confirmPassword -> {
                showError("Passwords don't match")
                false
            }
            else -> true
        }
    }

    private fun showLoading(show: Boolean) {
        // Implement your loading indicator
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.btSignin.isEnabled = !show
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}