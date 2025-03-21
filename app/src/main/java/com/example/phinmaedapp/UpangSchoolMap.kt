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

class UpangSchoolMap : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentUpangSchoolMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap

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

        // Set up the floor pick button
        binding.btFloorPick.setOnClickListener {
            startActivity(Intent(requireContext(), UpangFloorList::class.java))
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