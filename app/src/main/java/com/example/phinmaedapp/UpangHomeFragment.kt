package com.example.phinmaedapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.phinmaedapp.databinding.FragmentUpangHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UpangHomeFragment : Fragment() {
    private var _binding: FragmentUpangHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val viewModel: UpangHomeViewModel by viewModels()
    private lateinit var adapter: NotificationsAdapter
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpangHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        fetchUserDetails(view)

        // Load notifications for current user
        auth.currentUser?.uid?.let { userId ->
            viewModel.loadUserNotifications(userId)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? UpangMainActivity)?.updateActionBarTitle("Home")
    }



    private fun fetchUserDetails(view: View) {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    bindDataToViews(document)
                } else {
                    Snackbar.make(view, "User details not found", Snackbar.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Snackbar.make(view, "Failed to fetch user details: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
    }


    private fun bindDataToViews(document: DocumentSnapshot) {
        // Get user details
        val studentId = document.getString("studentId") ?: ""
        val lastName = document.getString("lastName") ?: ""
        val firstName = document.getString("firstName") ?: ""
        val middleName = document.getString("middleName") ?: ""
        val gender = document.getString("gender") ?: ""
        val email = document.getString("email") ?: ""
        val contact = document.getString("contact") ?: ""
        val fbLink = document.getString("fbLink") ?: ""
        val profilePictureBase64 = document.getString("profilePictureBase64") ?: ""

        // Update views
        binding.tvstudName.text = "$firstName $middleName $lastName"
        binding.tvstudNum.text = studentId
        binding.tvStudGender.text = gender
        binding.tvStudEmail.text = email
        binding.tvStudContact.text = contact
        binding.tvStudFB.text = fbLink

        if (profilePictureBase64.isNotEmpty()) {
            val bitmap = decodeBase64ToBitmap(profilePictureBase64)
            binding.ivProfilePicture.setImageBitmap(bitmap)
        }
    }

    private fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
