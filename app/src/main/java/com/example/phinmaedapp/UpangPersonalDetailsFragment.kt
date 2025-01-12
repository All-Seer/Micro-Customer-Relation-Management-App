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
}