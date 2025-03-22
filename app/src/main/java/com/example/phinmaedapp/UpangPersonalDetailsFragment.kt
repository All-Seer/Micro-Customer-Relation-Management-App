package com.example.phinmaedapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.phinmaedapp.databinding.FragmentUpangPersonalDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class UpangPersonalDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUpangPersonalDetailsBinding
    private lateinit var spinnerGender: Spinner
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpangPersonalDetailsBinding.inflate(inflater, container, false)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Submit Button Click Listener
        binding.btpdSubmit.setOnClickListener {
            if (validateInput()) {
                savePersonalDetails()
            }
        }

        // Upload Button Click Listener
        binding.btnUploadProfilePicture.setOnClickListener {
            openImagePicker()
        }

        // Initialize Gender Spinner
        spinnerGender = binding.spinnerGender
        val genderOptions = listOf("Select Gender", "Male", "Female", "Other")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
        spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedGender = parent.getItemAtPosition(position) as String
                if (selectedGender == "Select Gender") {
                    return
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as UpangMainActivity).updateActionBarTitle("Personal Details")
    }

    private fun savePersonalDetails() {
        val userId = auth.currentUser?.uid ?: run {
            Snackbar.make(requireView(), "User not authenticated", Snackbar.LENGTH_SHORT).show()
            return
        }

        // Convert the selected image to Base64
        val profilePictureBase64 = if (imageUri != null) {
            imageToBase64(imageUri!!)
        } else {
            "" // No image selected
        }

        // Get input values
        val studentId = binding.etStudentID.text.toString()
        val lastName = binding.etLastName.text.toString()
        val firstName = binding.etFirstName.text.toString()
        val middleName = binding.etMiddleName.text.toString()
        val gender = spinnerGender.selectedItem.toString()
        val email = binding.etEmail.text.toString()
        val contact = binding.etContact.text.toString()
        val fbLink = binding.etFBlink.text.toString()

        // Create a map of user details
        val userDetails = hashMapOf(
            "studentId" to studentId,
            "lastName" to lastName,
            "firstName" to firstName,
            "middleName" to middleName,
            "gender" to gender,
            "email" to email,
            "contact" to contact,
            "fbLink" to fbLink,
            "profilePictureBase64" to profilePictureBase64
        )

        db.collection("users").document(userId)
            .set(userDetails, SetOptions.merge())
            .addOnSuccessListener {
                Snackbar.make(requireView(), "Details saved successfully!", Snackbar.LENGTH_SHORT).show()
                navigateToHomeFragment()
            }
            .addOnFailureListener { e ->
                Snackbar.make(requireView(), "Failed to save details: ${e.message}", Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun navigateToHomeFragment() {
        val homeFragment = UpangHomeFragment()

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.upangFragment, homeFragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()

        val activity = requireActivity() as UpangMainActivity
        activity.navView.setCheckedItem(R.id.itemUpanghome)
    }

    private fun validateInput(): Boolean {
        val studentId = binding.etStudentID.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val firstName = binding.etFirstName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val contact = binding.etContact.text.toString().trim()

        if (studentId.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || contact.isEmpty() || spinnerGender === {"Select Gender"} || imageUri == null) {
            Snackbar.make(requireView(), "Please fill in all required fields", Snackbar.LENGTH_SHORT).show()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(requireView(), "Invalid email address", Snackbar.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == android.app.Activity.RESULT_OK && data != null) {
            imageUri = data.data
            binding.ivProfilePicturePreview.setImageURI(imageUri)
        }
    }

    private fun imageToBase64(imageUri: Uri): String {
        val inputStream = requireContext().contentResolver.openInputStream(imageUri)
        val bytes = inputStream?.readBytes() ?: byteArrayOf()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }


}