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
        return inflater.inflate(R.layout.fragment_upang_home, container, false)


    }

}