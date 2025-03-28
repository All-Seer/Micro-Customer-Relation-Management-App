package com.example.phinmaedapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UpangHomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upang_home, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        fetchUserDetails(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as UpangMainActivity).updateActionBarTitle("Home")
    }

    private fun fetchUserDetails(view: View) {
        val userId = auth.currentUser?.uid ?: return // Get the current user's ID

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Bind data to views
                    bindDataToViews(view, document)
                } else {
                    Snackbar.make(view, "User details not found", Snackbar.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Snackbar.make(view, "Failed to fetch user details: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun bindDataToViews(view: View, document: DocumentSnapshot) {
        // Get user details from Firestore document
        val studentId = document.getString("studentId") ?: ""
        val lastName = document.getString("lastName") ?: ""
        val firstName = document.getString("firstName") ?: ""
        val middleName = document.getString("middleName") ?: ""
        val gender = document.getString("gender") ?: ""
        val email = document.getString("email") ?: ""
        val contact = document.getString("contact") ?: ""
        val fbLink = document.getString("fbLink") ?: ""
        val profilePictureBase64 = document.getString("profilePictureBase64") ?: ""

        // Combine first name, middle name, and last name
        val fullName = "$firstName $middleName $lastName"

        // Bind data to views
        view.findViewById<TextView>(R.id.tvstudName).text = fullName
        view.findViewById<TextView>(R.id.tvstudNum).text = studentId
        view.findViewById<TextView>(R.id.tvStudGender).text = gender
        view.findViewById<TextView>(R.id.tvStudEmail).text = email
        view.findViewById<TextView>(R.id.tvStudContact).text = contact
        view.findViewById<TextView>(R.id.tvStudFB).text = fbLink

        // Decode and display the profile picture
        if (profilePictureBase64.isNotEmpty()) {
            val bitmap = decodeBase64ToBitmap(profilePictureBase64)
            view.findViewById<ImageView>(R.id.ivProfilePicture).setImageBitmap(bitmap)
        }
    }

    private fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}