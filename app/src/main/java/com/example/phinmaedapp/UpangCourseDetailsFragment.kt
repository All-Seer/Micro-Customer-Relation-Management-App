package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.FragmentUpangCourseDetailsBinding

class UpangCourseDetailsFragment : Fragment() {

    private var _binding: FragmentUpangCourseDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var spinnerType: Spinner
    private lateinit var spinnerType2: Spinner
    private lateinit var spinnerType3: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpangCourseDetailsBinding.inflate(inflater, container, false)

        spinnerType = binding.root.findViewById(com.example.phinmaedapp.R.id.type_elem)
        spinnerType2 = binding.root.findViewById(com.example.phinmaedapp.R.id.type_junior)
        spinnerType3 = binding.root.findViewById(com.example.phinmaedapp.R.id.type_senior)
        val typeOptions = listOf("Select", "Private", "Public")

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerType.adapter = adapter
        spinnerType2.adapter = adapter
        spinnerType3.adapter = adapter

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedType = parent.getItemAtPosition(position) as String
                if (selectedType == "Select") {
                    return
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        spinnerType2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedType = parent.getItemAtPosition(position) as String
                if (selectedType == "Select") {
                    return
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        spinnerType3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedType = parent.getItemAtPosition(position) as String
                if (selectedType == "Select") {
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
        (activity as UpangMainActivity).updateActionBarTitle("Course Details")
    }
}
