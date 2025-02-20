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

    private var _binding: FragmentUpangEventPageBinding? = null
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


        binding.btJuneActivities.setOnClickListener {
            toggleJuneActivities()
        }
        binding.btJulyActivities.setOnClickListener {
            toggleJulyActivities()
        }
        binding.btAugustActivities.setOnClickListener {
            toggleAugustActivities()
        }
        binding.btSeptemberActivities.setOnClickListener {
            toggleSeptemberActivities()
        }
        binding.btOctoberActivities.setOnClickListener {
            toggleOctoberActivities()
        }
        binding.btNovemberActivities.setOnClickListener {
            toggleNovemberActivities()
        }
        binding.btDecemberActivities.setOnClickListener {
            toggleDecemberActivities()
        }
        binding.btJanuaryActivities.setOnClickListener {
            toggleJanuaryActivities()
        }
        binding.btFebruaryActivities.setOnClickListener {
            toggleFebruaryActivities()
        }
        binding.btMarchActivities.setOnClickListener {
            toggleMarchActivities()
        }
        binding.btAprilActivities.setOnClickListener {
            toggleAprilActivities()
        }
        binding.btMayActivities.setOnClickListener {
            toggleMayActivities()
        }
    }

    private fun toggleJuneActivities() {
        val isVisible = binding.layoutJuneActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvJuneActivities, transition)
        binding.layoutJuneActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleJulyActivities() {
        val isVisible = binding.layoutJulyActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvJulyActivities, transition)
        binding.layoutJulyActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleAugustActivities() {
        val isVisible = binding.layoutAugustActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvAugustActivities, transition)
        binding.layoutAugustActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleSeptemberActivities() {
        val isVisible = binding.layoutSeptemberActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvSeptemberActivities, transition)
        binding.layoutSeptemberActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleOctoberActivities() {
        val isVisible = binding.layoutOctoberActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvOctoberActivities, transition)
        binding.layoutOctoberActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleNovemberActivities() {
        val isVisible = binding.layoutNovemberActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvNovemberActivities, transition)
        binding.layoutNovemberActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleDecemberActivities() {
        val isVisible = binding.layoutDecemberActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvDecemberActivities, transition)
        binding.layoutDecemberActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleJanuaryActivities() {
        val isVisible = binding.layoutJanuaryActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvJanuaryActivities, transition)
        binding.layoutJanuaryActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleFebruaryActivities() {
        val isVisible = binding.layoutFebruaryActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvFebruaryActivities, transition)
        binding.layoutFebruaryActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleMarchActivities() {
        val isVisible = binding.layoutMarchActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvMarchActivities, transition)
        binding.layoutMarchActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleAprilActivities() {
        val isVisible = binding.layoutAprilActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvAprilActivities, transition)
        binding.layoutAprilActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }
    private fun toggleMayActivities() {
        val isVisible = binding.layoutMayActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvMayActivities, transition)
        binding.layoutMayActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }
    override fun onResume() {
        super.onResume()
        (activity as UpangMainActivity).updateActionBarTitle("Events")
    }
}