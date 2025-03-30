package com.example.phinmaedapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EmailVerificationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        email = intent.getStringExtra("email") ?: ""

        // Handle both direct opens and link clicks
        handleIncomingLink(intent.data)
        setupUI()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIncomingLink(intent.data)
    }

    private fun setupUI() {
        findViewById<TextView>(R.id.tvEmail).text = "Verification email sent to: $email"

        findViewById<Button>(R.id.btVerify).setOnClickListener {
            checkEmailVerification()
        }

        findViewById<Button>(R.id.btResend).setOnClickListener {
            resendVerificationEmail()
        }

        findViewById<Button>(R.id.btSignOut).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, UpangLoginActivity::class.java))
            finish()
        }
    }


    private fun handleIncomingLink(data: Uri?) {
        data?.let { link ->
            if (link.toString().contains("mode=verifyEmail")) {
                val oobCode = link.getQueryParameter("oobCode")
                if (!oobCode.isNullOrEmpty()) {
                    auth.applyActionCode(oobCode)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                checkEmailVerification()
                            } else {
                                showError("Verification failed")
                            }
                        }
                }
            }
        }
    }

    private fun verifyEmailWithCode(oobCode: String) {
        showLoading(true)
        auth.applyActionCode(oobCode)
            .addOnCompleteListener { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    checkEmailVerification()
                } else {
                    showError("Verification failed: ${task.exception?.message}")
                }
            }
    }

    private fun checkEmailVerification() {
        showLoading(true)
        val user = auth.currentUser
        user?.reload()?.addOnCompleteListener { reloadTask ->
            showLoading(false)
            if (reloadTask.isSuccessful) {
                if (user.isEmailVerified) {
                    updateVerificationStatus(user.uid)
                } else {
                    showMessage("Please verify your email first")
                }
            } else {
                showError("Error checking verification status")
            }
        } ?: showError("User not logged in")
    }

    private fun updateVerificationStatus(userId: String) {
        showLoading(true)
        db.collection("users").document(userId)
            .update("verified", true)
            .addOnCompleteListener { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    navigateToMain()
                } else {
                    showError("Failed to update verification status")
                }
            }
    }

    private fun resendVerificationEmail() {
        showLoading(true)
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    showMessage("Verification email resent")
                } else {
                    showError("Failed to resend: ${task.exception?.message}")
                }
            } ?: showError("User not available")
    }

    private fun navigateToMain() {
        startActivity(Intent(this, UpangMainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, UpangLoginActivity::class.java))
        finish()
    }

    private fun showLoading(show: Boolean) {
        // Implement your loading indicator
    }

    private fun showMessage(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    private fun showError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }
}