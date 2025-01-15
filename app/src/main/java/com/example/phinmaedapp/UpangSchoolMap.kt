package com.example.phinmaedapp

import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.FragmentUpangSchoolMapBinding

class UpangSchoolMap : Fragment() {

    private var _binding: FragmentUpangSchoolMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var videoView: VideoView
    private var currentPosition: Int = 0
    private var isVideoPlaying: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpangSchoolMapBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoView = binding.upangMapVideo
        val videoPath = "android.resource://" + activity?.packageName + "/" + R.raw.phinma_upang_facilities
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.setVolume(0f, 0f)
            videoView.start()
        }
        scrollHorizontallySlowly()
        scrollHorizontallySlowlyUrdaneta()
    }
    private fun scrollHorizontallySlowly() {
        val scrollView = binding.horizontalScrollView
        val scrollAmount = 9000
        val duration = 20000L

        ObjectAnimator.ofInt(scrollView, "scrollX", scrollAmount).apply {
            this.duration = duration
            start()
        }
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
