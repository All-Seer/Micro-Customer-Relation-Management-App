package com.example.phinmaedapp

import android.app.Notification.Action
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.phinmaedapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var videoView: VideoView
    private var currentPosition: Int = 0
    private var isVideoPlaying: Boolean = false
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.iyot,R.string.open, R.string.close)

        videoView = binding.phinmaedVideo
        val videoPath = "android.resource://" + packageName + "/" + R.raw.phinmaedvideo
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.setVolume(0f, 0f)
        }
        videoView.start()
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