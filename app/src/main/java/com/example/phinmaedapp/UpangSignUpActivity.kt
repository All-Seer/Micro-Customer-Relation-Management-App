package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class UpangSignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upang_sign_up)


        auth = FirebaseAuth.getInstance()


        val btSignUp = findViewById<MaterialButton>(R.id.btSignin)
        btSignUp.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.etStudUsername).text.toString()
            val password = findViewById<TextInputEditText>(R.id.etStudPassword).text.toString()
            val confirmPassword = findViewById<TextInputEditText>(R.id.etStudConfirmPassword).text.toString()

            if (validateInput(email, password, confirmPassword)) {
                signUp(email, password)
            }
        }

        val tvLogin = findViewById<TextView>(R.id.tvLogin)
        tvLogin.setOnClickListener {
            startActivity(Intent(this, UpangLoginActivity::class.java))
        }
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-up success
                    val user = auth.currentUser
                    val userId = user?.uid ?: ""

                    // Save user details to Firestore
                    saveUserDetails(userId, email)

                    Snackbar.make(findViewById(R.id.rootlayout), "Sign-up successful!", Snackbar.LENGTH_SHORT).show()
                    // Navigate to the main activity
                    startActivity(Intent(this, UpangMainActivity::class.java))
                    finish() // Close the sign-up activity
                } else {
                    // Sign-up failed
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthUserCollisionException -> "Email already in use."
                        is FirebaseAuthWeakPasswordException -> "Password is too weak."
                        else -> "Sign-up failed: ${task.exception?.message}"
                    }
                    Snackbar.make(findViewById(R.id.rootlayout), errorMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserDetails(userId: String, email: String) {
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "email" to email,
            "createdAt" to FieldValue.serverTimestamp() // Optional: Add a timestamp
        )

        db.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener {
                Log.d("Firestore", "User details saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error saving user details", e)
            }
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(findViewById(R.id.rootlayout), "Invalid email address", Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Snackbar.make(findViewById(R.id.rootlayout), "Password must be at least 6 characters", Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Snackbar.make(findViewById(R.id.rootlayout), "Passwords do not match", Snackbar.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}