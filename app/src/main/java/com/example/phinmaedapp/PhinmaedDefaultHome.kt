package com.example.phinmaedapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.FragmentPhinmaedHomeBinding

class PhinmaedDefaultHome : Fragment(R.layout.fragment_phinmaed_home) {

    private var _binding: FragmentPhinmaedHomeBinding? = null
    private val binding get() = _binding!!

    private var currentPosition: Int = 0
    private var isVideoPlaying: Boolean = false
    private lateinit var videoView: VideoView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhinmaedHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOpenLink.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.phinma.edu.ph/contact-us/"))
            if (browserIntent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(browserIntent)
            }
        }

        videoView = binding.phinmaedVideo
        val videoPath = "android.resource://" + activity?.packageName + "/" + R.raw.phinmaedvideo
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.setVolume(0f, 0f)

            videoView.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        if (isVideoPlaying) {
            currentPosition = videoView.currentPosition
            videoView.pause()
            isVideoPlaying = false
        }
    }
    override fun onResume() {
        super.onResume()
        if (!isVideoPlaying) {
            videoView.seekTo(currentPosition)
            videoView.start()
            isVideoPlaying = true
        }
    }
    override fun onStop() {
        super.onStop()
        if (isVideoPlaying) {
            currentPosition = videoView.currentPosition
        }
    }
}

