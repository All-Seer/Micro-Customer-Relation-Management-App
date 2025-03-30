package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class UpangSignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upang_sign_up)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        findViewById<MaterialButton>(R.id.btSignin).setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.etStudUsername).text.toString().trim()
            val password = findViewById<TextInputEditText>(R.id.etStudPassword).text.toString().trim()
            val confirmPassword = findViewById<TextInputEditText>(R.id.etStudConfirmPassword).text.toString().trim()

            if (validateInput(email, password, confirmPassword)) {
                signUp(email, password)
            }
        }

        findViewById<TextView>(R.id.tvLogin).setOnClickListener {
            navigateToLogin()
        }
    }

    private fun signUp(email: String, password: String) {
        showLoading(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendVerificationEmail(email)
                } else {
                    showLoading(false)
                    handleSignUpError(task.exception)
                }
            }
    }

    private fun sendVerificationEmail(email: String) {
        val user = auth.currentUser
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://phinmaedapp.firebaseapp.com/__/auth/action")
            .setAndroidPackageName(
                packageName,
                true, // Install if not available
                null  // Minimum version
            )
            .setHandleCodeInApp(true)
            .build()

        user?.sendEmailVerification(actionCodeSettings)
            ?.addOnCompleteListener { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    saveUserAndNavigate(user.uid, email)
                } else {
                    user.delete().addOnCompleteListener {
                        showError("Failed to send verification: ${task.exception?.message}")
                    }
                }
            } ?: showError("User creation failed")
    }

    private fun saveUserAndNavigate(userId: String, email: String) {
        db.collection("users").document(userId)
            .set(createUserData(email))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToVerification(email)
                } else {
                    showError("Failed to save user data")
                }
            }
    }

    private fun createUserData(email: String) = hashMapOf(
        "email" to email,
        "verified" to false,
        "createdAt" to FieldValue.serverTimestamp()
    )

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
    }

    private fun showError(message: String) {
        Snackbar.make(findViewById(R.id.rootlayout), message, Snackbar.LENGTH_LONG).show()
    }
}