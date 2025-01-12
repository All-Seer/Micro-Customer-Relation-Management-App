package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.FragmentUpangHomeBinding

class UpangHomeFragment : Fragment() {

    private lateinit var binding: FragmentUpangHomeBinding
    private lateinit var spinnerGender: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpangHomeBinding.inflate(inflater, container, false)
        arguments?.let {
            val lastName = it.getString("LASTNAME")
            val firstName = it.getString("FIRSTNAME")
            val middleName = it.getString("MIDDLENAME")
            val extensionName = it.getString("EXTENSIONNAME")
            binding.tvstudName.text = "$lastName, $firstName $middleName $extensionName"
        }
        return binding.root
    }

}