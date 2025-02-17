package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.FragmentPhinmaedSchoolsBinding

class PhinmaedSchools : Fragment() {
    private var _binding: FragmentPhinmaedSchoolsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhinmaedSchoolsBinding.inflate(inflater, container, false)

        binding.sUpang.setOnClickListener {
            startActivity(Intent(this.context, LoginActivity::class.java))
        }



        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}