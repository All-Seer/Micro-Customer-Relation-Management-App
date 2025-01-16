package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.FragmentUpangPersonalDetailsBinding

class UpangPersonalDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUpangPersonalDetailsBinding
    private lateinit var spinnerGender: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpangPersonalDetailsBinding.inflate(inflater, container, false)


        binding.btpdSubmit.setOnClickListener{
            binding.tvPDStudentID.text = binding.etStudentID.text.toString()
           val passfragment = UpangHomeFragment()
            val bundle = Bundle().apply {
                putString("LASTNAME", binding.etLastName.text.toString())
                putString("FIRSTNAME", binding.etFirstName.text.toString())
                putString("MIDDLENAME", binding.etMiddleName.text.toString())
                putString("EXTENSIONNAME", binding.etExtensionName.text.toString())
                putString("STUDENTID", binding.etStudentID.text.toString())
                putString("STUDENTEMAIL", binding.etEmail.text.toString())
                putString("STUDENTCONTACT", binding.etContact.text.toString())
                putString("FACEBOOKLINK", binding.etFBlink.text.toString())
            }
            passfragment.arguments = bundle

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.upangFragment, passfragment)
            transaction.commit()
        }

        spinnerGender = binding.root.findViewById(R.id.spinnerGender)
        val genderOptions = listOf("Select Gender", "Male", "Female", "Other")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
        spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
}