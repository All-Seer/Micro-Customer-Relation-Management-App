package com.example.phinmaedapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.phinmaedapp.databinding.FragmentVideoPlayerDialogBinding


class VideoPlayerDialogFragment : DialogFragment() {
    private var _binding: FragmentVideoPlayerDialogBinding? = null
    private val binding get() = _binding!!
    private var exoPlayer: ExoPlayer? = null

    companion object {
        private const val ARG_VIDEO_URL = "video_url"

        fun newInstance(videoUrl: String): VideoPlayerDialogFragment {
            val args = Bundle().apply {
                putString(ARG_VIDEO_URL, videoUrl)
            }
            return VideoPlayerDialogFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoUrl = arguments?.getString(ARG_VIDEO_URL) ?: return

        exoPlayer = ExoPlayer.Builder(requireContext()).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = true
        }

        binding.playerView.player = exoPlayer
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.release()
        exoPlayer = null
        _binding = null
    }
}
