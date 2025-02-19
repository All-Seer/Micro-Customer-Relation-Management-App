package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.phinmaedapp.databinding.FragmentUpangEventPageBinding


class UpangEventPageFragment : Fragment() {

    private var _binding : FragmentUpangEventPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpangEventPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btJulyActivities.setOnClickListener {
            toggleDagupanCourseListVisibility()
        }

    }

    private fun toggleDagupanCourseListVisibility() {
        val isVisible = binding.layoutJuneActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvJulyActivities, transition)
        binding.layoutJuneActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }
}