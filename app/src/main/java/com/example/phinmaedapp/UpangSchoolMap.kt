package com.example.phinmaedapp

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.FragmentUpangSchoolMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

class UpangSchoolMap : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentUpangSchoolMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpangSchoolMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        // Initialize the bottom sheet
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomsheet) // Use bottomsheet ID
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        // Handle button click to show/hide the bottom sheet
        binding.btFloorPick.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        // Handle bottom sheet state changes (optional)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // Bottom sheet is fully expanded
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        // Bottom sheet is hidden
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Handle slide events (optional)
            }
        })
        // Set click listeners for the buttons inside the included layout
        binding.btn1stFloor.setOnClickListener {
            println("1st Floor Button Clicked")
            // Use requireContext() to get the correct Context
            startActivity(Intent(requireContext(), UpangFirstFloor::class.java))
        }
        binding.btn2ndFloor.setOnClickListener {
            val intent = Intent(requireContext(), UpangSecondFloor::class.java)
            startActivity(intent)
        }
        binding.btn3rdFloor.setOnClickListener {
            val intent = Intent(requireContext(), UpangThirdFloor::class.java)
            startActivity(intent)
        }
        binding.btn4thFloor.setOnClickListener {
            val intent = Intent(requireContext(), UpangFourthFloor::class.java)
            startActivity(intent)
        }
        binding.btn5thFloor.setOnClickListener {
            val intent = Intent(requireContext(), UpangFifthFloor::class.java)
            startActivity(intent)
        }

        // Start the horizontal scroll animation
        scrollHorizontallySlowlyUrdaneta()
    }

    private fun scrollHorizontallySlowlyUrdaneta() {
        val scrollView = binding.horizontalScrollViewUrdaneta
        val scrollAmount = 8000
        val duration = 18000L

        ObjectAnimator.ofInt(scrollView, "scrollX", scrollAmount).apply {
            this.duration = duration
            start()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Set up the map properties
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isCompassEnabled = true

        // Add a marker for the school location
        val schoolLocation = LatLng(15.96984442537076, 120.57218255472684)
        googleMap.addMarker(
            MarkerOptions()
                .position(schoolLocation)
                .title("PHINMA - University of Pangasinan")
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(schoolLocation, 15f))
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