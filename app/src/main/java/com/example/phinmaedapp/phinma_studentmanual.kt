package com.example.phinmaedapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phinmaedapp.databinding.FragmentPhinmaStudentmanualBinding

class phinma_studentmanual : Fragment() {
    private var _binding: FragmentPhinmaStudentmanualBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhinmaStudentmanualBinding.inflate(inflater, container, false)
        binding.manual3.setOnClickListener(){
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, pdf())
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }



}