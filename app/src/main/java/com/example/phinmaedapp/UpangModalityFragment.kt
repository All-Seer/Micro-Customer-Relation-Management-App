package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCourses.setOnClickListener {
            toggleCourseListVisibility()
        }
    }
    private fun toggleCourseListVisibility() {
        val isVisible = binding.layoutCourseList.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cardViewCourses, transition)
        binding.layoutCourseList.visibility = if (isVisible) View.GONE else View.VISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onResume() {
        super.onResume()
        (activity as UpangMainActivity).updateActionBarTitle("Modality Help")
    }
}