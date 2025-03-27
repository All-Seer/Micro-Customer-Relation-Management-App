package com.example.phinmaedapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoUrl = arguments?.getString(ARG_VIDEO_URL) ?: return

        binding.playerView.apply {
            // Hide navigation buttons
            setShowNextButton(false)
            setShowPreviousButton(false)

            // Keep other default controls
            useController = true
            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)

            // Player initialization
            exoPlayer = ExoPlayer.Builder(requireContext())
                .setSeekForwardIncrementMs(5000)
                .setSeekBackIncrementMs(5000)
                .build()
                .apply {
                    setMediaItem(MediaItem.fromUri(videoUrl))
                    prepare()
                    playWhenReady = true
                }
            player = exoPlayer
        }

        binding.touchInterceptor.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

            // Make sure touches outside don't go through
            setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            )
            clearFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
        }
    }

    override fun dismiss() {
        stopPlayer()
        super.dismiss()
    }

    private fun stopPlayer() {
        exoPlayer?.let {
            it.stop()
            it.release()
            exoPlayer = null
        }
    }

    override fun onDestroyView() {
        stopPlayer()
        _binding = null
        super.onDestroyView()
    }
}