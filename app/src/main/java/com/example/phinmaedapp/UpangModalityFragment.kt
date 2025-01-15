package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.FragmentUpangModalityBinding


class UpangModalityFragment : Fragment() {

    private var _binding: FragmentUpangModalityBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpangModalityBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        (activity as UpangMainActivity).updateActionBarTitle("Modality Help")
    }
}