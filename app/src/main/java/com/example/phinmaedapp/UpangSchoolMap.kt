package com.example.phinmaedapp

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.FragmentUpangSchoolMapBinding

class UpangSchoolMap : Fragment() {

    private var _binding: FragmentUpangSchoolMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpangSchoolMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btFloorPick.setOnClickListener{
            startActivity(Intent(this.context, UpangFloorList::class.java))
        }

        scrollHorizontallySlowlyUrdaneta()
    }

    private fun scrollHorizontallySlowlyUrdaneta() {
        val scrollView = binding.horizontalScrollViewUrdaneta
        val scrollAmount = 9000
        val duration = 18000L

        ObjectAnimator.ofInt(scrollView, "scrollX", scrollAmount).apply {
            this.duration = duration
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as UpangMainActivity).updateActionBarTitle("School Map")
    }
}